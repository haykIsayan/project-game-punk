package com.example.project_game_punk.features.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.VideogameAsset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.game_punk_domain.domain.entity.game.GameEntity
import com.example.game_punk_domain.domain.entity.user.UserEntity
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.shimmerBrush
import com.example.project_game_punk.features.common.game_progress.GameProgressBottomSheetController
import com.example.project_game_punk.features.common.game_progress.GameProgressButton
import com.example.project_game_punk.features.game_details.sections.GamePunkTab
import com.example.project_game_punk.features.main.GamePunkNavigator

@Composable
fun SearchScreen(
    searchFiltersViewModel: SearchFiltersViewModel,
    searchGamesViewModel: SearchGamesViewModel,
    searchUsersViewModel: SearchUsersViewModel,
    sheetController: GameProgressBottomSheetController,
) {
    val index = remember { mutableStateOf(0) }
    Column {
        Spacer(modifier = Modifier.height(6.dp))
        SearchField(
            index.value,
            searchGamesViewModel,
            searchUsersViewModel,
            searchFiltersViewModel
        )
        GamePunkTab(
            selectedItemIndex = index.value,
            items = listOf(
                "Games",
                "Users"
            ),
            onClick = {
                index.value = it
            }
        )
        when (index.value) {
            0 -> {
                SearchGamesResults(
                    searchGamesViewModel,
                    sheetController
                )
            }
            1 -> {
                SearchUsersResult(
                    searchUsersViewModel
                )
            }
        }

    }
}


@Composable
private fun SearchGamesResults(
    searchGamesViewModel: SearchGamesViewModel,
    sheetController: GameProgressBottomSheetController
) {
    val state = searchGamesViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state,
        loadingState = {
            SearchResultLoadingState()
        }
    ) { games ->
        SearchResultLoadedState(
            games,
            sheetController,
            searchGamesViewModel
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
    searchGamesViewModel: SearchGamesViewModel
) {
    LazyColumn {
        items(games) { game ->
            GameSearchResultItem(
                game,
                sheetController,
                searchGamesViewModel
            )
        }

        item {
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}

@Composable
private fun GameSearchResultItem(
    game : GameEntity,
    sheetController: GameProgressBottomSheetController,
    searchGamesViewModel: SearchGamesViewModel
) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .clickable {
                game.id?.let { gameId ->
                    GamePunkNavigator.navigate("game/$gameId")
                }
            },
    ) {
        game.backgroundImage?.let {
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
        } ?: Box(
            modifier = Modifier
                .size(100.dp, 120.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White.copy(alpha = 0.05f)),
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.Center)
                    .clip(RoundedCornerShape(10.dp))
                    .size(80.dp),
                imageVector = Icons.Filled.VideogameAsset,
                contentDescription = ""
            )
        }


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
                        searchGamesViewModel.updateGameProgress(game, gameProgress)
                    }
                )
            }
        }
    }
}

@Composable
private fun SearchUsersResult(searchUsersViewModel: SearchUsersViewModel) {
    val state = searchUsersViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state,
        loadingState = {
            SearchUsersResultLoadingState()
        }
    ) { users ->
        SearchUsersResultLoadedState(users)
    }
}

@Composable
private fun SearchUsersResultLoadingState() {
    val showShimmer = remember { mutableStateOf(true) }
    LazyColumn {
        items(10) {
            Row(modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(shimmerBrush(showShimmer = showShimmer.value)),
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(start = 12.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(shimmerBrush(showShimmer = showShimmer.value)),
                )
            }
        }
    }
}

@Composable
private fun SearchUsersResultLoadedState(users: List<UserEntity>) {
    LazyColumn {
        items(users) {user ->
            UserResultItem(user = user)
        }
    }
}

@Composable
private fun UserResultItem(user: UserEntity) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .clickable {
                 GamePunkNavigator.navigate("user/${user.id}")
            },
    ) {

        user.profileIcon?.let {

        } ?: Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White.copy(alpha = 0.05f)),
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.Center)
                    .clip(RoundedCornerShape(10.dp))
                    .size(60.dp),
                imageVector = Icons.Filled.Person,
                contentDescription = ""
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            user.displayName?.let { displayName ->
                Text(
                    text = displayName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(12.dp),
                    fontSize = 18.sp
                )
            }
        }
    }
}