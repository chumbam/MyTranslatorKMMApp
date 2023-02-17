package com.isaev.mytranslator_kmm.translate.data.local

import com.isaev.mytranslator_kmm.core.domain.util.CommonFlow
import com.isaev.mytranslator_kmm.core.domain.util.toCommonFlow
import com.isaev.mytranslator_kmm.translate.domain.history.HistoryDataSource
import com.isaev.mytranslator_kmm.translate.domain.history.HistoryItem
import kotlinx.coroutines.flow.MutableStateFlow

class FakeHistoryDataSource : HistoryDataSource {

    private val data = MutableStateFlow<List<HistoryItem>>(emptyList())

    override fun getHistory(): CommonFlow<List<HistoryItem>> {
        return data.toCommonFlow()
    }

    override suspend fun insertHistoryItem(item: HistoryItem) {
        data.value += item
    }
}