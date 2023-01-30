package com.isaev.mytranslator_kmm.voice_to_text.domain

import com.isaev.mytranslator_kmm.core.domain.util.CommonStateFlow

interface VoiceToTextParser {
    val state: CommonStateFlow<VoiceToTextParserState>
    fun startListening(languageCode: String)
    fun stopListening()
    fun cansel()
    fun reset()
}