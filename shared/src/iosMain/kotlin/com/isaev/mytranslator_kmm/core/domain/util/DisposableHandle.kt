package com.isaev.mytranslator_kmm.core.domain.util

// Это специфический интерфес диспосэбл хэндл для того что на иос все работало как надо
fun interface DisposableHandle : kotlinx.coroutines.DisposableHandle

// Верхняя реализация заменяет нам блок кода тот что нижу

//fun DisposableHandle(block: () -> Unit): kotlinx.coroutines.DisposableHandle {
//        return object: kotlinx.coroutines.DisposableHandle {
//            override fun dispose() {
//                block
//            }
//        }
//}
