package com.example.applikasigithubuser2

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserItems(
        var avatar: String? = null,
        var name: String? = null,
) : Parcelable

