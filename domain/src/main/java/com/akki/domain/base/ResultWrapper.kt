package com.akki.domain.base

sealed class ResultWrapper<T> {

    /**
     * A state of [data] which shows that we know there is still an update to come.
     */
    data class Loading<T>(val data: T) : ResultWrapper<T>()

    /**
     * A state that shows the [data] is the last known update.
     */
    data class Success<T>(val data: T) : ResultWrapper<T>()

    /**
     * A state to show a [throwable] is thrown.
     */
    data class Error<T>(val throwable: Throwable, val data: T?) : ResultWrapper<T>()
}
