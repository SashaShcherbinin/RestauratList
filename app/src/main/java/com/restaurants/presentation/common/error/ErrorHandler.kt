package com.restaurants.presentation.common.error

interface ErrorHandler {

    fun handleError(throwable: Throwable)

    fun handleError(throwable: Throwable, errorView: ((message: String) -> Unit)?)

}
