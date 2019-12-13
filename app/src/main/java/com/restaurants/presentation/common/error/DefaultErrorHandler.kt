package com.restaurants.presentation.common.error

import timber.log.Timber
import javax.inject.Inject

class DefaultErrorHandler
@Inject
constructor() : ErrorHandler {

    override fun handleError(throwable: Throwable) {
        handleError(throwable, null)
    }

    override fun handleError(throwable: Throwable, errorView: ((message: String) -> Unit)?) {
        Timber.e(throwable)
    }

}
