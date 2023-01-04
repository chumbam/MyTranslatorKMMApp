package com.isaev.mytranslator_kmm.translate.domain.translate

import com.isaev.mytranslator_kmm.core.domain.language.Language

interface TranslateClient {

    suspend fun translate(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): String

}