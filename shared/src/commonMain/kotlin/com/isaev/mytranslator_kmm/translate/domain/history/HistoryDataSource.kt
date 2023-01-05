package com.isaev.mytranslator_kmm.translate.domain.history

import com.isaev.mytranslator_kmm.core.domain.util.CommonFlow

interface HistoryDataSource {
    fun getHistory(): CommonFlow<List<HistoryItem>>
    suspend fun insertHistoryItem(item: HistoryItem)
}