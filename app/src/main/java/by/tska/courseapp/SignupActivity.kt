package by.tska.courseapp

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import by.tska.courseapp.dto.*
import by.tska.courseapp.network.Session
import by.tska.courseapp.network.controller.AuthController
import by.tska.courseapp.network.controller.UserController
import by.tska.courseapp.ui.MainScreenActivity
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class SignupActivity : AppCompatActivity() {
    private lateinit var _nameText: EditText
    private lateinit var _addressText: EditText
    private lateinit var _emailText: EditText
    private lateinit var _passwordText: EditText
    private lateinit var _reEnterPasswordText: EditText
    private lateinit var _signupButton: Button
    private lateinit var _loginLink: TextView

    private lateinit var authController: AuthController
    private lateinit var userController: UserController
    private lateinit var session: Session

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        session = Session(applicationContext)
        initControllers(applicationContext)
        initViews()
        _signupButton!!.setOnClickListener { signup() }
        _loginLink!!.setOnClickListener { // Finish the registration screen and return to the Login activity
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out)
        }
    }

    private fun initControllers(context: Context?) {
        authController = AuthController(session)
        userController = UserController(session)
    }

    private fun initViews() {
        _addressText = findViewById(R.id.input_address)
        _emailText = findViewById(R.id.input_email)
        _loginLink = findViewById(R.id.link_login)
        _nameText = findViewById(R.id.input_name)
        _passwordText = findViewById(R.id.input_password)
        _reEnterPasswordText = findViewById(R.id.input_reEnterPassword)
        _signupButton = findViewById(R.id.btn_signup)
    }

    private fun signup() {
        Log.d(TAG, "Signup")
        if (!validate()) {
            onSignupFailed()
            return
        }
        _signupButton!!.isEnabled = false
        val progressDialog = ProgressDialog(
                this@SignupActivity,
                R.style.AppTheme_Dark_Dialog
        )
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Creating Account...")
        progressDialog.show()
        val name = _nameText!!.text.toString()
        val address = _addressText!!.text.toString()
        val email = _emailText!!.text.toString()
        val password = _passwordText!!.text.toString()
        val reEnterPassword = _reEnterPasswordText!!.text.toString()

        // TODO: Implement your own signup logic here.
        Handler(Looper.getMainLooper()).postDelayed({
            // On complete call either onSignupSuccess or onSignupFailed
                    // depending on success
                    signupUser(
                        UserToRegister(email = email,
                        firstName = name.split(' ')[0],
                        lastName = name.split(' ')[1],
                        address = address,
                        password = password)
                    )
                    onSignupSuccess()
                    // onSignupFailed();
                    progressDialog.dismiss()
                }, 3000
        )
    }

    private fun onSignupSuccess() {
        _signupButton!!.isEnabled = true
        setResult(RESULT_OK, null)
        finish()
    }

    private fun onSignupFailed() {
        Toast.makeText(baseContext, "Login failed", Toast.LENGTH_LONG).show()
        _signupButton.isEnabled = true
    }

    private fun signupUser(user: UserToRegister) {
        // Set up progressbar before call

        val gson = Gson()
        val json = gson.toJson(user)
        userController.saveUser(user).enqueue(object : Callback<CurrentUser> {
            override fun onResponse(call: Call<CurrentUser>, response: Response<CurrentUser>) {
                when {
                    response.code() == 201 -> {
                        try {
                            val currentUser = response.body()!!
                            val intent = Intent(applicationContext, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out)
                            //Starting main activity after user sees dialog
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                    response.code() == 500 -> {
                        try {
                            val jsonObject = JSONObject(response.errorBody()!!.string())
                            Log.e("SignupFragment", jsonObject.toString())
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                    else -> Log.e("SignupFragment", response.raw().toString())
                }
            }

            override fun onFailure(call: Call<CurrentUser>, t: Throwable) {
                onSignupFailed()
            }
        })
    }

    private fun validate(): Boolean {
        var valid = true
        val name = _nameText!!.text.toString()
        val address = _addressText!!.text.toString()
        val email = _emailText!!.text.toString()
        val password = _passwordText!!.text.toString()
        val reEnterPassword = _reEnterPasswordText!!.text.toString()
        if (name.isEmpty() || name.length < 3) {
            _nameText!!.error = "at least 3 characters"
            valid = false
        } else {
            _nameText!!.error = null
        }
        if (address.isEmpty()) {
            _addressText!!.error = "Enter Valid Address"
            valid = false
        } else {
            _addressText!!.error = null
        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText!!.error = "enter a valid email address"
            valid = false
        } else {
            _emailText!!.error = null
        }
        if (password.isEmpty() || password.length < 4 || password.length > 10) {
            _passwordText!!.error = "between 4 and 10 alphanumeric characters"
            valid = false
        } else {
            _passwordText!!.error = null
        }
        if (reEnterPassword.isEmpty() || reEnterPassword.length < 4 || reEnterPassword.length > 10 || reEnterPassword != password) {
            _reEnterPasswordText!!.error = "Password Do not match"
            valid = false
        } else {
            _reEnterPasswordText!!.error = null
        }
        return valid
    }

    companion object {
        private const val TAG = "SignupActivity"
    }
}