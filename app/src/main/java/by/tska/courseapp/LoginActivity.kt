package by.tska.courseapp

import android.app.ProgressDialog
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

class LoginActivity : AppCompatActivity() {

    private var _emailText: EditText? = null
    private var _passwordText: EditText? = null
    private var _loginButton: Button? = null
    private var _signupLink: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initViews()
        _loginButton!!.setOnClickListener { login() }
        _signupLink!!.setOnClickListener { // Start the Signup activity
            val intent = Intent(applicationContext, SignupActivity::class.java)
            startActivityForResult(intent, REQUEST_SIGNUP)
            finish()
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out)
        }
    }

    private fun initViews() {
        _emailText = findViewById(R.id.input_email)
        _passwordText = findViewById(R.id.input_password)
        _loginButton = findViewById(R.id.btn_login)
        _signupLink = findViewById(R.id.link_signup)
    }

    fun login() {
        Log.d(TAG, "Login")
        if (!validate()) {
            onLoginFailed()
            return
        }
        _loginButton!!.isEnabled = false
        val progressDialog = ProgressDialog(
            this@LoginActivity,
            R.style.AppTheme_Dark_Dialog
        )
        progressDialog.isIndeterminate = true
        progressDialog.setMessage("Authenticating...")
        progressDialog.show()
        val email = _emailText!!.text.toString()
        val password = _passwordText!!.text.toString()

        // TODO: Implement your own authentication logic here.
        Handler(Looper.getMainLooper()).postDelayed(
            { // On complete call either onLoginSuccess or onLoginFailed
                onLoginSuccess()
                // onLoginFailed();
                progressDialog.dismiss()
            }, 3000
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish()
            }
        }
    }

    override fun onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true)
    }

    fun onLoginSuccess() {
        _loginButton!!.isEnabled = true
        finish()
    }

    fun onLoginFailed() {
        Toast.makeText(baseContext, "Login failed", Toast.LENGTH_LONG).show()
        _loginButton!!.isEnabled = true
    }

    fun validate(): Boolean {
        var valid = true
        val email = _emailText!!.text.toString()
        val password = _passwordText!!.text.toString()
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
        return valid
    }

    companion object {
        private const val TAG = "LoginActivity"
        private const val REQUEST_SIGNUP = 0
    }
}