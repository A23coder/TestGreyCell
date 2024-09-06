package com.example.testgreycell.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(
    entities = [UserData::class] ,
    version = 1
)
abstract class UserDatabase:RoomDatabase() {
     abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null
        fun getDatabase(context: Context): UserDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext ,
                    UserDatabase::class.java ,
                    "App_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}