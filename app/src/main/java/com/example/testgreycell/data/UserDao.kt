package com.example.testgreycell.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(userData: UserData):Long

    @Query("select * from user_table")
    fun getUserData(): List<UserData>
}