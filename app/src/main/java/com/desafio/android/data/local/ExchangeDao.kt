package com.desafio.android.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.desafio.android.data.model.ExchangeDto
import kotlinx.coroutines.flow.Flow

@Dao
interface ExchangeDao {

    @Query("SELECT * FROM exchange ORDER BY exchangeId ASC")
    fun getAll(): Flow<List<ExchangeDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(exchanges: List<ExchangeDto>)

    @Query("DELETE FROM exchange")
    suspend fun deleteAll()

    @Query("SELECT * FROM exchange WHERE exchangeId = :id LIMIT 1")
    fun getExchangeById(id: String): Flow<ExchangeDto>
}
