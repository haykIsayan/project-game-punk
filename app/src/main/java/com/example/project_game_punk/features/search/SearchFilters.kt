package com.example.project_game_punk.features.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import com.example.game_punk_domain.domain.models.GameFilter
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.carousels.ItemCarousel
import com.example.project_game_punk.features.common.composables.carousels.ItemCarouselDecorators
import com.example.project_game_punk.features.common.composables.carousels.ItemCarouselPill
import com.example.project_game_punk.features.common.composables.grids.GamePunkGrid


@Composable
fun SearchFilters(
    searchFiltersViewModel: SearchFiltersViewModel
) {
    val state = searchFiltersViewModel.getState().observeAsState().value
    LoadableStateWrapper(state = state) { gameFiltersUIModel ->
        LazyColumn(content = {
            item {
                ItemCarousel(
                    items = gameFiltersUIModel.gameFilters,
                    label = { FilterLabel(text = "Filter By") },
                    itemDecorator = ItemCarouselDecorators.pillItemDecorator
                ) {
                    filterToName(it.filter)?.let { filterName ->
                        ItemCarouselPill(text = filterName,
                            color = if (it.isEnabled) Color.Blue else Color.Transparent
                        ) {
                            searchFiltersViewModel.updateFilter(it.filter)
                        }
                    }
                }
            }
            item {
                ItemCarousel(
                    items = gameFiltersUIModel.gameGenres,
                    label = { FilterLabel(text = "Genre") },
                    itemDecorator = ItemCarouselDecorators.pillItemDecorator
                ) {
                    ItemCarouselPill(text = it.genre.name,
                        color = if (it.isEnabled) Color.Blue else Color.Transparent
                    ) {
                        searchFiltersViewModel.updateGenre(it.genre)
                    }
                }
            }
            item {
                ItemCarousel(
                    items = gameFiltersUIModel.gamePlatforms,
                    label = {
                        FilterLabel(text = "Platforms")
                    },
                    itemDecorator = ItemCarouselDecorators.pillItemDecorator
                ) {
                    ItemCarouselPill(
                        text = it.platform.name,
                        color = if (it.isEnabled) Color.Blue else Color.Transparent
                    ) {
//                        searchFiltersViewModel.updateFilter(it.filter)
                    }
                }
            }
        })
    }
}

@Composable
private fun FilterLabel(text: String) {
    Text(
        text = text.uppercase(),
        color = Color.White,
        modifier = Modifier.padding(8.dp),
        fontWeight = FontWeight.Bold
    )
}

private fun filterToName(filter: GameFilter): String? {
    return when (filter) {
        GameFilter.trending -> "Trending"
        GameFilter.highestRated -> "Highest Rated"
        GameFilter.recommended -> "Recommended"
        else -> null
    }
}
