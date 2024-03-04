package org.example.data
import org.example.model.User
import org.example.utils.toEntity
import org.example.utils.toModel
import javax.persistence.EntityManager
class UserDAO(manager: EntityManager): DAO<User, UserEntity>(manager, UserEntity::class.java) {
    override fun toModel(entity: UserEntity): User {
        return entity.toModel().apply {
            meetings = entity.meetings.map {
                it.toModel()
            }.toSet()
        }
    }

    override fun toEntity(user: User): UserEntity {
        return user.toEntity()
    }
}