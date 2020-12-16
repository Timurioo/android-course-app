package by.tska.courseapp.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.tska.courseapp.R
import by.tska.courseapp.dto.Content

class MainScreenActivity : AppCompatActivity(), ContentContract.View {

    val progressBar: ProgressBar by lazy {
        findViewById<ProgressBar>(R.id.progress_bar)
    }
    val repoList: RecyclerView by lazy {
        findViewById<RecyclerView>(R.id.rv_repo_list)
    }

    val mPresenter: ContentContract.Presenter by lazy {
        ContentPresenter(this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        repoList.layoutManager = LinearLayoutManager(this)

        if (!intent.hasExtra("username")) {
            finish()
        }

        val username = intent.getStringExtra("username")
        val libType = intent.getStringExtra("libraryType")

        if (libType != null) {
            if (username != null) {
                mPresenter.start(libType, username)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.destroy()
    }

    override fun onProductsAvailable(products: Array<Content>) {
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            val repoAdapter = ContentAdapter(products)
            repoList.adapter = repoAdapter
        }
    }

    override fun showLoading() {
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            progressBar.visibility = View.VISIBLE
            repoList.alpha = 0.4f
        }
    }

    override fun hideLoading() {
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            progressBar.visibility = View.GONE
            repoList.alpha = 1.0f
        }
    }

    override fun displayError(message: String) {
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun destroy() {
        finish()
    }
}
