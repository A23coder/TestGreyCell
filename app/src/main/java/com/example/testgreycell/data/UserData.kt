package com.example.testgreycell.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "user_table",
    indices = [Index(value = ["email"], unique = true), Index(value = ["phone"], unique = true)]
)

data class UserData(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String?,
    val email: String?,
    val phone: String,
    val age: String?,
)