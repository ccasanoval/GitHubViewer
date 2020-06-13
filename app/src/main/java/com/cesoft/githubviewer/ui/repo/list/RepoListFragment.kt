package com.cesoft.githubviewer.ui.repo.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.cesoft.githubviewer.R
import kotlinx.android.synthetic.main.fragment_repo_list.*

////////////////////////////////////////////////////////////////////////////////////////////////////
//
class RepoListFragment : Fragment() {

    private var viewModel = RepoListViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_repo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}