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
// TODO: progress icon !!
class RepoListViewModel : ViewModel() {

    private var currentQuery: String? = null

    private val _list = MutableLiveData<MutableList<RepoModel>>()
    val list: LiveData<MutableList<RepoModel>>
        get() = _list

    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status>
        get() = _status

    init {
        GlobalScope.launch(Dispatchers.IO) {
            val repos = Repository.getRepoListSame()
            processRes(repos)
        }
    }

    private fun processRes(repos: MutableList<RepoModel>) {
        Log.e(TAG, "processRes-------------------------------------------------------------------------------"+Repository.getPage()+" : "+Repository.getPageMax()+" "+repos.size)
        _list.postValue(repos)
        _status.postValue(Status(Repository.getPage(), Repository.getPageMax(), repos.size))
    }

    fun goPrev() {
        GlobalScope.launch(Dispatchers.IO) {
            Log.e(TAG, "goPrev-------------------------------------------------------------------------------")
            val repos = Repository.getRepoListPrev()
            processRes(repos)
        }
    }
    fun goNext(query: String?=null) {
        Log.e(TAG, "goNext-------------------------------------------------------------------------------")
        GlobalScope.launch(Dispatchers.IO) {
            val realQuery = query ?: currentQuery
            val repos = Repository.getRepoListNext(realQuery)
            processRes(repos)
        }
    }

    fun onSearch(query: String): MutableList<RepoModel>? = runBlocking(Dispatchers.IO) {
        Log.e(TAG, "onSearch-------------------------------------------------------------------------------")
        currentQuery = query
        val repos = Repository.getRepoListNext(currentQuery)
        processRes(repos)
        repos
    }
    fun onSeachClose() {
        Log.e(TAG, "onSeachClose-------------------------------------------------------------------------------")
        if(currentQuery != null) {
            currentQuery = null
            GlobalScope.launch(Dispatchers.IO) {
                val repos = Repository.getRepoListNext(currentQuery)
                processRes(repos)
            }
        }
    }

    companion object {
        private val TAG: String = RepoListViewModel::class.simpleName!!
    }
}