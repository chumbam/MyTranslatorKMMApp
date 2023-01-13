package com.isaev.mytranslator_kmm.translate.domain.translate

import com.isaev.mytranslator_kmm.core.domain.language.Language
import com.isaev.mytranslator_kmm.core.domain.util.Resource
import com.isaev.mytranslator_kmm.translate.domain.history.HistoryDataSource
import com.isaev.mytranslator_kmm.translate.domain.history.HistoryItem

class Translate(
    private val client: TranslateClient,
    private val historyDataSource: HistoryDataSource
) {

    suspend fun execute(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): Resource<String> {
        return try {
            val translatedText = client.translate(
                fromLanguage, fromText, toLanguage
            )
            historyDataSource.insertHistoryItem(
                HistoryItem(
                    id = null,
                    fromLanguageCode = fromLanguage.langCode,
                    fromText = fromText,
                    toLanguageCode = toLanguage.langCode,
                    toText = translatedText
                )
            )
            Resource.Success(data = translatedText)
        } catch (e: TranslateExceptions) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }
}