package com.isaev.mytranslator_kmm.translate.data.translate

import com.isaev.mytranslator_kmm.constants.NetworkConstants
import com.isaev.mytranslator_kmm.core.domain.language.Language
import com.isaev.mytranslator_kmm.translate.domain.translate.TranslateClient
import com.isaev.mytranslator_kmm.translate.domain.translate.TranslateError
import com.isaev.mytranslator_kmm.translate.domain.translate.TranslateExceptions
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.utils.io.errors.*

class KtorTranslateClient(
    private val httpClient: HttpClient
) : TranslateClient {
    override suspend fun translate(
        fromLanguage: Language,
        fromText: String,
        toLanguage: Language
    ): String {
        val result = try {
            httpClient.post {
                url(NetworkConstants.BASE_URL + "/translate")
                contentType(ContentType.Application.Json)
                setBody(
                    TranslateDTO(
                        textTotTranslate = fromText,
                        sourceLanguageCode = fromLanguage.langCode,
                        targetLanguageCode = toLanguage.langCode
                    )
                )
            }
        } catch (e: IOException) {
            throw TranslateExceptions(TranslateError.SERVICE_UNAVAILABLE)
        }

        when (result.status.value) {
            in 200..299 -> Unit
            500 -> throw TranslateExceptions(TranslateError.SERVER_ERROR)
            in 400..499 -> throw TranslateExceptions(TranslateError.CLIENT_ERROR)
            else -> throw TranslateExceptions(TranslateError.UNKNOWN_ERROR)
        }

        return try {
            result.body<TranslatedDTO>().translatedText
        } catch (e: Exception) {
            throw TranslateExceptions(TranslateError.SERVER_ERROR)
        }
    }
}