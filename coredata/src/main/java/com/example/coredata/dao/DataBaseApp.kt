package com.example.coredata.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.coredata.dao.db.UserDB
import com.example.coredata.models.User
import com.example.coredata.utils.Constants

@Database(entities = [User::class], version = 2)
abstract class DataBaseApp : RoomDatabase() {

    abstract fun userDB(): UserDB

    companion object {
        fun getInstance(androidContext: Context): DataBaseApp {
            return Room
                .databaseBuilder(androidContext, DataBaseApp::class.java, Constants.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}