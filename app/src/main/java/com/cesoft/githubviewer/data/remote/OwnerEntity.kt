package com.cesoft.githubviewer.data.remote

import androidx.annotation.Keep
import com.cesoft.githubviewer.data.OwnerModel
import com.google.gson.annotations.SerializedName

////////////////////////////////////////////////////////////////////////////////////////////////////
//
@Keep
data class OwnerEntity(
    @SerializedName("id")
    val id: Int,
    @SerializedName("node_id")
    val nodeId: String,
    @SerializedName("login")
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("html_url")
    val htmlUrl: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("site_admin")
    val isSiteAdmin: Boolean
) {
    fun toModel() = OwnerModel(
        id = id,
        nodeId = nodeId,
        login = login,
        avatarUrl = avatarUrl,
        url = url,
        htmlUrl = htmlUrl,
        type = type,
        isSiteAdmin = isSiteAdmin)
}
