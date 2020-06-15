package com.cesoft.githubviewer.ui.repo.item

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.cesoft.githubviewer.R
import com.cesoft.githubviewer.data.RepoModel
import kotlinx.android.synthetic.main.fragment_repo_item.*

////////////////////////////////////////////////////////////////////////////////////////////////////
// TODO: make a call to the repo api to get extra data
class RepoItemFragment : Fragment() {

    private var viewModel = RepoItemViewModel()
    var repo: RepoModel? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_repo_item, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val arg = arguments?.get(RepoModel.TAG)
        arg?.let {
            repo = arguments?.get(RepoModel.TAG) as RepoModel
            initFields()
        }

        fabViewInWeb.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(repo?.htmlUrl))
            startActivity(browserIntent)
        }
        Log.e(TAG, "onActivityCreated----------------------------------------------------------------")
    }

    private fun initFields() {
        Log.e(TAG, "initFields----------------------------------------------------------------")
        repo?.let { repo ->
            name.text = repo.name
            owner.text = repo.owner?.login
            description.text = repo.description
            Glide.with(this).load(repo.owner?.avatarUrl).into(image)
        }
    }

    companion object {
        private val TAG: String = RepoItemFragment::class.simpleName!!
    }
}