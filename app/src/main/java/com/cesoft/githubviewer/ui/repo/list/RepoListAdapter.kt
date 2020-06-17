package com.cesoft.githubviewer.ui.repo.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cesoft.githubviewer.R
import com.cesoft.githubviewer.data.RepoModel
import kotlinx.android.synthetic.main.item_repo.view.*
import java.util.*

////////////////////////////////////////////////////////////////////////////////////////////////////
//
class RepoListAdapter(
    private val items: MutableList<RepoModel>,
    private val onClickListener: OnClickListener)
    : RecyclerView.Adapter<RepoListAdapter.ViewHolder>() {

    companion object {
        private val TAG: String = RepoListAdapter::class.simpleName!!
    }

    interface OnClickListener {
        fun onItemClicked(repo: RepoModel)
    }

    private var list: MutableList<RepoModel>? = null
    init {
        list = ArrayList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_repo, parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RepoListAdapter.ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: RepoModel) {
            itemView.setOnClickListener {
                onClickListener.onItemClicked(item)
            }
            itemView.name.text = item.name
            itemView.description.text = item.description
            itemView.owner.text = itemView.context.getString(R.string.owner_title, item.owner?.login)
            Glide.with(itemView).load(item.owner?.avatarUrl).into(itemView.image)
        }
    }
}
