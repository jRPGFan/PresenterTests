package com.example.presentertests.model

import com.google.gson.annotations.SerializedName

data class GithubUser(
    val login: String? = null,

    val id: Long? = null,

    @SerializedName("avatar_url")
    val avatarUrl: String? = null,

    @SerializedName("repos_url")
    val reposUrl: String? = null,
)
