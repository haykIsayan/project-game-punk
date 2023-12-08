package com.example.project_game_punk.features.search

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.shimmerBrush
import com.example.project_game_punk.features.common.game_progress.GameProgressBottomSheetController
import com.example.project_game_punk.features.common.game_progress.GameProgressButton
import com.example.project_game_punk.features.game_details.GameDetailsActivity

@Composable
fun SearchScreen(
    searchFiltersViewModel: SearchFiltersViewModel,
    searchResultsViewModel: SearchResultsViewModel,
    sheetController: GameProgressBottomSheetController,
) {
    Column {
        SearchField(
            searchResultsViewModel,
            searchFiltersViewModel
        )
        SearchResults(
            searchResultsViewModel,
            sheetController
        )
    }
}


@Composable
private fun SearchResults(
    searchResultsViewModel: SearchResultsViewModel,
    sheetController: GameProgressBottomSheetController
) {
    val state = searchResultsViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state,
        loadingState = {
            SearchResultLoadingState()
        }
    ) { games ->
        SearchResultLoadedState(
            games,
            sheetController,
            searchResultsViewModel
        )
    }
}

@Composable
private fun SearchResultLoadingState() {
    val showShimmer = remember { mutableStateOf(true) }
    LazyColumn {
        items(10) {
            Row(modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp, 120.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(shimmerBrush(showShimmer = showShimmer.value)),
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .padding(start = 12.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(shimmerBrush(showShimmer = showShimmer.value)),
                )
            }
        }
    }
}

@Composable
private fun SearchResultLoadedState(
    games: List<GameEntity>,
    sheetController: GameProgressBottomSheetController,
    searchResultsViewModel: SearchResultsViewModel
) {
    LazyColumn {
        items(games) { game ->
            GameSearchResultItem(
                game,
                sheetController,
                searchResultsViewModel
            )
        }
    }
}

@Composable
private fun GameSearchResultItem(
    game : GameEntity,
    sheetController: GameProgressBottomSheetController,
    searchResultsViewModel: SearchResultsViewModel
) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .clickable {
                context.startActivity(
                    Intent(
                        context,
                        GameDetailsActivity::class.java
                    ).apply {
                        putExtra(
                            GameDetailsActivity.GAME_ID_INTENT_EXTRA,
                            game.id
                        )
                    }
                )
            },
    ) {
        AsyncImage(
            modifier = Modifier
                .size(100.dp, 120.dp)
                .clip(RoundedCornerShape(10.dp)),
            model = ImageRequest.Builder(LocalContext.current)
                .data(game.backgroundImage)
                .crossfade(true)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = game.name ?: "",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(12.dp),
                fontSize = 18.sp
            )
            Box(
                modifier = Modifier
                    .padding(12.dp)
            ) {
                GameProgressButton(
                    game = game,
                    modifier = Modifier
                        .width(100.dp),
                    controller = sheetController,
                    onProgressSelected = { game, gameProgress ->
                        searchResultsViewModel.updateGameProgress(game, gameProgress)
                    }
                )
            }
        }
    }
}