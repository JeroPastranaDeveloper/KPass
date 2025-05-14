package com.jero.data.repository.roomdatabase

import com.example.database.AccountsDao
import com.example.domain.repository.roomdatabase.GetAccountById
import com.jero.core.model.Account

class GetAccountByIdImpl(
    private val accountsDao: AccountsDao
) : GetAccountById {
    override suspend fun invoke(id: String) =
        accountsDao.getAccountById(id)?.asAccount() ?: Account()
}
