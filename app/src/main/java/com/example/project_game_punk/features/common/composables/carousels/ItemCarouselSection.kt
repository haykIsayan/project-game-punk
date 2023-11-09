package com.example.project_game_punk.features.common.composables.carousels

import androidx.compose.foundation.layout.Column
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import com.example.project_game_punk.features.common.ViewModelState
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.SectionTitle

@Composable
fun <DataType> ItemCarouselSection(
    title: String,
    state: ViewModelState<List<DataType>>?,
    itemBuilder: @Composable (DataType) -> Unit,
) {
    Column {
        SectionTitle(title = title)
        LoadableStateWrapper(
            state = state,
            failState = {
                        /*TODO*/
                        },
            loadingState = {
                CircularProgressIndicator()
            },
        ) { items ->
            ItemCarousel(items = items) { item ->
                itemBuilder.invoke(item)
            }
        }
    }
}