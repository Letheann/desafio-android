package com.example.coredata.dao.db

import androidx.room.*
import com.example.coredata.models.User

@Dao
interface UserDB {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(userDataList: List<User>)

    @Query("DELETE FROM user")
    suspend fun deleteAll()

    @Transaction
    suspend fun insertAndDelete(userDataList: List<User>){
        deleteAll()
        insertList(userDataList)
    }

    @get:Query("SELECT * FROM user")
    val all: List<User>

}