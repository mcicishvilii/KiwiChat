package com.example.kiwichat.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val uid:String = "",
    val userName: String = "",
    val email: String = "",
    val password: String = "",
//    val messages: List<Messages>? = null,
):Parcelable
