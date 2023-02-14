package com.isaev.mytranslator_kmm.translate.presentation

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.isaev.mytranslator_kmm.core.presentation.UiLanguage
import com.isaev.mytranslator_kmm.translate.data.local.FakeHistoryDataSource
import com.isaev.mytranslator_kmm.translate.data.remote.FakeTranslateClient
import com.isaev.mytranslator_kmm.translate.domain.history.HistoryDataSource
import com.isaev.mytranslator_kmm.translate.domain.history.HistoryItem
import com.isaev.mytranslator_kmm.translate.domain.translate.Translate
import com.isaev.mytranslator_kmm.translate.domain.translate.TranslateClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test


class TranslateViewModelTest {

    private lateinit var viewModel: TranslateViewModel
    private lateinit var historyDataSource: HistoryDataSource
    private lateinit var translateClient: TranslateClient

    @BeforeTest
    fun setup() {
        historyDataSource = FakeHistoryDataSource()
        translateClient = FakeTranslateClient()
        val translate = Translate(
            client = translateClient,
            historyDataSource = historyDataSource
        )
        viewModel = TranslateViewModel(
            translate = translate,
            historyDataSource = historyDataSource,
            CoroutineScope(Dispatchers.Default)
        )
    }

    @Test
    fun `State and history item are properly combined`() = runBlocking {
        viewModel.state.test {
            val initialItem = awaitItem()
            assertThat(initialItem).isEqualTo(TranslateState())

            val item = HistoryItem(
                id = 0,
                fromLanguageCode = "en",
                fromText = "from",
                toLanguageCode = "de",
                toText = "to"
            )

            historyDataSource.insertHistoryItem(item)

            val state = awaitItem()

            val expected = UiHistoryItem(
                id = item.id!!,
                fromText = item.fromText,
                toText = item.toText,
                fromLanguage = UiLanguage.byCode(item.fromLanguageCode),
                toLanguage = UiLanguage.byCode(item.toLanguageCode)
            )

            assertThat(state.history.first()).isEqualTo(expected)
        }
    }

    @Test
    fun `Translate success - state properly updated`() = runBlocking {
        viewModel.state.test {
            awaitItem()

            viewModel.onEvent(TranslateEvent.ChangeTranslationText("test"))
            awaitItem()

            viewModel.onEvent(TranslateEvent.Translate)

            val loadingState = awaitItem()
            assertThat(loadingState.isTranslating).isTrue()

            val resultState = awaitItem()
            assertThat(resultState.isTranslating).isFalse()
            assertThat(resultState.toText).isEqualTo((translateClient as FakeTranslateClient).text)
        }

    }
}