package com.chinlung.trackmovie.model

import android.os.Parcel
import android.os.Parcelable


data class TvJson(
    val page: Int,
    val results: List<TvResult>,
    val total_pages: Int,
    val total_results: Int
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        TODO("results"),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(page)
        parcel.writeInt(total_pages)
        parcel.writeInt(total_results)
    }

    override fun describeContents(): Int {
        return results.size
    }

    companion object CREATOR : Parcelable.Creator<TvJson> {
        override fun createFromParcel(parcel: Parcel): TvJson {
            return TvJson(parcel)
        }

        override fun newArray(size: Int): Array<TvJson?> {
            return arrayOfNulls(size)
        }
    }
}