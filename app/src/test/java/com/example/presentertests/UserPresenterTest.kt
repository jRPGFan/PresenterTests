package com.example.presentertests

import com.example.presentertests.model.GithubUser
import com.example.presentertests.presenter.UsersPresenter
import com.example.presentertests.repository.GitHubRepository
import com.example.presentertests.view.ViewContract
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import retrofit2.Response

class UserPresenterTest {
    private lateinit var presenter: UsersPresenter

    @Mock
    private lateinit var repository: GitHubRepository

    @Mock
    private lateinit var viewContract: ViewContract

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = UsersPresenter(viewContract, repository)
    }

    @Test
    fun getAllUsers_Test() {
        presenter.getUsers()
        verify(repository, times(1)).getUsers(presenter)
    }

    @Test
    fun handleGithubError_DisplayError_Test() {
        presenter.handleGitHubError()
        verify(viewContract, times(1)).displayError()
    }

    @Test
    fun handleGithubError_AssertResponseUnsuccessful() {
        val response = mock(Response::class.java) as Response<List<GithubUser>>
        `when`(response.isSuccessful).thenReturn(false)
        assertFalse(response.isSuccessful)
    }

    @Test
    fun handleGithubResponse_DisplayUnsuccessfulResponseError() {
        val response = mock(Response::class.java) as Response<List<GithubUser>>
        `when`(response.isSuccessful).thenReturn(false)
        presenter.handleGitHubResponse(response)
        verify(viewContract, times(1))
            .displayError("Response is null or unsuccessful")
    }

    @Test
    fun handleGithubResponse_ResponseNull() {
        val response = mock(Response::class.java) as Response<List<GithubUser>>
        `when`(response.body()).thenReturn(null)
        presenter.handleGitHubResponse(response)
        assertNull(response.body())
    }

    @Test
    fun handleGithubResponse_ResponseNotNull() {
        val response = mock(Response::class.java) as Response<List<GithubUser>>
        `when`(response.body()).thenReturn(listOf(mock(GithubUser::class.java)))
        presenter.handleGitHubResponse(response)
        assertNotNull(response.body())
    }

    @Test
    fun handleGithubResponse_BodyNull() {
        val response = mock(Response::class.java) as Response<List<GithubUser>>
        `when`(response.isSuccessful).thenReturn(true)
        `when`(response.body()).thenReturn(null)
        presenter.handleGitHubResponse(response)
        verify(viewContract, times(1))
            .displayError("Search results or total count are null")
    }

    @Test
    fun handleGithubResponse_BodyNotNull() {
        val response = mock(Response::class.java) as Response<List<GithubUser>>
        val users = listOf(mock(GithubUser::class.java))
        `when`(response.isSuccessful).thenReturn(true)
        `when`(response.body()).thenReturn(users)
        presenter.handleGitHubResponse(response)
        verify(viewContract, times(1)).displaySearchResults(users)
    }

    @Test
    fun handleGithubResponse_ResponseFailed_ViewContractMethodOrder() {
        val response = mock(Response::class.java) as Response<List<GithubUser>>
        `when`(response.isSuccessful).thenReturn(false)
        presenter.handleGitHubResponse(response)
        val methodOrder = inOrder(viewContract)
        methodOrder.verify(viewContract).displayLoading(false)
        methodOrder.verify(viewContract).displayError("Response is null or unsuccessful")
    }
}