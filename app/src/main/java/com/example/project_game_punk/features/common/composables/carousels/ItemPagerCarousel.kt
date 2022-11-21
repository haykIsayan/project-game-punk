package com.example.project_game_punk.features.common.composables.carousels

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun <ItemType> ItemPagerCarousel(
    items: List<ItemType>,
    itemComposer: @Composable (item: ItemType) -> Unit
) {
    val state = rememberPagerState()

    Column {
        HorizontalPager(
            count = items.size,
            state = state
        ) { index ->
            itemComposer.invoke(items[index])
        }
        HorizontalPagerIndicator(
            pagerState = state,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
        )
    }
}
