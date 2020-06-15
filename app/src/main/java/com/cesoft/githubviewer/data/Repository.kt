package com.cesoft.githubviewer.data

import com.cesoft.githubviewer.data.remote.GitHubApiImpl
import kotlinx.coroutines.sync.withLock

////////////////////////////////////////////////////////////////////////////////////////////////////
//
object Repository {

    //private val local = ...
    private val remote = GitHubApiImpl

    fun getPageMax() = remote.pageMax
    fun getPage() = remote.page
    suspend fun getRepoListPrev(): MutableList<RepoModel> {
        val res = remote.getRepoListPrevPage()
        return if (res != null) {
            MutableList(res.size) { i -> res[i].toModel() }
        } else {
            mutableListOf()
        }
    }
    suspend fun getRepoListNext(query: String?=null): MutableList<RepoModel> {
        val res = remote.getRepoListNextPage(query)
        return if (res != null) {
            MutableList(res.size) { i -> res[i].toModel() }
        } else {
            mutableListOf()
        }
    }
    suspend fun getRepoListSame(): MutableList<RepoModel> {
        val res = remote.getRepoListSamePage()
        return if (res != null) {
            MutableList(res.size) { i -> res[i].toModel() }
        } else {
            mutableListOf()
        }
    }
}