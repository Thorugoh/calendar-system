package service

import org.example.data.UserDAO
import org.example.model.User

class UserService(private val userDAO: UserDAO) {
    fun createUser(user: User) {
        return userDAO.add(user)
    }

    fun getUserById(id: Int): User {
        return userDAO.getById(id)
    }
}