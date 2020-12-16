package by.tska.courseapp.ui

import by.tska.courseapp.dto.Content

interface ContentContract {

    interface View {
        fun showLoading()
        fun hideLoading()
        fun onProductsAvailable(repos: Array<Content>)
        fun displayError(message: String)
        fun destroy()
    }

    interface Presenter {
        fun start(type: String, username: String)
        fun destroy()
    }

}