package com.example.catsgallery.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TheCatCategoryResponseItem(
    val id: String,
    val name: String) : Parcelable