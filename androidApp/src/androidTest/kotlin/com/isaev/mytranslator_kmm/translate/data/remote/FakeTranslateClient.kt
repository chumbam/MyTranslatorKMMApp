package com.isaev.mytranslator_kmm.translate.data.remote

import com.isaev.mytranslator_kmm.core.domain.language.Language
import com.isaev.mytranslator_kmm.translate.domain.translate.TranslateClient

class FakeTranslateClient : TranslateClient {

    var text = "test"

    override suspend fun translate(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): String {
        return text
    }
}