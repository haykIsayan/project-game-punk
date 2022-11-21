package com.example.project_game_punk.features.common

sealed class ViewModelState<DataType> {
    open class SuccessState<DataType>(val data: DataType): ViewModelState<DataType>()
    data class PendingState<DataType>(val message: String = "") : ViewModelState<DataType>()
    data class ErrorState<DataType>(val message: String = ""): ViewModelState<DataType>()
    object Bruh
}
