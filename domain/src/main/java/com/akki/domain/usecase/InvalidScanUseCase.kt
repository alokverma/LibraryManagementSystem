package com.akki.domain.usecase

import com.akki.domain.base.UseCaseWithParams
import com.akki.domain.repository.SessionRepository
import io.reactivex.Observable
import javax.inject.Inject

class InvalidScanUseCase @Inject constructor(private val repo: SessionRepository) :
    UseCaseWithParams<String, Observable<String>>() {

    override fun buildUseCase(params: String): Observable<String> = repo.checkIsSessionIsValid()
}