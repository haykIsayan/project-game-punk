package com.example.project_game_punk.features.common.composables.grids

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
        modifier = modifier.scrollable(rememberScrollState(), enabled = false, orientation = Orientation.Vertical),
        cells = GridCells.Fixed(span),
    ) {
        items(items) { item ->
            itemComposer.invoke(item)
        }
    }
}