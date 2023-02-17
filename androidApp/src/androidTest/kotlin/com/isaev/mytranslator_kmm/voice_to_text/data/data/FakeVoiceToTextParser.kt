package com.isaev.mytranslator_kmm.voice_to_text.data.data

import com.isaev.mytranslator_kmm.core.domain.util.CommonStateFlow
import com.isaev.mytranslator_kmm.core.domain.util.toCommonStateFlow
import com.isaev.mytranslator_kmm.voice_to_text.domain.VoiceToTextParser
import com.isaev.mytranslator_kmm.voice_to_text.domain.VoiceToTextParserState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class FakeVoiceToTextParser : VoiceToTextParser {

    private val _state = MutableStateFlow(VoiceToTextParserState())
    var voiceToTextResult = "test translate result"

    override val state: CommonStateFlow<VoiceToTextParserState>
        get() = _state.toCommonStateFlow()

    override fun startListening(languageCode: String) {
        _state.update {
            it.copy(
                result = "",
                isSpeaking = true
            )
        }
    }

    override fun stopListening() {
        _state.update {
            it.copy(
                result = voiceToTextResult,
                isSpeaking = false
            )
        }
    }

    override fun cansel() = Unit

    override fun reset() {
        _state.update { VoiceToTextParserState() }
    }
}