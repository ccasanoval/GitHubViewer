package com.cesoft.githubviewer.data

////////////////////////////////////////////////////////////////////////////////////////////////////
//
data class RepoModel(
    val id: String?,
    val nodeId: String?,
    val owner: OwnerModel?,
    val name: String?,
    val fullName: String?,
    val description: String?,
    val htmlUrl: String?,
    val url: String?
)
