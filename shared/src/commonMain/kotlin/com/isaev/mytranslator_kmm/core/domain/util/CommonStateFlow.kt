package com.isaev.mytranslator_kmm.core.domain.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

expect class CommonStateFlow<T>(stateFlow: StateFlow<T>) : StateFlow<T>

fun <T> StateFlow<T>.toCommonStateFlow() = CommonStateFlow(this)