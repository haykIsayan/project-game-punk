package com.example.project_game_punk.features.common.composables.carousels

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

typealias ItemCarouselDecorator = @Composable (index: Int, size: Int, item: @Composable () -> Unit) -> Unit

object ItemCarouselDecorators {
    val pillItemDecorator: ItemCarouselDecorator = { index, size, item ->
        Box(
            Modifier.padding(
                start = if (index == 0) 12.dp else 6.dp,
                top = 6.dp,
                bottom = 6.dp,
                end = if (index == size - 1) 12.dp else 6.dp
            )
        ) {
            item.invoke()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <ItemType> ItemCarousel(
    state: LazyListState = rememberLazyListState(),
    items: List<ItemType>,
    label: (@Composable () -> Unit)? = null,
    itemDecorator: ItemCarouselDecorator? = null,
    itemComposer: @Composable (item: ItemType) -> Unit
) {
        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            state = state
        ) {

            label?.let {
                item {
                    label.invoke()
                }
            }

            itemsIndexed(items) { index, item ->
                Box(
                    modifier = Modifier
                        .animateItemPlacement(
                            animationSpec = tween(
                                durationMillis = 500
                            )
                        )
                ) {
                    itemDecorator?.invoke(
                        index,
                        items.size
                    ) {
                        itemComposer.invoke(item)
                    } ?: itemComposer.invoke(item)
                }
            }
    }
}