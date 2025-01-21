package com.desafio.android.data.repository

import android.content.SharedPreferences
import android.util.Base64
import com.desafio.android.data.local.LogsDao
import com.desafio.android.data.mapper.LogsDtoMapper
import com.desafio.android.presentation.login.compose.Credentials
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

interface LoginRepository {
    fun encrypt(credentials: Credentials): Flow<ByteArray>
    fun decrypt(): Flow<String>
}

class LoginRepositoryImpl(
    private val randomKey: String,
    private val gson: Gson,
    private val encryptedSharedPreferences: SharedPreferences,
    private val logsDao: LogsDao
) : LoginRepository {

    override fun encrypt(credentials: Credentials): Flow<ByteArray> = flow {
        val cipherText: ByteArray
        val json = gson.toJson(credentials)
        val plainText = json.toByteArray(Charsets.UTF_8)
        val key = generateKey(randomKey)
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(Cipher.ENCRYPT_MODE, key)
        cipherText = cipher.doFinal(plainText)
        encryptedSharedPreferences.edit()
            .putString(IV_VALUE, Base64.encodeToString(cipher.iv, Base64.DEFAULT)).apply()
        encryptedSharedPreferences.edit()
            .putString(CIPHER, Base64.encodeToString(cipherText, Base64.DEFAULT)).apply()
        saveLog(json)
        emit(cipherText)
    }

    override fun decrypt() = flow {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        val key = generateKey(randomKey)
        cipher.init(
            Cipher.DECRYPT_MODE,
            key,
            IvParameterSpec(
                Base64.decode(
                    encryptedSharedPreferences.getString(IV_VALUE, ""),
                    Base64.DEFAULT
                )
            )
        )
        val cipherText =
            cipher.doFinal(
                Base64.decode(
                    encryptedSharedPreferences.getString(CIPHER, ""),
                    Base64.DEFAULT
                )
            )
        emit(buildString(cipherText))
    }

    private fun generateKey(password: String): SecretKeySpec {
        val digest: MessageDigest = MessageDigest.getInstance("SHA-256")
        val bytes = password.toByteArray()
        digest.update(bytes, 0, bytes.size)
        val key = digest.digest()
        return SecretKeySpec(key, "AES")
    }

    private fun buildString(text: ByteArray): String {
        val sb = StringBuilder()
        for (char in text) {
            sb.append(char.toInt().toChar())
        }
        return sb.toString()
    }

    private suspend fun saveLog(credentials: String) {
        logsDao.insertAll(LogsDtoMapper.transformToList(listOf(credentials)))
    }

    companion object {
        private const val CIPHER = "cypherText"
        private const val IV_VALUE = "ivValue"
    }

}