package com.alexchar_dev.socialrelationships.domain

import java.lang.Exception

sealed class CustomErrors: Exception() {

    object LocalIOException: CustomErrors()
    object RemoteIOException: CustomErrors()
    object NetworkUnavailableException: CustomErrors()
    object AuthError: CustomErrors()
    object TransactionIOException : CustomErrors()
}