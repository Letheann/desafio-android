package com.desafio.android.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.desafio.android.data.model.LogsDto
import kotlinx.coroutines.flow.Flow

@Dao
interface LogsDao {
    @Query("SELECT * FROM logs order by id ASC")
    fun getAll(): Flow<List<LogsDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertAll(movies: List<LogsDto>)

    @Query("DELETE FROM logs")
    suspend fun deleteAll()
}
