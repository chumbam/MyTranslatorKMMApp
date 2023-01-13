package com.isaev.mytranslator_kmm.translate.presentation

import com.isaev.mytranslator_kmm.core.domain.language.Language
import com.isaev.mytranslator_kmm.core.presentation.UiLanguage

data class UiHistoryItem(
    val id: Long,
    val fromText: String,
    val toText: String,
    val fromLanguage: UiLanguage,
    val toLanguage: UiLanguage

)
