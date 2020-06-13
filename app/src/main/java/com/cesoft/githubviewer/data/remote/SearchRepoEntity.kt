package com.cesoft.githubviewer.data.remote

import com.google.gson.annotations.SerializedName

////////////////////////////////////////////////////////////////////////////////////////////////////
//
data class SearchRepoEntity(
    @SerializedName("total_count")
    val count: Int,
    @SerializedName("incomplete_results")
    val isIncomplete: Boolean,
    @SerializedName("items")
    val items: List<RepoEntity>
)

/**
{
"total_count": 97125,
"incomplete_results": false,
"items": [
{
"id": 3432266,
"node_id": "MDEwOlJlcG9zaXRvcnkzNDMyMjY2",
"name": "kotlin",
"full_name": "JetBrains/kotlin",
"private": false,
"owner": {
"login": "JetBrains",
"id": 878437,
"node_id": "MDEyOk9yZ2FuaXphdGlvbjg3ODQzNw==",
"avatar_url": "https://avatars2.githubusercontent.com/u/878437?v=4",
"gravatar_id": "",
"url": "https://api.github.com/users/JetBrains",
"html_url": "https://github.com/JetBrains",
"followers_url": "https://api.github.com/users/JetBrains/followers",
"following_url": "https://api.github.com/users/JetBrains/following{/other_user}",
"gists_url": "https://api.github.com/users/JetBrains/gists{/gist_id}",
"starred_url": "https://api.github.com/users/JetBrains/starred{/owner}{/repo}",
"subscriptions_url": "https://api.github.com/users/JetBrains/subscriptions",
"organizations_url": "https://api.github.com/users/JetBrains/orgs",
"repos_url": "https://api.github.com/users/JetBrains/repos",
"events_url": "https://api.github.com/users/JetBrains/events{/privacy}",
"received_events_url": "https://api.github.com/users/JetBrains/received_events",
"type": "Organization",
"site_admin": false
},
"html_url": "https://github.com/JetBrains/kotlin",
"description": "The Kotlin Programming Language",
"fork": false,
"url": "https://api.github.com/repos/JetBrains/kotlin",
"forks_url": "https://api.github.com/repos/JetBrains/kotlin/forks",
"keys_url": "https://api.github.com/repos/JetBrains/kotlin/keys{/key_id}",
"collaborators_url": "https://api.github.com/repos/JetBrains/kotlin/collaborators{/collaborator}",
"teams_url": "https://api.github.com/repos/JetBrains/kotlin/teams",
"hooks_url": "https://api.github.com/repos/JetBrains/kotlin/hooks",
"issue_events_url": "https://api.github.com/repos/JetBrains/kotlin/issues/events{/number}",
"events_url": "https://api.github.com/repos/JetBrains/kotlin/events",
"assignees_url": "https://api.
...
 */