package com.cesoft.githubviewer.data.remote

import retrofit2.Response
import retrofit2.http.*

////////////////////////////////////////////////////////////////////////////////////////////////////
//
//https://www.andreasjakl.com/how-to-retrofit-moshi-coroutines-recycler-view-for-rest-web-service-operations-with-kotlin-for-android/
//https://blog.hipolabs.com/api-search-with-pagination-for-android-development-in-2020-f47717fb34ac
interface GitHubApi {

//    @GET("/repositories")
//    suspend fun getAllRepos(
//        //https://developer.github.com/v3/#pagination
//        //Note: It's important to form calls with Link header values instead of constructing your own URLs.
//        //So GitHub doesn't care about this pagination parameters!! make use of headers ones
//        //@Query("page")page: Int,
//        //@Query("per_page")perPage: Int
//    ): List<RepoEntity>

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