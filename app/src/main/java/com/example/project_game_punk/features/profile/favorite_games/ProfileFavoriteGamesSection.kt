package com.example.project_game_punk.features.profile.favorite_games

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.example.project_game_punk.features.common.composables.GameCarouselItem
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.SectionTitle
import com.example.project_game_punk.features.common.composables.carousels.ItemCarousel
import com.example.project_game_punk.features.common.composables.carousels.ItemCarouselDecorators
import com.example.project_game_punk.features.common.game_progress.GameProgressBottomSheetController
import com.example.project_game_punk.features.discover.components.DiscoverGameFailState
import com.example.project_game_punk.features.profile.GamesCarouselSectionLoadingState


@Composable
fun ProfileFavoriteGamesSection(
    favoriteGamesViewModel: FavoriteGamesViewModel,
    controller: GameProgressBottomSheetController
) {
    val state = favoriteGamesViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state,
        failState = { errorMessage -> DiscoverGameFailState(errorMessage) { favoriteGamesViewModel.loadState() } },
        loadingState = { GamesCarouselSectionLoadingState(title = "Favorite Games") },
    ) { games ->
        Column {
            SectionTitle(title = "Favorite Games") {

            }
            ItemCarousel(
                items = games,
                itemDecorator = ItemCarouselDecorators.pillItemDecorator
            ) { game ->
                GameCarouselItem(
                    game = game,
                    sheetController = controller
                ) { game, gameProgress ->
                    favoriteGamesViewModel.updateGameProgress(game, gameProgress)
                }
            }
        }
    }
}