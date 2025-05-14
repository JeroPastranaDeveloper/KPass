package com.jero.data.repository.roomdatabase

import com.example.database.AccountsDao
import com.example.domain.repository.roomdatabase.GetAccountsRepository
import com.jero.core.model.Account

class GetAccountsRepositoryImpl(
    private val accountsDao: AccountsDao
) : GetAccountsRepository {
    override suspend fun invoke(): List<Account> =
        accountsDao.getAccounts().map { it.asAccount() }
}
