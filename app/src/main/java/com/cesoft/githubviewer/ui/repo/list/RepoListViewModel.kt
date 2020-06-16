package com.cesoft.githubviewer.ui.repo.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cesoft.githubviewer.data.RepoModel
import com.cesoft.githubviewer.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


////////////////////////////////////////////////////////////////////////////////////////////////////
//
class RepoListViewModel : ViewModel() {

    private var currentQuery: String? = null

    private val _list = MutableLiveData<MutableList<RepoModel>>()
    val list: LiveData<MutableList<RepoModel>>
        get() = _list

    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status>
        get() = _status

    private val _isWorking = MutableLiveData<Boolean>()
    private fun workInProgress() = _isWorking.postValue(true)
    private fun workFinished() = _isWorking.postValue(false)
    val isWorking: LiveData<Boolean>
        get() = _isWorking

    init {
        GlobalScope.launch(Dispatchers.IO) {
            val repos = Repository.getRepoListSame()
            processRes(repos)
        }
    }

    private fun processRes(repos: MutableList<RepoModel>) {
        _list.postValue(repos)
        _status.postValue(Status(Repository.getPage(), Repository.getPageMax(), repos.size))
        workFinished()
    }

    fun goPrev() {
        GlobalScope.launch(Dispatchers.IO) {
            workInProgress()
            val repos = Repository.getRepoListPrev()
            processRes(repos)
        }
    }
    fun goNext(query: String?=null) {
        GlobalScope.launch(Dispatchers.IO) {
            workInProgress()
            val realQuery = query ?: currentQuery
            val repos = Repository.getRepoListNext(realQuery)
            processRes(repos)
        }
    }

    fun onSearch(query: String): MutableList<RepoModel>? = runBlocking(Dispatchers.IO) {
        workInProgress()
        currentQuery = query
        val repos = Repository.getRepoListNext(currentQuery)
        processRes(repos)
        repos
    }
    fun onSeachClose() {
        if(currentQuery != null) {
            currentQuery = null
            GlobalScope.launch(Dispatchers.IO) {
                workInProgress()
                val repos = Repository.getRepoListNext(currentQuery)
                processRes(repos)
            }
        }
    }

    companion object {
        private val TAG: String = RepoListViewModel::class.simpleName!!
    }
}