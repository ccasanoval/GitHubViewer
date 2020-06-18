package com.cesoft.githubviewer.data.remote

import android.util.Log
import com.cesoft.githubviewer.App
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

////////////////////////////////////////////////////////////////////////////////////////////////////
//
object GitHubApiImpl {
    private val TAG: String = GitHubApiImpl::class.simpleName!!
    private const val CACHE_SIZE = 5*1024*1024L  //bytes
    private const val CACHE_MAX_AGE = 10*60      //seconds
    private const val CACHE_MAX_STALE = 24*60*60 //seconds
    private const val GITHUB_API_URL = "https://api.github.com/"

    private val mutex = Mutex()

    /// Search fetch fields
    private var maxSearchPage = 0
    private var searchQuery: String? = null
    private val isSearching
        get() = searchQuery != null
    val pageMax: Int
        get() = maxSearchPage

    /// Normal fetch fields
    private var currentPage = 0
    private val index = HashMap<Int, Int>()
    val page: Int
        get() = if(isSearching) currentPage else currentPage+1

    /// Error
    var lastErrorCode: Int = 0

    /// GitHub API
    private val api: GitHubApi by lazy {
        return@lazy Retrofit.Builder()
            .baseUrl(GITHUB_API_URL)
            .client(createHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHubApi::class.java)
    }

    private fun createHttpClient(): OkHttpClient {
        val cache = Cache(App.getInstance().cacheDir, CACHE_SIZE)

        val interceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.e(TAG, "HttpLoggingInterceptor: $message")
            }
        })
        interceptor.level = HttpLoggingInterceptor.Level.HEADERS//BASIC//NONE

        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor { chain ->
                var request = chain.request()
                request = if(Util.isOnline(App.getInstance())) {
                    request.newBuilder().header("Cache-Control","public, max-age=$CACHE_MAX_AGE").build()
                }
                else {
                    request.newBuilder().header("Cache-Control","public, only-if-cached, max-stale=$CACHE_MAX_STALE").build()
                }
                val res = chain.proceed(request)
                if(res.networkResponse!=null)
                    Log.e(TAG, "*********** NETWORK RESPONSE *************")
                if(res.cacheResponse!=null)
                    Log.e(TAG, "************ CACHE RESPONSE **************")
                res
            }
            .addInterceptor(interceptor)
            .build()
    }

    /// Normal fetch functions
    private fun updateIndex(res: Response<List<RepoEntity>>) {
        res.headers()["link"]?.let { link ->
            val target = "https://api.github.com/repositories?since="
            //val rel = "rel=\"next\""
            val i = link.indexOf(target)
            if(i > 0) {
                val tmp = link.substring(i+target.length)
                val j = tmp.indexOf('>')
                index[currentPage + 1] = tmp.substring(0, j).toInt()
            }
        }
    }
    private suspend fun getRepoList(): List<RepoEntity>? {
        val since = index[currentPage]
        val res = api.getRepoList(since)
        return if(res.isSuccessful) {
            lastErrorCode = 0
            updateIndex(res)
            res.body()
        }
        else {
            currentPage--
            lastErrorCode = res.code()
            null
        }
    }


    /// Search fetch functions
    private fun updateSearchPage(res: Response<SearchRepoEntity>) {
        if(maxSearchPage > 0)return
        currentPage = 1

        //link: <https://api.github.com/search/repositories?q=kotlin&page=2>; rel="next", <https://api.github.com/search/repositories?q=kotlin&page=34>; rel="last"
        res.headers()["link"]?.let { link ->
            try {
                val targetIni = "&page="
                val targetEnd = ">; rel=\"last\""
                val i = link.lastIndexOf(targetIni)
                val j = link.lastIndexOf(targetEnd)
                maxSearchPage = if (i in 1 until j) {
                    link.substring(i + targetIni.length, j).toInt()
                }
                else 0
            }
            catch(e: Exception) {
                Log.e(TAG, "updateSearchPage:e: ($link) ",e)
            }
        } ?: run {
            maxSearchPage = 1
        }
    }
    private suspend fun getRepoListSearch(): List<RepoEntity>? {
        val res = api.getRepoListSearch(searchQuery!!, currentPage)
        return if(res.isSuccessful) {
            updateSearchPage(res)
            lastErrorCode = 0
            res.body()?.items
        }
        else {
            currentPage--
            lastErrorCode = res.code()
            null
        }
    }

    private fun processRepoDetail(res: Response<RepoDetailEntity>): RepoDetailEntity? {
        return if(res.isSuccessful) {
            lastErrorCode = 0
            res.body()
        }
        else {
            lastErrorCode = res.code()
            null
        }
    }

    private fun updatePagePrev() {
        if(isSearching) {
            if(currentPage > 1) {
                currentPage--
            }
        }
        else {
            if(currentPage > 0) {
                currentPage--
            }
        }
    }
    private fun restartPage() {
        if(isSearching) {
            currentPage = 0
            maxSearchPage = 0
        }
        else {
            currentPage = -1
        }
    }

    //----------------------------------------------------------------------------------------------
    // INTERFACE

    suspend fun getRepoListPrevPage(): List<RepoEntity>? {
        mutex.withLock {
            updatePagePrev()
            return if(isSearching) {
                getRepoListSearch()
            }
            else {
                getRepoList()
            }
        }
    }
    suspend fun getRepoListNextPage(query: String?=null): List<RepoEntity>? {
        mutex.withLock {
            if(query != searchQuery) {
                searchQuery = query
                restartPage()
            }

            return if(isSearching) {
                if(currentPage < maxSearchPage)
                    currentPage++
                getRepoListSearch()
            }
            else {
                currentPage++
                getRepoList()
            }
        }
    }
    suspend fun getRepoListSamePage(): List<RepoEntity>? {
        mutex.withLock {
            return if(isSearching) {
                getRepoListSearch()
            }
            else {
                getRepoList()
            }
        }
    }

    suspend fun getRepoDetail(owner: String, repo: String): RepoDetailEntity? {
        mutex.withLock {
            val res = api.getRepoDetail(owner, repo)
            return processRepoDetail(res)
        }
    }
    suspend fun getRepoDetail(path: String): RepoDetailEntity? {
        mutex.withLock {
            val res = api.getRepoDetail(path)
            return processRepoDetail(res)
        }
    }
}
