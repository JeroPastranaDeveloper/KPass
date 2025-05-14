package com.jero.data.repository.roomdatabase

import com.example.database.AccountsDao
import com.example.domain.repository.roomdatabase.SaveAccountRepository
import com.jero.core.model.Account

class SaveAccountRepositoryImpl(
    private val accountsDao: AccountsDao
) : SaveAccountRepository {

    override suspend fun invoke(account: Account) {
        val accountWithId = if (account.id.isBlank()) {
            account.copy(id = generateRandomId())
        } else {
            account
        }

        accountsDao.insertAccount(accountWithId.asEntity())
    }

    private fun generateRandomId(): String = java.util.UUID.randomUUID().toString()
}
