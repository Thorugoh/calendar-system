package org.example.utils

import org.example.data.UserEntity
import org.example.model.User

fun User.toEntity(): UserEntity {
    return UserEntity(this.name, this.id)
}

fun UserEntity.toModel(): User {
    return User(this.name, this.id)
}