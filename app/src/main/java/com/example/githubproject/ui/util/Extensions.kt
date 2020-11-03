package com.example.githubproject.ui.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import io.reactivex.Single

fun <T> Single<T>.toLiveData() : LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(this.toFlowable())
}

