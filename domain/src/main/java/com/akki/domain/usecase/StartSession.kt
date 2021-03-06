package com.akki.domain.usecase

import com.akki.domain.base.UseCaseWithParams
import com.akki.domain.repository.SessionRepository
import io.reactivex.Single
import javax.inject.Inject

class StartSession @Inject constructor(private val repo: SessionRepository) :
    UseCaseWithParams<String, Single<Int>>() {

    override fun buildUseCase(params: String): Single<Int> {
        return repo.startSession(params)
    }
}