package com.cesoft.githubviewer.ui.repo.item

import androidx.lifecycle.LiveData
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

    private val _data = MutableLiveData<RepoDetailModel>()
    val data: LiveData<RepoDetailModel>
        get() = _data

    private val _error = MutableLiveData<Int>()
    val error: LiveData<Int>
        get() = _error

    init {
        GlobalScope.launch(Dispatchers.IO) {
            if(repo.fullName != null) {
                repoDetails = Repository.getRepoDetails(repo.fullName)
            }
            else if(repo.owner?.login != null && repo.name != null) {
                repoDetails = Repository.getRepoDetails(repo.owner.login, repo.name)
            }
            if(repoDetails == null) {
                repoDetails = RepoDetailModel.fromRepo(repo)
                _error.postValue(Repository.getLastErrorCode())
            }
            _data.postValue(repoDetails)
        }
    }

    companion object {
        private val TAG: String = RepoItemViewModel::class.simpleName!!
    }
}
