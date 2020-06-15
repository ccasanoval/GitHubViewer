package com.cesoft.githubviewer.data

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep

////////////////////////////////////////////////////////////////////////////////////////////////////
//
@Keep
data class RepoModel(
    val id: String?,
    val nodeId: String?,
    val owner: OwnerModel?,
    val name: String?,
    val fullName: String?,
    val description: String?,
    val htmlUrl: String?,
    val url: String?
): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(OwnerModel::class.java.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(nodeId)
        parcel.writeParcelable(owner, flags)
        parcel.writeString(name)
        parcel.writeString(fullName)
        parcel.writeString(description)
        parcel.writeString(htmlUrl)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        val TAG: String = RepoModel::class.simpleName!!

        val CREATOR = object: Parcelable.Creator<RepoModel> {
            override fun createFromParcel(parcel: Parcel): RepoModel {
                return RepoModel(parcel)
            }

            override fun newArray(size: Int): Array<RepoModel?> {
                return arrayOfNulls(size)
            }
        }
    }
}
