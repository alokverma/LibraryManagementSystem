package com.akki.domain.usecase

import com.akki.domain.base.UseCaseWithParams
import com.akki.domain.repository.SessionRepository
import javax.inject.Inject

class EndSession @Inject constructor(private val repo: SessionRepository) :
    UseCaseWithParams<String, Unit>() {

    override fun buildUseCase(params: String): Unit = repo.endSession(params)
}