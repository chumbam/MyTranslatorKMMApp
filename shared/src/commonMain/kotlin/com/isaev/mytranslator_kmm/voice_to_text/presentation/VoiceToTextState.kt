package com.isaev.mytranslator_kmm.voice_to_text.presentation

data class VoiceToTextState(
    val powerRation: List<Float> = emptyList(),
    val spokenText: String = "",
    val canRecord: Boolean = false,
    val recordError: String? = null,
    val displayState: DisplayState? = null
)

enum class DisplayState {
    WAITING_TO_TALK,
    SPEAKING,
    DISPLAY_RESULT,
    ERROR
}
