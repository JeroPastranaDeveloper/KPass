package com.example.domain.repository.roomdatabase

interface DeleteAccount {
    suspend operator fun invoke(id: String)
}
