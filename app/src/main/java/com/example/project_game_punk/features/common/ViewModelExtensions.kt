package com.example.project_game_punk.features.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun <T> ViewModel.executeIO(
    ioDispatcher: CoroutineDispatcher,
    execute: suspend () -> T,
    onBefore: () -> Unit,
    onFail: (Exception) -> Unit,
    onSuccess: (T) -> Unit = {}
) {
    viewModelScope.launch(Dispatchers.Main) {
        try {
            onBefore.invoke()
            val data = withContext(ioDispatcher) { execute.invoke() }
            onSuccess.invoke(data)
        } catch (e: Exception) {
            onFail.invoke(e)
        }
    }
}

fun <T> MutableList<T>.update(element: T) {
    val index = indexOf(element)
    set(index, element)
}