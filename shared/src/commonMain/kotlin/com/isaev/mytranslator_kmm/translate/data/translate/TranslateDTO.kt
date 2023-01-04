package com.isaev.mytranslator_kmm.translate.data.translate

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class TranslateDTO(
    @SerialName("q")
    val textTotTranslate: String,
    @SerialName("source")
    val sourceLanguageCode: String,
    @SerialName("target")
    val targetLanguageCode: String
)
