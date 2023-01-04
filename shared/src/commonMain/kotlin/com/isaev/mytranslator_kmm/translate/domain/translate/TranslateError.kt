package com.isaev.mytranslator_kmm.translate.domain.translate

enum class TranslateError {
    SERVICE_UNAVAILABLE,
    CLIENT_ERROR,
    SERVER_ERROR,
    UNKNOWN_ERROR
}

class TranslateExceptions(val error: TranslateError) : Exception(
    "An error occured when translating: $error"
)