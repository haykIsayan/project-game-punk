package com.example.project_game_punk.features.discover.trending

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.example.project_game_punk.features.discover.components.DiscoverGameCarouselSection

@Composable
fun TrendingGamesSection(viewModel: TrendingGamesViewModel) {
    DiscoverGameCarouselSection(title = "Trending Games", viewModel)
}
