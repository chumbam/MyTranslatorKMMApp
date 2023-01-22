package com.isaev.mytranslator_kmm.di

import com.isaev.mytranslator_kmm.database.TranslateDatabase
import com.isaev.mytranslator_kmm.translate.data.history.SqlDelightHistoryDataSource
import com.isaev.mytranslator_kmm.translate.data.local.DatabaseDriverFactory
import com.isaev.mytranslator_kmm.translate.data.remote.HttpClientFactory
import com.isaev.mytranslator_kmm.translate.data.translate.KtorTranslateClient
import com.isaev.mytranslator_kmm.translate.domain.history.HistoryDataSource
import com.isaev.mytranslator_kmm.translate.domain.translate.Translate
import com.isaev.mytranslator_kmm.translate.domain.translate.TranslateClient

class AppModule {

    val historyDataSource: HistoryDataSource by lazy {
        SqlDelightHistoryDataSource(
            TranslateDatabase(
                DatabaseDriverFactory().create()
            )
        )
    }

    private val translateClient: TranslateClient by lazy {
        KtorTranslateClient(
            HttpClientFactory().create()
        )
    }

    val translateUseCase: Translate by lazy {
        Translate(
            client = translateClient,
            historyDataSource = historyDataSource
        )
    }
}