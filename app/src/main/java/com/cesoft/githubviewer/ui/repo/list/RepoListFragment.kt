package com.cesoft.githubviewer.ui.repo.list

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.cesoft.githubviewer.R
import com.cesoft.githubviewer.data.RepoModel
import com.cesoft.githubviewer.ui.hideMenuIcon
import kotlinx.android.synthetic.main.fragment_repo_list.*


////////////////////////////////////////////////////////////////////////////////////////////////////
//
class RepoListFragment : Fragment(), RepoListAdapter.OnClickListener, RepoListAdapter.OnSearchListener {

    private var viewModel = RepoListViewModel()
    private var adapter: RepoListAdapter? = RepoListAdapter(mutableListOf(), this, this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_repo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        /// DATA PRESENTATION
        list.layoutManager = LinearLayoutManager(requireContext())
        list.itemAnimator = DefaultItemAnimator()
        list.adapter = adapter
        list.setHasFixedSize(true)

        /// DATA QUERY
        btnPrev.setOnClickListener {
            viewModel.goPrev()
        }
        btnNext.setOnClickListener {
            viewModel.goNext()
        }

        /// DATA RETURNED
        viewModel.list.observe(viewLifecycleOwner, Observer { data ->
            refreshData(data)
        })
        viewModel.status.observe(viewLifecycleOwner, Observer { status ->
            if(status.pageMax == 0)
                refreshStatus(getString(R.string.status, status.page, status.count))
            else
                refreshStatus(getString(R.string.status_max_page, status.page, status.pageMax, status.count))
        })
    }

    /// Implements RepoListAdapter.OnClickListener
    override fun onItemClicked(repo: RepoModel) {
        val bundle = bundleOf(RepoModel.TAG to repo)
        findNavController().navigate(R.id.nav_repo_item, bundle)
        //findNavController().navigate(R.id.action_RepoListFragment_to_RepoItemFragment, bundle)
    }
    /// Implements RepoListAdapter.OnSearchListener
    override fun onSearch(query: String): MutableList<RepoModel>? {
        return viewModel.onSearch(query)
    }

    private fun refreshData(data: MutableList<RepoModel>) {
        adapter = RepoListAdapter(data, this, this)
        list.adapter = adapter
        adapter?.notifyDataSetChanged()
    }
    private fun refreshStatus(status: String) {
        lblStatus.text = status
    }

    override fun onResume() {
        super.onResume()
        hideMenuIcon()
    }

    /// Menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.repolist, menu)
        val menuItem = menu.findItem(R.id.menu_search)
        val searchView = menuItem?.actionView as SearchView
        searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    adapter?.filter?.filter(query)
                    return false
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })
        }
        menuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                Log.e(TAG, "setOnCloseListener------------------------------------")
                viewModel.onSeachClose()
                return true // Return true to collapse action view
            }
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                Log.e(TAG, "onMenuItemActionExpand------------------------------------")
                return true
            }
        })
    }
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId) {
//        }
//        return super.onOptionsItemSelected(item)
//    }

    companion object {
        private val TAG: String = RepoListFragment::class.simpleName!!
    }
}