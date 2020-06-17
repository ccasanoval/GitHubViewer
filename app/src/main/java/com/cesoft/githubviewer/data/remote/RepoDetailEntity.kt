package com.cesoft.githubviewer.data.remote

import androidx.annotation.Keep
import com.cesoft.githubviewer.data.RepoDetailModel
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

////////////////////////////////////////////////////////////////////////////////////////////////////
//
@Keep
data class RepoDetailEntity(
    @SerializedName("id")
    val id: String?,
    @SerializedName("node_id")
    val nodeId: String?,
    @SerializedName("owner")
    val owner: OwnerEntity?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("full_name")
    val fullName: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("html_url")
    val htmlUrl: String?,
    @SerializedName("url")
    val url: String?,
    /// Details
    @SerializedName("created_at")
    val createdAt: String?,//"2007-10-29T14:37:16Z",
    @SerializedName("updated_at")
    val updatedAt: String?,//"2020-06-14T09:36:03Z",
    @SerializedName("pushed_at")
    val pushedAt: String?,//"2018-11-08T13:16:24Z",
    @SerializedName("homepage")
    val homepage: String?,
    @SerializedName("language")
    val language: String?,
    @SerializedName("size")
    val size: Long?
) {
    companion object {
        private const val pattern = "yyyy-MM-dd'T'hh:mm:ss'Z'"
        private val dateFormat = SimpleDateFormat(pattern, Locale.ROOT)
        //@RequiresApi(Build.VERSION_CODES.O) DateTimeFormatter.ISO_LOCAL_TIME
    }
    fun toModel(): RepoDetailModel {
        val ownerModel = owner?.toModel()
        return RepoDetailModel(
            id = id,
            nodeId = nodeId,
            owner = ownerModel,
            name = name,
            fullName = fullName,
            description = description,
            htmlUrl = htmlUrl,
            url = url,
            /// Details
            createdAt = createdAt?.let { dateFormat.parse(createdAt) },
            updatedAt = updatedAt?.let { dateFormat.parse(updatedAt) },
            pushedAt = pushedAt?.let { dateFormat.parse(pushedAt) },
            homepage = homepage,
            language = language,
            size = size
        )
    }
}

/**
{
"id": 1,
"node_id": "MDEwOlJlcG9zaXRvcnkx",
"name": "grit",
"full_name": "mojombo/grit",
"private": false,
"owner": {
    "login": "mojombo",
    "id": 1,
    "node_id": "MDQ6VXNlcjE=",
    "avatar_url": "https://avatars0.githubusercontent.com/u/1?v=4",
    "gravatar_id": "",
    "url": "https://api.github.com/users/mojombo",
    "html_url": "https://github.com/mojombo",
    "followers_url": "https://api.github.com/users/mojombo/followers",
    "following_url": "https://api.github.com/users/mojombo/following{/other_user}",
    "gists_url": "https://api.github.com/users/mojombo/gists{/gist_id}",
    "starred_url": "https://api.github.com/users/mojombo/starred{/owner}{/repo}",
    "subscriptions_url": "https://api.github.com/users/mojombo/subscriptions",
    "organizations_url": "https://api.github.com/users/mojombo/orgs",
    "repos_url": "https://api.github.com/users/mojombo/repos",
    "events_url": "https://api.github.com/users/mojombo/events{/privacy}",
    "received_events_url": "https://api.github.com/users/mojombo/received_events",
    "type": "User",
    "site_admin": false
},
"html_url": "https://github.com/mojombo/grit",
"description": "**Grit is no longer maintained. Check out libgit2/rugged.** Grit gives you object oriented read/write access to Git repositories via Ruby.",
"fork": false,
"url": "https://api.github.com/repos/mojombo/grit",
"forks_url": "https://api.github.com/repos/mojombo/grit/forks",
"keys_url": "https://api.github.com/repos/mojombo/grit/keys{/key_id}",
"collaborators_url": "https://api.github.com/repos/mojombo/grit/collaborators{/collaborator}",
"teams_url": "https://api.github.com/repos/mojombo/grit/teams",
"hooks_url": "https://api.github.com/repos/mojombo/grit/hooks",
"issue_events_url": "https://api.github.com/repos/mojombo/grit/issues/events{/number}",
"events_url": "https://api.github.com/repos/mojombo/grit/events",
"assignees_url": "https://api.github.com/repos/mojombo/grit/assignees{/user}",
"branches_url": "https://api.github.com/repos/mojombo/grit/branches{/branch}",
"tags_url": "https://api.github.com/repos/mojombo/grit/tags",
"blobs_url": "https://api.github.com/repos/mojombo/grit/git/blobs{/sha}",
"git_tags_url": "https://api.github.com/repos/mojombo/grit/git/tags{/sha}",
"git_refs_url": "https://api.github.com/repos/mojombo/grit/git/refs{/sha}",
"trees_url": "https://api.github.com/repos/mojombo/grit/git/trees{/sha}",
"statuses_url": "https://api.github.com/repos/mojombo/grit/statuses/{sha}",
"languages_url": "https://api.github.com/repos/mojombo/grit/languages",
"stargazers_url": "https://api.github.com/repos/mojombo/grit/stargazers",
"contributors_url": "https://api.github.com/repos/mojombo/grit/contributors",
"subscribers_url": "https://api.github.com/repos/mojombo/grit/subscribers",
"subscription_url": "https://api.github.com/repos/mojombo/grit/subscription",
"commits_url": "https://api.github.com/repos/mojombo/grit/commits{/sha}",
"git_commits_url": "https://api.github.com/repos/mojombo/grit/git/commits{/sha}",
"comments_url": "https://api.github.com/repos/mojombo/grit/comments{/number}",
"issue_comment_url": "https://api.github.com/repos/mojombo/grit/issues/comments{/number}",
"contents_url": "https://api.github.com/repos/mojombo/grit/contents/{+path}",
"compare_url": "https://api.github.com/repos/mojombo/grit/compare/{base}...{head}",
"merges_url": "https://api.github.com/repos/mojombo/grit/merges",
"archive_url": "https://api.github.com/repos/mojombo/grit/{archive_format}{/ref}",
"downloads_url": "https://api.github.com/repos/mojombo/grit/downloads",
"issues_url": "https://api.github.com/repos/mojombo/grit/issues{/number}",
"pulls_url": "https://api.github.com/repos/mojombo/grit/pulls{/number}",
"milestones_url": "https://api.github.com/repos/mojombo/grit/milestones{/number}",
"notifications_url": "https://api.github.com/repos/mojombo/grit/notifications{?since,all,participating}",
"labels_url": "https://api.github.com/repos/mojombo/grit/labels{/name}",
"releases_url": "https://api.github.com/repos/mojombo/grit/releases{/id}",
"deployments_url": "https://api.github.com/repos/mojombo/grit/deployments",

"created_at": "2007-10-29T14:37:16Z",
"updated_at": "2020-06-14T09:36:03Z",
"pushed_at": "2018-11-08T13:16:24Z",

"git_url": "git://github.com/mojombo/grit.git",
"ssh_url": "git@github.com:mojombo/grit.git",
"clone_url": "https://github.com/mojombo/grit.git",
"svn_url": "https://github.com/mojombo/grit",
"homepage": "http://grit.rubyforge.org/",

"size": 7954,

"stargazers_count": 1890,
"watchers_count": 1890,

"language": "Ruby",

"has_issues": true,
"has_projects": true,
"has_downloads": true,
"has_wiki": true,
"has_pages": false,
"forks_count": 521,
"mirror_url": null,
"archived": false,
"disabled": false,
"open_issues_count": 23,
"license": {
    "key": "mit",
    "name": "MIT License",
    "spdx_id": "MIT",
    "url": "https://api.github.com/licenses/mit",
    "node_id": "MDc6TGljZW5zZTEz"
},
"forks": 521,
"open_issues": 23,
"watchers": 1890,
"default_branch": "master",
"temp_clone_token": null,
"network_count": 521,
"subscribers_count": 64
}
 */