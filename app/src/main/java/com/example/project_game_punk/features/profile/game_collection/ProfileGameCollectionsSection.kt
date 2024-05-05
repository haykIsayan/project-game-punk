package com.example.project_game_punk.features.profile.game_collection

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.game_punk_domain.domain.entity.GameCollectionEntity
import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.SectionTitle
import com.example.project_game_punk.features.common.composables.carousels.ItemCarousel
import com.example.project_game_punk.features.common.composables.carousels.ItemCarouselDecorators
import com.example.project_game_punk.features.common.composables.carousels.ItemPagerCarousel
import com.example.project_game_punk.features.common.composables.grids.GamePunkGrid
import com.example.project_game_punk.features.common.composables.shimmerBrush
import com.example.project_game_punk.features.main.GamePunkNavigator

@Composable
fun ProfileGameCollectionsSection(
    profileGameCollectionsViewModel: ProfileGameCollectionsViewModel,
    isCurrentUser: Boolean = false,
    onCreateGameCollectionPressed: () -> Unit
) {
    val state = profileGameCollectionsViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state,
        loadingState = {
            ProfileGameCollectionsSectionLoadingState()
        }
    ) { profileGameCollectionState ->
        ProfileGameCollectionsSectionLoadedState(
            profileGameCollectionState = profileGameCollectionState,
            isCurrentUser = isCurrentUser
        ) {
            onCreateGameCollectionPressed()
        }
    }
}

@Composable
private fun ProfileGameCollectionsSectionLoadingState() {
    val showShimmer = remember { mutableStateOf(true) }
    Column {
        SectionTitle(
            title = "Collection",
            isLoading = true
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .padding(12.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(shimmerBrush(showShimmer = showShimmer.value))
        )
        Box(modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .height(14.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(shimmerBrush(showShimmer = showShimmer.value))
        )
    }
}

@Composable
private fun ProfileGameCollectionsSectionLoadedState(
    profileGameCollectionState: ProfileGameCollectionState,
    isCurrentUser: Boolean = false,
    onCreateGameCollectionPressed: () -> Unit
) {
    Column {
        SectionTitle(
            title = "Collections",
            trailing = if (isCurrentUser) {
                {
                    if (!profileGameCollectionState.isLoading) {
                        Icon(
                            modifier = Modifier
                                .padding(12.dp)
                                .clickable {
                                    onCreateGameCollectionPressed()
                                },
                            imageVector = Icons.Filled.Add,
                            contentDescription = ""
                        )
                    } else {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(12.dp)
                                .size(20.dp),
                            strokeWidth = 3.dp,
                            color = Color.White
                        )
                    }
                }
            } else {
                null
            }
        )
        if (profileGameCollectionState.isLoading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .clip(RoundedCornerShape(10.dp)),
                color = Color.White
            )
        }
        if (profileGameCollectionState.gameCollections.isEmpty()) {
            ProfileGameCollectionsEmptyState()
        } else {
            ProfileGameCollectionsCarousel(gameCollections = profileGameCollectionState.gameCollections)
        }
    }
}

@Composable
private fun ProfileGameCollectionsEmptyState(

) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(12.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White.copy(alpha = 0.05f))
    ) {
        Icon(
            modifier = Modifier
                .size(30.dp)
                .align(Alignment.Center)
                .clickable {
//                    onCreateGameCollectionPressed()
                },
            imageVector = Icons.Filled.Add,
            contentDescription = ""
        )
    }
}

@Composable
private fun ProfileGameCollectionsCarousel(
    gameCollections: List<GameCollectionEntity>
) {
    ItemPagerCarousel(
        items = gameCollections,
    ) { gameCollection ->
        ProfileGameCollectionsItem(gameCollection = gameCollection)
    }
}

@Composable
private fun ProfileGameCollectionsItem(
    gameCollection: GameCollectionEntity
) {
    val games = gameCollection.games
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(12.dp)
            .clickable {
                gameCollection.id?.let { collectionId ->
                    GamePunkNavigator.navigate("game_collection/userId/$collectionId")
                }
            }
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White.copy(alpha = 0.05f))
    ) {
        Row {
            if (games.isNotEmpty()) {
                FirstItemGameCover(
                    game = games.first()
                )
            }
            Column {
                Text(
                    modifier = Modifier
                        .padding(vertical = 6.dp, horizontal = 6.dp)
                        .fillMaxWidth(),
                    text = gameCollection.name ?: "",
                    textAlign = TextAlign.Start,
                    color = Color.White,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                if (games.size > 4) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        games.subList(1, 3).map { game ->
                            NotFirstItemGameCover(game = game)
                        }
                        Box(
                            modifier = Modifier
                                .size(
                                    90.dp,
                                    130.dp
                                )
                                .padding(
                                    horizontal = 6.dp,
                                    vertical = 6.dp
                                )
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color.White.copy(alpha = 0.05f))
                        ,
                        ) {
                            Column(
                                modifier = Modifier.align(Alignment.Center),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = games.size.toString(),
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White
                                )
                                Text(
                                    text = "Games",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun FirstItemGameCover(game: GameEntity) {
    AsyncImage(
        modifier = Modifier
            .size(
                80.dp,
//                150.dp
            )
            .padding(6.dp)
            .clip(RoundedCornerShape(10.dp)),
        model = ImageRequest.Builder(LocalContext.current)
            .data(game.backgroundImage)
            .crossfade(true)
            .build(),
        contentDescription = "",
        contentScale = ContentScale.Crop,
    )
}


@Composable
private fun NotFirstItemGameCover(game: GameEntity) {
    AsyncImage(
        modifier = Modifier
            .size(
                90.dp,
                130.dp
            )
            .padding(horizontal = 6.dp, vertical = 6.dp)
            .clip(RoundedCornerShape(10.dp)),
        model = ImageRequest.Builder(LocalContext.current)
            .data(game.backgroundImage)
            .crossfade(true)
            .build(),
        contentDescription = "",
        contentScale = ContentScale.Crop,
    )
}

@Composable
private fun GameCollectionItemGamesPreview(games: List<GameEntity>) {
    Row {
        games.subList(0,4).mapIndexed { index, game ->
            if (index == 0) {
                AsyncImage(
                    modifier = Modifier
                        .size(
                            110.dp,
                            150.dp
                        )
                        .padding(12.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(game.backgroundImage)
                        .crossfade(true)
                        .build(),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                )
            } else {
                AsyncImage(
                    modifier = Modifier
                        .size(
                            90.dp,
                            130.dp
                        )
                        .padding(12.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(game.backgroundImage)
                        .crossfade(true)
                        .build(),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                )
            }
        }
    }
}