package com.cesoft.githubviewer.ui.repo.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.cesoft.githubviewer.BuildConfig
import com.cesoft.githubviewer.data.RepoModel
import com.cesoft.githubviewer.data.Repository
import com.cesoft.githubviewer.data.remote.RepoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


////////////////////////////////////////////////////////////////////////////////////////////////////
// TODO: cuando se recrea la ventana por cambio de orientacion se pide la siguiente pagina, nor!
class RepoListViewModel : ViewModel() {

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
        _list.postValue(repos)
        _status.postValue(Status(Repository.getPage(), repos.size))
    }

    fun goPrev() {
        GlobalScope.launch(Dispatchers.IO) {
            val repos = Repository.getRepoListPrev()
            processRes(repos)
        }
    }
    fun goNext(query: String?=null) {
        GlobalScope.launch(Dispatchers.IO) {
            val repos = Repository.getRepoListNext(query)
            processRes(repos)
        }
    }

    companion object {
        private val TAG: String = RepoListViewModel::class.simpleName!!
    }
}