package com.example.coredata.dao.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.coredata.models.User

@Dao
interface UserDB {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(userDataList: List<User>?)

    @Query("DELETE FROM user")
    suspend fun deleteAll()
}