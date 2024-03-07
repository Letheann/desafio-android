package com.picpay.desafio.android.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.picpay.desafio.android.data.model.UserDto
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user order by id ASC")
    fun getAll(): Flow<List<UserDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertAll(movies: List<UserDto>)

    @Query("DELETE FROM user")
    suspend fun deleteAll()
}
