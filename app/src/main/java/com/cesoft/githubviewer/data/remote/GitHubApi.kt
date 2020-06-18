package com.cesoft.githubviewer.data.remote

import retrofit2.Response
import retrofit2.http.*

////////////////////////////////////////////////////////////////////////////////////////////////////
//
interface GitHubApi {

    //----------------------------------------------------------------------------------------------
    //https://developer.github.com/v3/#pagination
    //https://developer.github.com/v3/repos/
    // Ej: https://api.github.com/search/repositories
    // Header: <https://api.github.com/repositories?since=369>; rel="next", <https://api.github.com/repositories{?since}>; rel="first"
    @Headers("Content-Type: application/json")
    @GET("/repositories")
    suspend fun getRepoList(@Query("since")since: Int?=null): Response<List<RepoEntity>>

    //----------------------------------------------------------------------------------------------
    //https://developer.github.com/v3/search/#search-repositories
    //https://developer.github.com/v3/search/#constructing-a-search-query
    // Ej: https://api.github.com/search/repositories?q=Kotlin
    // Header: <https://api.github.com/search/repositories?q=Kotlin&page=2>; rel="next", <https://api.github.com/search/repositories?q=Kotlin&page=34>; rel="last"
    @Headers("Content-Type: application/json")
    @GET("/search/repositories")
    suspend fun getRepoListSearch(
        @Query("q")query: String,
        @Query("page")page: Int?=null): Response<SearchRepoEntity>

    //https://developer.github.com/v3/repos/
    // Ej: https://api.github.com/repos/mojombo/grit
    @GET("/repos/{owner}/{repo}")
    suspend fun getRepoDetail(@Path("owner")owner: String, @Path("repo")repo: String): Response<RepoDetailEntity>
    @GET("/repos/{path}")
    suspend fun getRepoDetail(@Path("path", encoded = true)path: String): Response<RepoDetailEntity>
}
