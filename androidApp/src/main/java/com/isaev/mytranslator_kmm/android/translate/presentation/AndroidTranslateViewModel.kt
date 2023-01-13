package com.isaev.mytranslator_kmm.android.translate.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isaev.mytranslator_kmm.translate.domain.history.HistoryDataSource
import com.isaev.mytranslator_kmm.translate.domain.translate.Translate
import com.isaev.mytranslator_kmm.translate.presentation.TranslateEvent
import com.isaev.mytranslator_kmm.translate.presentation.TranslateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidTranslateViewModel @Inject constructor(
    private val translate: Translate,
    private val historyDataSource: HistoryDataSource
) : ViewModel() {

    private val viewModel by lazy {
        TranslateViewModel(
            translate,
            historyDataSource,
            viewModelScope
        )
    }

    val state = viewModel.state

    fun onEvent(event: TranslateEvent) {
        viewModel.onEvent(event)
    }
}