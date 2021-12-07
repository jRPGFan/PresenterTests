package com.example.presentertests.repository

import com.example.presentertests.model.GithubRepository
import com.example.presentertests.model.GithubUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface GithubUsersService {
    @GET("/users")
    fun getAllUsers(): Call<List<GithubUser>>

    @GET
    fun getRepositories(@Url reposUrl: String): Call<List<GithubRepository>>
}
