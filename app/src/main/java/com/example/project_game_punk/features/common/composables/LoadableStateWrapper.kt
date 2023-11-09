package com.example.project_game_punk.features.common.composables

import androidx.compose.runtime.Composable
import com.example.project_game_punk.features.common.ViewModelState

typealias ComposableFunction = @Composable () -> Unit

@Composable
fun <DataType> LoadableStateWrapper(
    state: ViewModelState<DataType>?,
    failState: @Composable (errorMessage: String) -> Unit = {},
    loadingState: ComposableFunction = {},
    successState: @Composable (data: DataType) -> Unit = {},
) {
    when (state) {
        is ViewModelState.ErrorState -> failState.invoke(state.message)
        is ViewModelState.PendingState -> loadingState.invoke()
        is ViewModelState.SuccessState -> successState.invoke(state.data)
    }
}