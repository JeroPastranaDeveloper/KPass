package com.example.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AccountEntity(
    val title: String,
    val description: String,
    val email: String,
    val password: String,
    @PrimaryKey val id: String
)
