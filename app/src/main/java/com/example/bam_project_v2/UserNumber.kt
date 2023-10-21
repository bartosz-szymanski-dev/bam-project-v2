package com.example.bam_project_v2

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserNumber(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String,
    val number: Int
)