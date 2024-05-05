package com.example.project_game_punk.features.common

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class StateViewModel<DataType, Param>: ViewModel() {
    private val state = MutableLiveData<ViewModelState<DataType>>()
    private var job: Job? = null

    fun getState(): LiveData<ViewModelState<DataType>> = state

    protected fun getData(): DataType? = (state.value as? ViewModelState.SuccessState)?.data

    protected fun emit(updatedState: ViewModelState<DataType>) {
        state.value = updatedState
    }

    protected fun updateData(newData: DataType) {
        emit(ViewModelState.SuccessState(newData))
    }

    fun loadState(
        param: Param? = null,
        force: Boolean = false,
        debounce: Boolean = false,
    ) {
        if (state.value != null && !force) return
        if (debounce) job?.cancel()
        job = viewModelScope.launch {
            if (debounce) delay(500)
            try {
                state.value = ViewModelState.PendingState()
                val data = withContext(Dispatchers.IO) { loadData(param) }
                state.value = ViewModelState.SuccessState(data)
            } catch (e: Exception) {
                Log.d("Haykk", e.localizedMessage ?: "")
                state.value = ViewModelState.ErrorState(message = e.localizedMessage ?: "")
            }
        }
    }

    protected abstract suspend fun loadData(param: Param? = null): DataType
}