package by.tska.courseapp.network

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class Session(var ctx: Context) {
    private val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx)

    var jwtToken: String?
        get() {
            var token = prefs.getString(TOKEN_KEY, "")
            if (token.isNullOrEmpty()) {
                token = "Bearer " + prefs.getString(TOKEN_KEY, "")
            }
            return token
        }
        set(token) {
            prefs.edit {
                putString(TOKEN_KEY, token)
            }
        }

    var userId: String?
        get() {
            return prefs.getString(USER_ID_KEY, "")
        }
        set(userId) {
            prefs.edit {
                remove(USER_ID_KEY)
                putString(USER_ID_KEY, userId)
            }
        }


    fun signOut() {
        prefs.edit {
            remove(TOKEN_KEY)
            remove(USER_ID_KEY)
            clear()
        }
    }

    fun saveToken(token: String) {
        prefs.edit {
            remove(TOKEN_KEY)
            putString(TOKEN_KEY, token)
        }
    }

    private companion object {
        const val TOKEN_KEY = "JwtToken"
        const val USER_ID_KEY = "UserId"
    }
}