package com.isaev.mytranslator_kmm.di

import com.isaev.mytranslator_kmm.translate.data.local.FakeHistoryDataSource
import com.isaev.mytranslator_kmm.translate.data.remote.FakeTranslateClient
import com.isaev.mytranslator_kmm.translate.domain.history.HistoryDataSource
import com.isaev.mytranslator_kmm.translate.domain.translate.Translate
import com.isaev.mytranslator_kmm.translate.domain.translate.TranslateClient
import com.isaev.mytranslator_kmm.voice_to_text.data.data.FakeVoiceToTextParser
import com.isaev.mytranslator_kmm.voice_to_text.domain.VoiceToTextParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    fun provideFakeTranslateClient(): TranslateClient {
        return FakeTranslateClient()
    }

    @Provides
    @Singleton
    fun provideFakeHistoryDataSource(): HistoryDataSource {
        return FakeHistoryDataSource()
    }

    @Provides
    @Singleton
    fun provideTranslateUseCase(
        client: TranslateClient,
        historyDataSource: HistoryDataSource
    ): Translate {
        return Translate(client, historyDataSource)
    }

    @Provides
    @Singleton
    fun provideFakeVoiceToTextParser(): VoiceToTextParser {
        return FakeVoiceToTextParser()
    }
}