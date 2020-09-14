package com.akki.domain.usecase

import com.akki.domain.base.UseCaseWithParams
import com.akki.domain.repository.SessionRepository
import javax.inject.Inject

class StartSession @Inject constructor(private val repo: SessionRepository) :
    UseCaseWithParams<String, Unit>() {

    override fun buildUseCase(params: String) = repo.startSession(params)
}