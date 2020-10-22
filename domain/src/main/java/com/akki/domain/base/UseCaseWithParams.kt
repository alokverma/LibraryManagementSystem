package com.akki.domain.base

/**
 * Clean architecture Use case.
 *
 * It's orchestrate the flow of data to and from business entities,
 * and direct those entities to use their Critical Business Rules to achieve a goal.
 *
 * [R] represent the result produced by this use case.
 *
 * @see UseCaseWithParams if the use case require parameters.
 */
abstract class UseCaseWithParams<in Params, out R> {

    /**
     * Execute the use case.
     *
     * Called by client of the use case.
     *
     * @params Params passed from our presentation layer
     *
     * @return [R] result of executing this use case
     */
    fun execute(params: Params): R = buildUseCase(params)

    /**
     * Build the use case to be executed.
     *
     * @return result [R] of the use case.
     */
    abstract fun buildUseCase(params: Params): R



}