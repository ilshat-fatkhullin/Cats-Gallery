package com.example.catsgallery.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TheCatCategoryResponseItem(
    val id: Int,
    val name: String) : Parcelable