package com.example.presentertests.view

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.presentertests.R
import com.example.presentertests.model.GithubUser
import com.example.presentertests.presenter.PresenterContract
import com.example.presentertests.presenter.UsersPresenter
import com.example.presentertests.repository.GitHubRepository
import com.example.presentertests.repository.GithubUsersService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MainActivity : AppCompatActivity(), ViewContract {

    private val adapter = UsersAdapter()
    private val presenter: PresenterContract = UsersPresenter(this, createRepository())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUI()
    }

    private fun setUI() {
        setQueryListener()
        setRecyclerView()
    }

    private fun setRecyclerView() {
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
    }

    private fun setQueryListener() {
        loadUsers.setOnClickListener {
            presenter.getUsers()
        }
    }

    private fun createRepository(): GitHubRepository {
        return GitHubRepository(createRetrofit().create(GithubUsersService::class.java))
    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override fun displaySearchResults(searchResults: List<GithubUser>) {
        adapter.updateResults(searchResults)
    }

    override fun displayError() {
        Toast.makeText(this, "Unknown Error!", Toast.LENGTH_SHORT).show()
    }

    override fun displayError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun displayLoading(show: Boolean) {
        if (show) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val BASE_URL = "https://api.github.com"
    }
}
