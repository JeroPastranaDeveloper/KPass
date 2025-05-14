package com.example.domain.repository.roomdatabase

import com.jero.core.model.Account

interface GetAccountsRepository {
    suspend operator fun invoke() : List<Account>
}
