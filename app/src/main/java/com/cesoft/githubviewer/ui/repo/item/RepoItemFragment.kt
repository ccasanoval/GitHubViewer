package com.cesoft.githubviewer.ui.repo.item

import android.content.Intent
import android.net.Uri
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
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_repo_item.*
import java.text.SimpleDateFormat
import java.util.*

////////////////////////////////////////////////////////////////////////////////////////////////////
// TODO: make a call to the repo api to get extra data !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// TODO: ERrores de web 400 , 300 ...
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
            }
                ?: run {
                findNavController().popBackStack()
                //TODO: Show error message...
            }
        }

        fabViewInWeb.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(repo?.htmlUrl))
            startActivity(browserIntent)
        }

        viewModel.data.observe(viewLifecycleOwner, Observer { data ->
            repoDetail = data
            initFields()
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

                createdAt.text = "$CREATED?"
                repo.createdAt?.let { date ->
                    createdAt.text = "$CREATED${dateFormat.format(date)}"
                }
                updatedAt.text = "$UPDATED?"
                repo.updatedAt?.let { date ->
                    updatedAt.text = "$UPDATED${dateFormat.format(date)}"
                }
                pushedAt.text = "$PUSHED?"
                repo.pushedAt?.let { date ->
                    pushedAt.text = "$PUSHED${dateFormat.format(date)}"
                }
            }
        }
    }

    companion object {
        private val TAG: String = RepoItemFragment::class.simpleName!!
        private val dateFormat = SimpleDateFormat.getDateTimeInstance()
        private const val CREATED = "Created: "
        private const val UPDATED = "Updated: "
        private const val PUSHED =  "Pushed: "
    }
}