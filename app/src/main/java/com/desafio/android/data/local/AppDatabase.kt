package com.desafio.android.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.desafio.android.data.model.ExchangeDto

@Database(entities = [ExchangeDto::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ExchangeDao(): ExchangeDao
}
