package com.example.presentertests.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.presentertests.R
import com.example.presentertests.model.GithubUser
import kotlinx.android.synthetic.main.list_item.view.*

internal class UsersAdapter : RecyclerView.Adapter<UsersAdapter.SearchResultViewHolder>() {

    private var results: List<GithubUser> = listOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchResultViewHolder {
        return SearchResultViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, null)
        )
    }

    override fun onBindViewHolder(
        holder: SearchResultViewHolder,
        position: Int
    ) {
        holder.bind(results[position])
    }

    override fun getItemCount(): Int {
        return results.size
    }

    fun updateResults(results: List<GithubUser>) {
        this.results = results
        notifyDataSetChanged()
    }

    internal class SearchResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(userResult: GithubUser) {
            itemView.userName.text = userResult.login
        }
    }
}
