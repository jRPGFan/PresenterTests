package com.example.presentertests.repository

import com.example.presentertests.model.GithubUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GitHubRepository(private val gitHubApi: GithubUsersService) {

    fun getUsers(
        callback: GitHubRepositoryCallback
    ) {
        val call = gitHubApi.getAllUsers()
        call?.enqueue(object : Callback<List<GithubUser>> {

            override fun onResponse(
                call: Call<List<GithubUser>>,
                response: Response<List<GithubUser>>
            ) {
                callback.handleGitHubResponse(response)
            }

            override fun onFailure(
                call: Call<List<GithubUser>>,
                t: Throwable
            ) {
                callback.handleGitHubError()
            }
        })
    }

    interface GitHubRepositoryCallback {
        fun handleGitHubResponse(response: Response<List<GithubUser>>)
        fun handleGitHubError()
    }
}
