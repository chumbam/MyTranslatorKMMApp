package com.isaev.mytranslator_kmm.android.text_to_speech.di

import android.app.Application
import com.isaev.mytranslator_kmm.android.text_to_speech.data.AndroidVoiceToTextParser
import com.isaev.mytranslator_kmm.voice_to_text.domain.VoiceToTextParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object VoiceToTextModule {

    @Provides
    @ViewModelScoped
    fun provideVoiceToTextParser(app: Application): VoiceToTextParser {
        return AndroidVoiceToTextParser(app)
    }
}