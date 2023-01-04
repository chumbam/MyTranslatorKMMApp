package com.isaev.mytranslator_kmm.core.domain.util

import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.StateFlow

actual open class CommonStateFlow<T> actual constructor(
    private val stateFlow: StateFlow<T>
) : CommonFlow<T>(stateFlow), StateFlow<T> {

    override val replayCache: List<T>
        get() = stateFlow.replayCache

    override val value: T
        get() = stateFlow.value

    override suspend fun collect(collector: FlowCollector<T>): Nothing {
        stateFlow.collect(collector)
    }
}