package com.akki.data.utility

import com.akki.domain.base.ResultWrapper
import io.reactivex.Single
import retrofit2.HttpException
import java.io.IOException

fun <T> Single<T>.mapResponeToGeneric(): Single<ResultWrapper<T>> {
    return this
        .map { it.asResult() }
        .onErrorReturn {
            if (it is HttpException || it is IOException) {
                return@onErrorReturn it.asErrorResult<T>()
            } else {
                throw it
            }
        }
}

fun <T> T.asResult(): ResultWrapper<T> {
    return ResultWrapper.Success(data = this)
}

fun <T> T.onLoading(): ResultWrapper<T> {
    return ResultWrapper.Loading(data = this)
}

fun <T> Throwable.asErrorResult(): ResultWrapper<T> {
    return ResultWrapper.Error(this, null)
}