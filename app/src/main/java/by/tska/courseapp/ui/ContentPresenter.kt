package by.tska.courseapp.ui

import android.app.Activity
import by.tska.courseapp.R
import by.tska.courseapp.dto.Content
import by.tska.courseapp.network.Session
import by.tska.courseapp.network.controller.ContentController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContentPresenter(ctx: Activity, val view: ContentContract.View) : ContentContract.Presenter {

    val context: Activity = ctx
    private val contentController: ContentController

    var isAlive = true

    init {
        contentController = ContentController(session = Session(context))
    }

    override fun start(type: String, username: String) {
        view.showLoading()
        loadAllProducts()
    }

    override fun destroy() {
        isAlive = false
    }

    private fun loadAllProducts() {

        contentController.getAllProducts().enqueue(object : Callback<Array<Content>> {
            override fun onFailure(call: Call<Array<Content>>?, t: Throwable?) {
                if (!isAlive) {
                    return
                }
                view.displayError(context.getString(R.string.some_error_occured))
                view.hideLoading()
                view.destroy()
            }

            override fun onResponse(call: Call<Array<Content>>?, response: Response<Array<Content>>?) {
                if (!isAlive) {
                    return
                }
                if (response?.body() != null) {
                    view.onProductsAvailable(response.body()!!)
                    view.hideLoading()
                } else {
                    view.displayError(context.getString(R.string.some_error_occured))
                    view.hideLoading()
                    view.destroy()
                }
            }
        })
    }
}