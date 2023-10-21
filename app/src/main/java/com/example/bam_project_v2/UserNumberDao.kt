package com.example.bam_project_v2

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserNumberDao {

    @Insert
    fun insert(userNumber: UserNumber): Long

    @Query("SELECT * FROM UserNumber")
    fun getAll(): List<UserNumber>

    @Query("SELECT * FROM UserNumber")
    fun getAllCursor(): Cursor
}