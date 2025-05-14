package com.example.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.database.entity.AccountEntity

@Database(
    entities = [AccountEntity::class],
    version = 1,
    exportSchema = true
)

abstract class KPassDatabase : RoomDatabase() {
    abstract fun accountsDao(): AccountsDao
}
