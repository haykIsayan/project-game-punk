package com.example.project_game_punk.features.common.composables

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable

@Composable
fun <ItemType> ItemCarousel(
    items: List<ItemType>,
    itemComposer: @Composable (item: ItemType) -> Unit
) {
    LazyRow {
        items(items) { item ->
            itemComposer.invoke(item)
        }
    }
}