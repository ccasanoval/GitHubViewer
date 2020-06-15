package com.cesoft.githubviewer.data.remote

import android.util.Log
import com.cesoft.githubviewer.App
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
    private const val CACHE_SIZE = 100 * 1024L      //bytes
    private const val CACHE_MAX_AGE = 5*60             //seconds
    private const val CACHE_MAX_STALE = 24*60*60 //seconds
    private const val GITHUB_API_URL = "https://api.github.com/"

    private var maxSearchPage = 0
    private var searchQuery: String? = null
    private val isSearching
        get() = searchQuery != null

    private var currentPage = -1
    private val index = HashMap<Int, Int>()
    val page: Int
        get() = currentPage

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
                chain.proceed(request)
            }
            .addInterceptor(interceptor)
            .build()
    }

    private fun updateIndex(res: Response<List<RepoEntity>>) {
        res.headers()["link"]?.let { link ->
            Log.e(TAG, "------------------------------------link=$link")
            val target = "https://api.github.com/repositories?since="
            val rel = "rel=\"next\""
            val i = link.indexOf(target)
            if(i > 0) {
                val tmp = link.substring(i+target.length)
                val j = tmp.indexOf('>')
                index[currentPage + 1] = tmp.substring(0, j).toInt()
                Log.e(TAG, "------------------------------------index[$currentPage+1]=${index[currentPage + 1]}")
            }
        }
    }
    private suspend fun getRepoList(): List<RepoEntity>? {
        val since = index[currentPage]
        android.util.Log.e("API", "getRepoListSince----------------------since=$since")

        val res = api.getRepoList(since)
        updateIndex(res)

        android.util.Log.e("API", "OK--------------"+res.isSuccessful)
        android.util.Log.e("API", "LINK--------------"+ res.headers()["link"])

        if(res.isSuccessful)
            return res.body()
        else
            return res.body()//TODO...................
    }


    private fun updatePage(res: Response<SearchRepoEntity>) {
        if(maxSearchPage > 0)return
        //link: <https://api.github.com/search/repositories?q=kotlin&page=2>; rel="next", <https://api.github.com/search/repositories?q=kotlin&page=34>; rel="last"
        res.headers()["link"]?.let { link ->
            Log.e(TAG, "------------------------------------link=$link")
            try {
                val targetIni = "&page="
                val targetEnd = ">; rel=\"last\""
                val i = link.lastIndexOf(targetIni)
                val j = link.lastIndexOf(targetEnd)
                maxSearchPage = if (i in 1 until j) {
                    link.substring(i + targetIni.length, j).toInt()
                } else 0
                Log.e(TAG, "updatePage------------------------------------maxSearchPage=$maxSearchPage")
            }
            catch(e: Exception) {
                Log.e(TAG, "updatePage:e: ($link) ",e)
            }
        }
    }
    private suspend fun getRepoListSearch(): List<RepoEntity>? {
        Log.e(TAG, "getRepoListSearch---------------------------searchQuery=$searchQuery")

        val res = api.getRepoListSearch(searchQuery!!, currentPage)

        Log.e(TAG, "getRepoListSearch---------------------------count=${res.body()?.count}")
        Log.e(TAG, "getRepoListSearch---------------------------isIncomplete=${res.body()?.isIncomplete}")
        Log.e(TAG, "getRepoListSearch---------------------------items=${res.body()?.items?.size}")

        updatePage(res)

        if(res.isSuccessful)
            return res.body()?.items
        else
            return res.body()?.items//TODO...................
    }

    suspend fun getRepoListPrevPage(): List<RepoEntity>? {
        if(currentPage > 0) {
            currentPage--
        }

        return if(isSearching) {
            getRepoListSearch()
        } else {
            getRepoList()
        }
    }

    suspend fun getRepoListNextPage(query: String?=null): List<RepoEntity>? {
        Log.e(TAG, "getRepoListNextPage---------------------------query=$query maxSearchPage=$maxSearchPage")
        if(query != searchQuery) {
            searchQuery = query
            if(isSearching) {
                currentPage = 1
                maxSearchPage = 0
            }
            else {
                currentPage = -1
            }
        }
        if(!isSearching || currentPage < maxSearchPage)
            currentPage++

        return if(isSearching) {
            getRepoListSearch()
        } else {
            getRepoList()
        }
    }

    suspend fun getRepoListSamePage(): List<RepoEntity>? {
        Log.e(TAG, "getRepoListSamePage--------------------------- maxSearchPage=$maxSearchPage")
        return if(isSearching) {
            getRepoListSearch()
        } else {
            getRepoList()
        }
    }
}