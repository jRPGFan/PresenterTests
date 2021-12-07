package com.example.presentertests

import com.example.presentertests.model.GithubUser
import com.example.presentertests.repository.GitHubRepository
import com.example.presentertests.repository.GitHubRepository.*
import com.example.presentertests.repository.GithubUsersService
import okhttp3.Request
import okio.Timeout
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubRepositoryTest {
    private lateinit var repository: GitHubRepository

    @Mock
    private lateinit var githubServices: GithubUsersService

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = GitHubRepository(githubServices)
    }

    @Test
    fun getAllUsers() {
        val call = mock(Call::class.java) as Call<List<GithubUser>>
        `when`(githubServices.getAllUsers()).thenReturn(call)
        repository.getUsers(mock(GitHubRepositoryCallback::class.java))
        verify(githubServices, times(1)).getAllUsers()
    }

    @Test
    fun getAllUsers_TestCallback() {
        val response = mock(Response::class.java) as Response<List<GithubUser>>
        val githubCallback = mock(GitHubRepositoryCallback::class.java)
        val call = object : Call<List<GithubUser>> {
            override fun enqueue(callback: Callback<List<GithubUser>>) {
                callback.onResponse(this, response)
                callback.onFailure(this, Throwable())
            }

            override fun clone(): Call<List<GithubUser>> {
                TODO("Not yet implemented")
            }

            override fun execute(): Response<List<GithubUser>> {
                TODO("Not yet implemented")
            }

            override fun isExecuted(): Boolean {
                TODO("Not yet implemented")
            }

            override fun cancel() { }

            override fun isCanceled(): Boolean {
                TODO("Not yet implemented")
            }

            override fun request(): Request {
                TODO("Not yet implemented")
            }

            override fun timeout(): Timeout {
                TODO("Not yet implemented")
            }
        }

        `when`(githubServices.getAllUsers()).thenReturn(call)
        repository.getUsers(githubCallback)
        verify(githubCallback, times(1)).handleGitHubResponse(response)
        verify(githubCallback, times(1)).handleGitHubError()
    }

    @Test
    fun getAllUsers_TestCallback_WithMock() {
        val call = mock(Call::class.java) as Call<List<GithubUser>>
        val callback = mock(Callback::class.java) as Callback<List<GithubUser>>
        val githubCallback = mock(GitHubRepositoryCallback::class.java)
        val response = mock(Response::class.java) as Response<List<GithubUser>>

        `when`(githubServices.getAllUsers()).thenReturn(call)
        `when`(call.enqueue(callback)).then { callback.onResponse(any(), any()) }
        `when`(callback.onResponse(any(), any())).then { githubCallback.handleGitHubResponse(response) }
        repository.getUsers(githubCallback)
        verify(githubCallback, times(1)).handleGitHubResponse(response)
    }
}