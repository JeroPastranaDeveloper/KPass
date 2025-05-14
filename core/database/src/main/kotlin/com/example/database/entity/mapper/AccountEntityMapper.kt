package com.example.database.entity.mapper

interface AccountEntityMapper<Domain, Entity> {
    fun asEntity(domain: Domain): Entity
    fun asDomain(entity: Entity): Domain
}