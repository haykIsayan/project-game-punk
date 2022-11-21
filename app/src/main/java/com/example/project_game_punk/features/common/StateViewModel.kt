package com.example.project_game_punk.features.common

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class StateViewModel<DataType, Param>: ViewModel() {
    private val state = MutableLiveData<ViewModelState<DataType>>()

    fun getState(): LiveData<ViewModelState<DataType>> = state

    protected fun getData(): DataType? = (state.value as? ViewModelState.SuccessState)?.data

    protected fun emit(updatedState: ViewModelState<DataType>) {
        state.value = updatedState
    }

    fun loadState(param: Param? = null) {
        viewModelScope.launch {
            try {
                state.value = ViewModelState.PendingState()
                val data = withContext(Dispatchers.IO) { loadData(param) }
//                throw Exception()
                state.value = ViewModelState.SuccessState(data)
            } catch (e: Exception) {
                Log.d("Haykk", e.localizedMessage ?: "")
                state.value = ViewModelState.ErrorState(message = e.localizedMessage ?: "")
            }
        }
    }

//    fun emit(data: DataType)

    protected abstract suspend fun loadData(param: Param? = null): DataType
}