package com.cesoft.githubviewer.data

import java.util.Date

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
    val createdAt: Date?,//java.time.Instant?,
    val updatedAt: Date?,
    val pushedAt: Date?,
    val homepage: String?,
    val language: String?,
    val size: Long?
) {
    companion object {
        fun fromRepo(repo: RepoModel): RepoDetailModel {
            return RepoDetailModel(
                id = repo.id,
                nodeId = repo.nodeId,
                owner = repo.owner,
                name = repo.name,
                fullName = repo.fullName,
                description = repo.description,
                htmlUrl = repo.htmlUrl,
                url = repo.url,
                /// Details
                createdAt = null,
                updatedAt = null,
                pushedAt = null,
                homepage = null,
                language = null,
                size = null)
        }
    }
}
