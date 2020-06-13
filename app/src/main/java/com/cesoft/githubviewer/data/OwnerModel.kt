package com.cesoft.githubviewer.data

////////////////////////////////////////////////////////////////////////////////////////////////////
//
data class OwnerModel(
    val id: Int,
    val nodeId: String,
    val login: String,
    val avatarUrl: String,
    val url: String,
    val htmlUrl: String,
    val type: String,
    val isSiteAdmin: Boolean
)