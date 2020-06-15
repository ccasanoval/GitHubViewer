package com.cesoft.githubviewer.ui.repo.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cesoft.githubviewer.R
import com.cesoft.githubviewer.data.RepoModel
import kotlinx.android.synthetic.main.item_repo.view.*
import java.util.*

////////////////////////////////////////////////////////////////////////////////////////////////////
//
class RepoListAdapter(val items: MutableList<RepoModel>, val callback: OnClickListener)
    : RecyclerView.Adapter<RepoListAdapter.ViewHolder>(), Filterable {

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
                callback.onItemClicked(item)
            }
            //itemView.name.text = itemView.context.getString(R.string.item_title, item.name, item.owner?.login)
            itemView.name.text = item.name
            itemView.description.text = item.description
            itemView.owner.text = itemView.context.getString(R.string.owner_title, item.owner?.login)
            Glide.with(itemView).load(item.owner?.avatarUrl).into(itemView.image)
        }
    }

    /// Implemets Filterable
    override fun getFilter(): Filter = filter
    private var filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = ArrayList<RepoModel>()

            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(list as Iterable<RepoModel>)
            } else {
                val filterPattern = constraint.toString().toLowerCase(Locale.ROOT).trim { it <= ' ' }
                for(item in list!!) {
                    if (item.name != null && item.name.toLowerCase(Locale.ROOT).contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            items.clear()
            val res = results.values
            if(res is List<*> && res.isNotEmpty() && res[0] is RepoModel) {
                items.addAll(res as List<RepoModel>)
            }
            notifyDataSetChanged()
        }
    }
}