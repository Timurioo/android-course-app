package by.tska.courseapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import by.tska.courseapp.ui.MainScreenActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    val etUsername: EditText by lazy {
        findViewById<EditText>(R.id.et_github_username)
    }

    val btnRetrofit: Button by lazy {
        findViewById<Button>(R.id.btn_load_retrofit)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnRetrofit.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        etUsername.text.clear()
    }

    override fun onClick(view: View) {

        val username = etUsername.text.toString().trim()

        when (view.id) {
            R.id.btn_load_retrofit -> {
                loadRepositories(username, "retrofit")
            }
            R.id.btn_load_okhttp -> {
                loadRepositories(username, "okhttp")
            }
            R.id.btn_load_volley -> {
                loadRepositories(username, "volley")
            }
        }
    }

    private fun loadRepositories(username: String, libraryType: String) {
        val intent = Intent(this, MainScreenActivity::class.java)
        intent.putExtra("username", username)
        intent.putExtra("libraryType", libraryType)
        startActivity(intent)
    }
}
