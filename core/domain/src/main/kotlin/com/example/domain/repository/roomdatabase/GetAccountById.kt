package com.example.domain.repository.roomdatabase

import com.jero.core.model.Account

interface GetAccountById {
    suspend operator fun invoke(id: String) : Account
}
