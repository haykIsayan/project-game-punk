package com.example.project_game_punk.features.common.composables.grids

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <ItemType >GamePunkGrid(
    modifier: Modifier,
    items: List<ItemType>,
    span: Int = 2,
    footer: @Composable () -> Unit = {},
    itemComposer: @Composable (item: ItemType) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        cells = GridCells.Fixed(span),
    ) {
        items(items) { item ->
            itemComposer.invoke(item)
        }
    }
}