package com.example.bam_project_v2

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserNumber::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userNumberDao(): UserNumberDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "user_number_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}