package ru.skillbranch.kotlinexample

import androidx.annotation.VisibleForTesting
import java.lang.IllegalStateException

object UserHolder {

    private val map = mutableMapOf<String, User>()

    fun registerUser(
            fullName: String,
            email: String,
            password: String
    ): User = User.makeUser(fullName, email, password)
            .also { user ->
                if (map[user.login] != null) throw IllegalArgumentException("User is already exist!")
                map[user.login] = user
            }

    fun registerUserByPhone(fullName: String, phone: String
    ): User = User.makeUser(fullName = fullName, phone = phone)
            .also { user ->
                if (map[phone] != null) throw IllegalArgumentException("User is already exist!")
                map[phone] = user
            }

    fun loginUser(login: String, password: String): String? =
            map[login.trim()]?.let {
                if (it.checkPassword(password)) it.userInfo
                else null
            }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    fun clearHolder() {
        map.clear()
    }

    fun requestAccessCode(login: String) {
        map[login.trim()]?.let {
            user -> user.changePassword(user.accessCode!!, user.generateAccessCode())
        }
    }

    fun importUsers(list: List<String>) : List<User> {
        val result = ArrayList<User>()
        for (rawUser in list) {
            User.importUser(rawUser).also { user ->
                if (map[user.login] != null) {
                    if (map[user.phone] != null) throw IllegalArgumentException("User is already exist!")
                    map[user.phone!!] = user
                } else {
                    map[user.login] = user
                }

                result.add(user)
            }
        }
        return result
    }
}