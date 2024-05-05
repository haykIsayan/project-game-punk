package com.example.project_game_punk.features.common.composables.grids

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

//@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <ItemType> GamePunkGrid(
    modifier: Modifier,
    isVertical: Boolean = true,
    items: List<ItemType>,
    span: Int = 2,
    footer: @Composable () -> Unit = {},
    itemComposer: @Composable (item: ItemType) -> Unit
) {


//    LazyHorizontalGrid(rows = , content = )

    if (isVertical) {
        LazyVerticalGrid(
            modifier = modifier,
            columns = GridCells.Fixed(span),
            contentPadding = PaddingValues(6.dp),

            verticalArrangement = Arrangement.spacedBy(12.dp),
//        horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            items(items) { item ->
                itemComposer.invoke(item)
            }
        }
    } else {
        LazyHorizontalGrid(
            modifier = modifier,
            rows = GridCells.Fixed(span),
            contentPadding = PaddingValues(6.dp),

            horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(items) { item ->
                itemComposer.invoke(item)
            }
        }
    }
}

@Composable
fun GamePunkGridLoading(
    span: Int = 2,
    itemComposer: @Composable (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(span),
    ) {
        items(10) {
            itemComposer(it)
        }
    }
}

