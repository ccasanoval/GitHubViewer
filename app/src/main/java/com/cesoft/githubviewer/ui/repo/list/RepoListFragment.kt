package com.cesoft.githubviewer.ui.repo.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
class RepoListFragment : Fragment(), RepoListAdapter.OnClickListener {

    private var viewModel = RepoListViewModel()
    private var adapter: RepoListAdapter? = RepoListAdapter(mutableListOf(), this)

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
            refreshStatus(getString(R.string.status, status.page, status.count))
        })
    }

    /// Implements RepoListAdapter.OnClickListener
    override fun onItemClicked(repo: RepoModel) {
        val bundle = bundleOf(RepoModel.TAG to repo)
        findNavController().navigate(R.id.nav_repo_item, bundle)
        //findNavController().navigate(R.id.action_RepoListFragment_to_RepoItemFragment, bundle)
    }

    private fun refreshData(data: MutableList<RepoModel>) {
        adapter = RepoListAdapter(data, this)
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

    companion object {
        private val TAG: String = RepoListFragment::class.simpleName!!
    }
}