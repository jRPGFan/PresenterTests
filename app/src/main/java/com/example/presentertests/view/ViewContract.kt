package com.example.presentertests.view

import com.example.presentertests.model.GithubUser

internal interface ViewContract {
    fun displaySearchResults(searchResults: List<GithubUser>)

    fun displayError()
    fun displayError(error: String)
    fun displayLoading(show: Boolean)
}
