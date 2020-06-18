package com.cesoft.githubviewer.ui.repo.item

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.cesoft.githubviewer.R
import com.cesoft.githubviewer.data.RepoDetailModel
import com.cesoft.githubviewer.data.RepoModel
import com.cesoft.githubviewer.ui.MainActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_repo_item.*
import java.text.SimpleDateFormat

////////////////////////////////////////////////////////////////////////////////////////////////////
//
class RepoItemFragment : Fragment() {

    private lateinit var viewModel: RepoItemViewModel
    var repo: RepoModel? = null
    var repoDetail: RepoDetailModel? = null

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
            repo = it as RepoModel
            repo?.let { repo ->
                viewModel = RepoItemViewModel(repo)
            } ?: run {
                findNavController().popBackStack()
                Snackbar.make(root_layout, getString(R.string.api_error), Snackbar.LENGTH_LONG).show()
            }
        }

        fab.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.data.observe(viewLifecycleOwner, Observer { data ->
            repoDetail = data
            initFields()
        })
        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            when(error) {
                504 -> Snackbar.make(root_layout, getString(R.string.error_504), Snackbar.LENGTH_LONG).show()
                else -> Snackbar.make(root_layout, getString(R.string.api_error), Snackbar.LENGTH_LONG).show()
            }
        })
    }

    private fun initFields() {
        repoDetail?.let { repo ->
            activity?.let { act ->
                val activity = act as MainActivity
                activity.toolbar.title = repo.fullName

                name.text = repo.name
                owner.text = repo.owner?.login
                description.text = repo.description
                Glide.with(this).load(repo.owner?.avatarUrl).into(image)

                htmlUrl.text = repo.htmlUrl
                if(repo.language != null)
                    language.text = getString(R.string.language, repo.language)
                if(repo.size != null)
                    size.text = getString(R.string.size, repo.size)

                createdAt.text = getString(R.string.created_at, "?")
                repo.createdAt?.let { date ->
                    createdAt.text = getString(R.string.created_at, dateFormat.format(date))
                }
                updatedAt.text = getString(R.string.updated_at, "?")
                repo.updatedAt?.let { date ->
                    updatedAt.text = getString(R.string.updated_at, dateFormat.format(date))
                }
                pushedAt.text = getString(R.string.pushed_at, "?")
                repo.pushedAt?.let { date ->
                    pushedAt.text = getString(R.string.pushed_at, dateFormat.format(date))
                }
            }
        }
    }

    companion object {
        private val TAG: String = RepoItemFragment::class.simpleName!!
        private val dateFormat = SimpleDateFormat.getDateTimeInstance()

    }
}