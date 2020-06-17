package com.cesoft.githubviewer.ui.repo.list

import android.app.SearchManager
import android.database.Cursor
import android.database.MatrixCursor
import android.os.Bundle
import android.provider.BaseColumns
import android.view.*
import android.widget.AutoCompleteTextView
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.cursoradapter.widget.CursorAdapter
import androidx.cursoradapter.widget.SimpleCursorAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.cesoft.githubviewer.R
import com.cesoft.githubviewer.data.RepoModel
import com.cesoft.githubviewer.ui.hideKeyboard
import com.cesoft.githubviewer.ui.hideMenuIcon
import com.google.android.material.snackbar.Snackbar
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
            if(status.pageMax == 0)
                refreshStatus(getString(R.string.status, status.page, status.count))
            else
                refreshStatus(getString(R.string.status_max_page, status.page, status.pageMax, status.count))
        })
        /// STATE RETURNED
        viewModel.isWorking.observe(viewLifecycleOwner, Observer { isWorking ->
            btnPrev.isEnabled = !isWorking
            btnNext.isEnabled = !isWorking
            progressBar.visibility = if(isWorking) View.VISIBLE else View.GONE
        })
        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            when(error) {
                RepoListViewModel.ERROR_EMPTY ->
                    Snackbar.make(root_layout, getString(R.string.error_empty), Snackbar.LENGTH_LONG).show()
                504 ->
                    Snackbar.make(root_layout, getString(R.string.error_504), Snackbar.LENGTH_LONG).show()
                else ->
                    Snackbar.make(root_layout, getString(R.string.api_error), Snackbar.LENGTH_LONG).show()
            }
        })
    }

    /// Implements RepoListAdapter.OnClickListener
    override fun onItemClicked(repo: RepoModel) {
        val bundle = bundleOf(RepoModel.TAG to repo)
        findNavController().navigate(R.id.nav_repo_item, bundle)
        //findNavController().navigate(R.id.action_RepoListFragment_to_RepoItemFragment, bundle)
    }
    /// Implements RepoListAdapter.OnSearchListener
//    override fun onSearchResult(query: String): MutableList<RepoModel>? {
//        Log.e(TAG,"onSearchResult---------------------------------query=$query")
//        return viewModel.onSearch(query)
//    }

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

    /// Menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.repolist, menu)
        val menuItem = menu.findItem(R.id.menu_search)
        val searchView = menuItem?.actionView as SearchView

        //https://spin.atomicobject.com/2019/11/11/how-to-create-a-searchview-with-suggestions-in-kotlin/
        searchView.queryHint = getString(R.string.search)
        searchView.findViewById<AutoCompleteTextView>(R.id.search_src_text).threshold = 3
        val from = arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1)
        val to = intArrayOf(R.id.item_label)
        val cursorAdapter = SimpleCursorAdapter(
            context,
            R.layout.suggestion_item,
            null,
            from,
            to,
            CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER)
        val suggestions = listOf(
            "user:ccasanoval",
            "GitHubViewer language:kotlin",
            "GitHubViewer user:ccasanoval"
        )
        searchView.suggestionsAdapter = cursorAdapter
        searchView.setOnSuggestionListener(object: SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {
                return false
            }
            override fun onSuggestionClick(position: Int): Boolean {
                val cursor = searchView.suggestionsAdapter.getItem(position) as Cursor
                val selection = cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
                val goDirectly = true
                searchView.setQuery(selection, goDirectly)
                if(goDirectly)hideKeyboard()
                return true
            }
        })

        searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.onSearch(query)
                    return false
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    val cursor = MatrixCursor(arrayOf(BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1))
                    query?.let {
                        suggestions.forEachIndexed { index, suggestion ->
                            if(suggestion.contains(query, true))
                                cursor.addRow(arrayOf(index, suggestion))
                        }
                    }
                    cursorAdapter.changeCursor(cursor)
                    return true
                }
            })
        }
        menuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                viewModel.onSearchClose()
                return true // Return true to collapse action view
            }
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
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
