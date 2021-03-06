package com.cesoft.githubviewer.data.remote

import androidx.annotation.Keep
import com.cesoft.githubviewer.data.RepoModel
import com.google.gson.annotations.SerializedName

////////////////////////////////////////////////////////////////////////////////////////////////////
//
@Keep
data class RepoEntity(
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
    val url: String?
) {
    fun toModel(): RepoModel {
        val ownerModel = owner?.toModel()
        return RepoModel(
            id = id,
            nodeId = nodeId,
            owner = ownerModel,
            name = name,
            fullName = fullName,
            description = description,
            htmlUrl = htmlUrl,
            url = url)
    }
}

/**
Status: 200 OK
Link: <https://api.github.com/repositories?since=364>; rel="next"
[
{
"id": 1296269,
"node_id": "MDEwOlJlcG9zaXRvcnkxMjk2MjY5",
"name": "Hello-World",
"full_name": "octocat/Hello-World",
"owner": {
    "login": "octocat",
    "id": 1,
    "node_id": "MDQ6VXNlcjE=",
    "avatar_url": "https://github.com/images/error/octocat_happy.gif",
    "gravatar_id": "",
    "url": "https://api.github.com/users/octocat",
    "html_url": "https://github.com/octocat",
    "followers_url": "https://api.github.com/users/octocat/followers",
    "following_url": "https://api.github.com/users/octocat/following{/other_user}",
    "gists_url": "https://api.github.com/users/octocat/gists{/gist_id}",
    "starred_url": "https://api.github.com/users/octocat/starred{/owner}{/repo}",
    "subscriptions_url": "https://api.github.com/users/octocat/subscriptions",
    "organizations_url": "https://api.github.com/users/octocat/orgs",
    "repos_url": "https://api.github.com/users/octocat/repos",
    "events_url": "https://api.github.com/users/octocat/events{/privacy}",
    "received_events_url": "https://api.github.com/users/octocat/received_events",
    "type": "User",
    "site_admin": false
},
"private": false,
"html_url": "https://github.com/octocat/Hello-World",
"description": "This your first repo!",
"fork": false,
"url": "https://api.github.com/repos/octocat/Hello-World",
"archive_url": "http://api.github.com/repos/octocat/Hello-World/{archive_format}{/ref}",
"assignees_url": "http://api.github.com/repos/octocat/Hello-World/assignees{/user}",
"blobs_url": "http://api.github.com/repos/octocat/Hello-World/git/blobs{/sha}",
"branches_url": "http://api.github.com/repos/octocat/Hello-World/branches{/branch}",
"collaborators_url": "http://api.github.com/repos/octocat/Hello-World/collaborators{/collaborator}",
"comments_url": "http://api.github.com/repos/octocat/Hello-World/comments{/number}",
"commits_url": "http://api.github.com/repos/octocat/Hello-World/commits{/sha}",
"compare_url": "http://api.github.com/repos/octocat/Hello-World/compare/{base}...{head}",
"contents_url": "http://api.github.com/repos/octocat/Hello-World/contents/{+path}",
"contributors_url": "http://api.github.com/repos/octocat/Hello-World/contributors",
"deployments_url": "http://api.github.com/repos/octocat/Hello-World/deployments",
"downloads_url": "http://api.github.com/repos/octocat/Hello-World/downloads",
"events_url": "http://api.github.com/repos/octocat/Hello-World/events",
"forks_url": "http://api.github.com/repos/octocat/Hello-World/forks",
"git_commits_url": "http://api.github.com/repos/octocat/Hello-World/git/commits{/sha}",
"git_refs_url": "http://api.github.com/repos/octocat/Hello-World/git/refs{/sha}",
"git_tags_url": "http://api.github.com/repos/octocat/Hello-World/git/tags{/sha}",
"git_url": "git:github.com/octocat/Hello-World.git",
"issue_comment_url": "http://api.github.com/repos/octocat/Hello-World/issues/comments{/number}",
"issue_events_url": "http://api.github.com/repos/octocat/Hello-World/issues/events{/number}",
"issues_url": "http://api.github.com/repos/octocat/Hello-World/issues{/number}",
"keys_url": "http://api.github.com/repos/octocat/Hello-World/keys{/key_id}",
"labels_url": "http://api.github.com/repos/octocat/Hello-World/labels{/name}",
"languages_url": "http://api.github.com/repos/octocat/Hello-World/languages",
"merges_url": "http://api.github.com/repos/octocat/Hello-World/merges",
"milestones_url": "http://api.github.com/repos/octocat/Hello-World/milestones{/number}",
"notifications_url": "http://api.github.com/repos/octocat/Hello-World/notifications{?since,all,participating}",
"pulls_url": "http://api.github.com/repos/octocat/Hello-World/pulls{/number}",
"releases_url": "http://api.github.com/repos/octocat/Hello-World/releases{/id}",
"ssh_url": "git@github.com:octocat/Hello-World.git",
"stargazers_url": "http://api.github.com/repos/octocat/Hello-World/stargazers",
"statuses_url": "http://api.github.com/repos/octocat/Hello-World/statuses/{sha}",
"subscribers_url": "http://api.github.com/repos/octocat/Hello-World/subscribers",
"subscription_url": "http://api.github.com/repos/octocat/Hello-World/subscription",
"tags_url": "http://api.github.com/repos/octocat/Hello-World/tags",
"teams_url": "http://api.github.com/repos/octocat/Hello-World/teams",
"trees_url": "http://api.github.com/repos/octocat/Hello-World/git/trees{/sha}"
}
]
 */