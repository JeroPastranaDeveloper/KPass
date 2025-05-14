package com.example.domain.repository.roomdatabase

import com.jero.core.model.Account

interface SaveAccountRepository {
    suspend operator fun invoke(account: Account)
}
