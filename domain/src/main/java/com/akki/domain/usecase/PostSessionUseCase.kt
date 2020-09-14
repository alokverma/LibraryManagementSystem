package com.akki.domain.usecase

import com.akki.domain.base.UseCaseWithParams
import com.akki.domain.enitity.ScanResult
import com.akki.domain.enitity.SessionSubmitResult
import com.akki.domain.repository.SessionRepository
import io.reactivex.Single
import javax.inject.Inject

class PostSessionUseCase @Inject constructor(private val repo: SessionRepository) :
    UseCaseWithParams<ScanResult, Single<SessionSubmitResult>>() {

    override fun buildUseCase(params: ScanResult): Single<SessionSubmitResult> =
        repo.postSession(params)

}