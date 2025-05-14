package com.example.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.entity.AccountEntity

@Dao
interface AccountsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(newEntity: AccountEntity)

    @Query("SELECT * FROM AccountEntity")
    suspend fun getAccounts(): List<AccountEntity>

    @Query("SELECT * FROM AccountEntity WHERE id = :id")
    suspend fun getAccountById(id: String): AccountEntity?

    @Query("DELETE FROM AccountEntity WHERE id = :id")
    suspend fun deleteAccount(id: String)
}
