package com.cesoft.githubviewer.data

import com.cesoft.githubviewer.data.remote.GitHubApiImpl

////////////////////////////////////////////////////////////////////////////////////////////////////
//
object Repository {

    //private val local = ...
    private val remote = GitHubApiImpl

    fun getPage() = remote.page +1
    suspend fun getRepoListPrev(): MutableList<RepoModel> { // = remote.getRepoListPrevPage()
        val res = remote.getRepoListPrevPage()
        return if(res != null) {
            MutableList(res.size) { i -> res[i].toModel() }
        }
        else {
            mutableListOf()
        }
    }
    suspend fun getRepoListNext(query: String?=null): MutableList<RepoModel> { // = remote.getRepoListNextPage(query)
        val res = remote.getRepoListNextPage(query)
        return if(res != null) {
            MutableList(res.size) { i -> res[i].toModel() }
        }
        else {
            mutableListOf()
        }
    }

}