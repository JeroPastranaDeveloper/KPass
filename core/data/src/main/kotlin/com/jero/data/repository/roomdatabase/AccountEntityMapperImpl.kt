package com.jero.data.repository.roomdatabase

import com.example.database.entity.AccountEntity
import com.example.database.entity.mapper.AccountEntityMapper
import com.jero.core.model.Account

object AccountEntityMapperImpl : AccountEntityMapper<Account, AccountEntity> {
    override fun asEntity(domain: Account): AccountEntity = AccountEntity(
        title = domain.title,
        description = domain.description,
        email = domain.email,
        password = domain.password,
        id = domain.id
    )

    override fun asDomain(entity: AccountEntity): Account {
        return Account(
            title = entity.title,
            description = entity.description,
            email = entity.email,
            password = entity.password,
            id = entity.id
        )
    }
}

fun Account.asEntity(): AccountEntity = AccountEntityMapperImpl.asEntity(this)
fun AccountEntity.asAccount(): Account = AccountEntityMapperImpl.asDomain(this)
