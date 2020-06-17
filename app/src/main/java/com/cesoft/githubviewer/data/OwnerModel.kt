package com.cesoft.githubviewer.data

import android.os.Parcel
import android.os.Parcelable

////////////////////////////////////////////////////////////////////////////////////////////////////
//
data class OwnerModel(
    val id: Int,
    val nodeId: String?,
    val login: String?,
    val avatarUrl: String?,
    val url: String?,
    val htmlUrl: String?,
    val type: String?,
    val isSiteAdmin: Boolean?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nodeId)
        parcel.writeString(login)
        parcel.writeString(avatarUrl)
        parcel.writeString(url)
        parcel.writeString(htmlUrl)
        parcel.writeString(type)
        parcel.writeByte(if(isSiteAdmin==true) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OwnerModel> {
        override fun createFromParcel(parcel: Parcel): OwnerModel {
            return OwnerModel(parcel)
        }
        override fun newArray(size: Int): Array<OwnerModel?> {
            return arrayOfNulls(size)
        }
    }
}
