package com.jero.data.repository.roomdatabase

import com.example.database.AccountsDao
import com.example.domain.repository.roomdatabase.DeleteAccount

class DeleteAccountImpl(
    private val accountsDao: AccountsDao
) : DeleteAccount {
    override suspend fun invoke(id: String) {
        accountsDao.deleteAccount(id)
    }
}
