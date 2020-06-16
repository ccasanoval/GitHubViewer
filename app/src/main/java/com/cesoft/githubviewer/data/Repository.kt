package com.cesoft.githubviewer.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.cesoft.githubviewer.data.remote.GitHubApiImpl
import kotlinx.coroutines.sync.withLock

////////////////////////////////////////////////////////////////////////////////////////////////////
//
object Repository {
    private val TAG: String = Repository::class.simpleName!!

    //private val local = ...
    private val remote = GitHubApiImpl

    fun getPageMax() = remote.pageMax
    fun getPage() = remote.page
    suspend fun getRepoListPrev(): MutableList<RepoModel>? {
        val res = remote.getRepoListPrevPage()
        return if(res != null) {
            MutableList(res.size) { i -> res[i].toModel() }
        }
        else null
    }
    suspend fun getRepoListNext(query: String?=null): MutableList<RepoModel>? {
        Log.e(TAG,"getRepoListNext---------------------------query=$query")

        val res = remote.getRepoListNextPage(query)
        return if(res != null) {
            MutableList(res.size) { i -> res[i].toModel() }
        }
        else null
    }
    suspend fun getRepoListSame(): MutableList<RepoModel>? {
        val res = remote.getRepoListSamePage()
        return if (res != null) {
            MutableList(res.size) { i -> res[i].toModel() }
        }
        else null
    }
    suspend fun getRepoDetails(owner: String, repo: String): RepoDetailModel? {
        return remote.getRepoDetail(owner, repo)?.toModel()
    }
    suspend fun getRepoDetails(path: String): RepoDetailModel? {
        return remote.getRepoDetail(path)?.toModel()
    }
    fun getLastErrorCode() = remote.lastErrorCode
}