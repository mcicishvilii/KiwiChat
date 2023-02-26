package com.example.kiwichat.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Messages(
    val id: String = "",
    val text: String = "",
): Parcelable