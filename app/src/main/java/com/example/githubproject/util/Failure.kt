package com.example.githubproject.util

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class Failure {
    @Parcelize data class ServerFailure(val responseCode: Int): Failure(), Parcelable
    @Parcelize object ConnectionFailure : Failure(), Parcelable
}