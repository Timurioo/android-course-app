package by.tska.courseapp.dto

data class RepoOwner(val login: String, val id: Long, val avatar_url: String, val url: String)

data class User(val id: String, val email: String, val firstName: String, val lastName: String,
    val password: String, val isBlocked: String, val role: Role, val address: String)

data class UserToRegister(val email: String, val firstName: String, val lastName: String,
                          val password: String, val address: String, val isBlocked: String = "0",
                          val role: Role = Role("2", "user"))

data class CurrentUser(val id: String, val email: String, val firstName: String, val address: String, val role: Role)

data class Role(val id: String, val name: String)

data class Content(val id: String, val pictureUrl: String, val itemName: String,
                   val description: String, val cost: String, val userProfile: User)

data class Subscription(val id: String, val billingAccount: BillingAccount,
                        val content: Content, val isBlocked: String)

data class BillingAccount(val id: String, val walletId: String, val funds: String,
                          val userProfile: User, val isActive: String)

data class AuthToken(var token: String)