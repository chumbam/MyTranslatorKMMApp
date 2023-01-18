package com.isaev.mytranslator_kmm.android.di

import android.app.Application
import com.isaev.mytranslator_kmm.database.TranslateDatabase
import com.isaev.mytranslator_kmm.translate.data.history.SqlDelightHistoryDataSource
import com.isaev.mytranslator_kmm.translate.data.local.DatabaseDriverFactory
import com.isaev.mytranslator_kmm.translate.data.remote.HttpClientFactory
import com.isaev.mytranslator_kmm.translate.data.translate.KtorTranslateClient
import com.isaev.mytranslator_kmm.translate.domain.history.HistoryDataSource
import com.isaev.mytranslator_kmm.translate.domain.translate.Translate
import com.isaev.mytranslator_kmm.translate.domain.translate.TranslateClient
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClientFactory().create()
    }

    @Provides
    @Singleton
    fun provideTranslateClient(httClient: HttpClient): TranslateClient {
        return KtorTranslateClient(httClient)
    }

    @Provides
    @Singleton
    fun provideDatabaseDriverDriver(app: Application): SqlDriver {
        return DatabaseDriverFactory(app).create()
    }

    @Provides
    @Singleton
    fun provideHistoryDataSource(driver: SqlDriver): HistoryDataSource {
        return SqlDelightHistoryDataSource(TranslateDatabase(driver))
    }

    @Provides
    @Singleton
    fun provideTranslateUseCase(
        client: TranslateClient,
        dataSource: HistoryDataSource
    ): Translate {
        return Translate(client, dataSource)
    }
}