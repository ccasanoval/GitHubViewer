package com.cesoft.githubviewer.data

import com.google.gson.annotations.SerializedName
import java.util.*

data class RepoDetailModel(
    val id: String?,
    val nodeId: String?,
    val owner: OwnerModel?,
    val name: String?,
    val fullName: String?,
    val description: String?,
    val htmlUrl: String?,
    val url: String?,
    /// Details
    val createdAt: Date?,
    val updatedAt: Date?,
    val pushedAt: Date?,
    val homepage: String,
    val language: String,
    val size: Long
)
