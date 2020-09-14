package com.akki.domain.usecase

import com.akki.domain.base.UseCaseWithParams
import com.akki.domain.repository.SessionRepository
import javax.inject.Inject

class GetEndSessionTime @Inject constructor(private val repo: SessionRepository) :
    UseCaseWithParams<String, Long>() {

    override fun buildUseCase(params: String): Long = repo.getEndSessionTime()
}
