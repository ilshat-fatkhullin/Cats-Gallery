package com.example.catsgallery.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TheCatSearchResponseItem(
    val id: String,
    @Json(name = "url") val imgSrcUrl: String,
    val width: Int,
    val height: Int) : Parcelable
