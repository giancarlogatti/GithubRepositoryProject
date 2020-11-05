package com.example.githubproject.util

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class Either<out T, out V> {
    @Parcelize data class Left<T: Parcelable, V>(val data: T): Either<T, Nothing>(), Parcelable
    @Parcelize data class Right<T, V: Parcelable>(val data: V): Either<Nothing, V>(), Parcelable
}