package com.example.presentertests.presenter

import com.example.presentertests.model.GithubUser
import com.example.presentertests.repository.GitHubRepository
import com.example.presentertests.view.ViewContract
import retrofit2.Response

internal class UsersPresenter internal constructor(
    private val viewContract: ViewContract,
    private val repository: GitHubRepository
) : PresenterContract, GitHubRepository.GitHubRepositoryCallback {

    override fun getUsers() {
        viewContract.displayLoading(true)
        repository.getUsers(this)
    }

    override fun handleGitHubResponse(response: Response<List<GithubUser>>) {
        viewContract.displayLoading(false)
        if (response != null && response.isSuccessful) {
            val searchResponse = response.body()
            if (searchResponse != null) {
                viewContract.displaySearchResults(searchResponse)
            } else {
                viewContract.displayError("Search results or total count are null")
            }
        } else {
            viewContract.displayError("Response is null or unsuccessful")
        }
    }

    override fun handleGitHubError() {
        viewContract.displayLoading(false)
        viewContract.displayError()
    }
}
