package com.cesoft.githubviewer.ui.repo.item

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cesoft.githubviewer.data.RepoDetailModel
import com.cesoft.githubviewer.data.RepoModel
import com.cesoft.githubviewer.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

////////////////////////////////////////////////////////////////////////////////////////////////////
//
class RepoItemViewModel(val repo: RepoModel) : ViewModel() {

    private var repoDetails: RepoDetailModel? = null
    val data = MutableLiveData<RepoDetailModel>()

    init {
        GlobalScope.launch(Dispatchers.IO) {
            repoDetails = if(repo.fullName != null) {
                Repository.getRepoDetails(repo.fullName)
            }
            else if(repo.owner?.login != null && repo.name != null)
                Repository.getRepoDetails(repo.owner.login, repo.name)
            else
                RepoDetailModel.fromRepo(repo)
            Log.e(TAG, "-------------------------------------------$repoDetails")
            data.postValue(repoDetails)
        }
    }

    companion object {
        private val TAG: String = RepoItemViewModel::class.simpleName!!
    }
}
