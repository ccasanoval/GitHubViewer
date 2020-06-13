package com.cesoft.githubviewer.ui.repo.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cesoft.githubviewer.R

////////////////////////////////////////////////////////////////////////////////////////////////////
//
class RepoItemFragment : Fragment() {

    private var viewModel = RepoItemViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_repo_item, container, false)
    }
}