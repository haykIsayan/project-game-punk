package com.example.project_game_punk.features.profile

import android.content.Intent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project_game_punk.features.authentication.GamePunkAuthActivity
import com.example.project_game_punk.features.common.ViewModelState
import com.example.project_game_punk.features.common.composables.*
import com.example.project_game_punk.features.common.composables.carousels.ItemCarousel
import com.example.project_game_punk.features.common.composables.carousels.ItemCarouselDecorators
import com.example.project_game_punk.features.common.game_progress.GameProgressBottomSheetController
import com.example.project_game_punk.features.discover.components.DiscoverGameCarouselLoading
import com.example.project_game_punk.features.discover.components.DiscoverGameFailState
import com.example.project_game_punk.features.discover.playing.ProfileNowPlayingSection
import com.example.project_game_punk.features.discover.playing.NowPlayingViewModel
import com.example.project_game_punk.features.main.GamePunkNavigator
import com.example.project_game_punk.features.profile.artworks.ProfileArtworkCarousel
import com.example.project_game_punk.features.profile.artworks.ProfileArtworksViewModel
import com.example.project_game_punk.features.profile.favorite_games.ProfileFavoriteGamesSection
import com.example.project_game_punk.features.profile.favorite_games.FavoriteGamesViewModel
import com.example.project_game_punk.features.profile.game_collection.CreateGameCollectionDialog
import com.example.project_game_punk.features.profile.game_collection.ProfileGameCollectionsSection
import com.example.project_game_punk.features.profile.game_collection.ProfileGameCollectionsViewModel
//import com.example.project_game_punk.ui.theme.gamePunkPrimary

@Composable
fun ProfileScreen(
    userId: String? = null,
    nowPlayingViewModel: NowPlayingViewModel,
    profileArtworksViewModel: ProfileArtworksViewModel,
    profileUserViewModel: ProfileUserViewModel,
    favoriteGamesViewModel: FavoriteGamesViewModel,
    profileLibraryViewModel: ProfileLibraryViewModel,
    profileGameCollectionsViewModel: ProfileGameCollectionsViewModel,
    controller: GameProgressBottomSheetController
) {
    val context = LocalContext.current
    val openSignOutDialog = remember { mutableStateOf(false) }
    val openCreateGameCollectionDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        nowPlayingViewModel.loadState(userId)
        profileUserViewModel.loadState(userId)
        profileLibraryViewModel.loadState(userId)
        favoriteGamesViewModel.loadState(userId)
        profileArtworksViewModel.loadState(userId)
        profileGameCollectionsViewModel.loadState(userId)
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .animateContentSize()
    ) {
        Box(modifier = Modifier.align(Alignment.Center)) {
            SignOutDialog(
                openSignOutDialog.value,
                {
                    openSignOutDialog.value = false
                }
            ) {
                openSignOutDialog.value = true
                profileUserViewModel.signOut {
                    context.startActivity(
                        Intent(
                            context,
                            GamePunkAuthActivity::class.java
                        ).apply {
                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        }
                    )
                }
            }
        }

        Box(modifier = Modifier.align(Alignment.Center)) {
            if (openCreateGameCollectionDialog.value) {
                CreateGameCollectionDialog(
                    onDismissPressed = {
                        openCreateGameCollectionDialog.value = false
                    },
                    onConfirmPressed = { title ->
                        profileGameCollectionsViewModel
                            .createGameCollection(title) { gameCollection ->
                                gameCollection.id?.let { collectionId ->
                                    GamePunkNavigator.navigate("game_collection/userId/$collectionId")
                                }
                            }
                        openCreateGameCollectionDialog.value = false
                    }
                )
            }
        }

        LazyColumn {

            item {
                ProfileNameSection(
                    userId = userId,
                    profileUserViewModel = profileUserViewModel
                ) {
                    openSignOutDialog.value = true
                }
            }

            item {
                ProfileArtworkCarousel(
                    profileLibraryViewModel = profileLibraryViewModel,
                    profileArtworksViewModel = profileArtworksViewModel,
                    profileUserViewModel = profileUserViewModel
                )
            }

//            item {
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .align(Alignment.Center)
//                        .padding(12.dp)
//                ) {
//                    Box(
//                        modifier = Modifier
//                            .align(Alignment.Center)
//                            .padding(12.dp)
//                    ) {
//                        ProfileSubHeaderSection(
//                            profileUserViewModel = profileUserViewModel,
//                            libraryViewModel = profileLibraryViewModel
//                        )
//                    }
//                }
//            }

            item {
                ProfileFollowUnfollowButton(profileUserViewModel = profileUserViewModel)
            }

//            item {
//                ProfileRecentActivitySection()
//            }

            item {
                ProfileNowPlayingSection(
                    nowPlayingViewModel = nowPlayingViewModel
                )
            }

            item {
                ProfileAllGamesSection(
                    profileLibraryViewModel = profileLibraryViewModel,
                    controller = controller
                )
            }

            item {
                ProfileFavoriteGamesSection(
                    favoriteGamesViewModel = favoriteGamesViewModel,
                    controller = controller
                )
            }

            item {
                ProfileGameCollectionsSection(
                    profileGameCollectionsViewModel,
                    profileUserViewModel.isCurrentUser(userId)
                ) {
                    openCreateGameCollectionDialog.value = true
                }
            }
        }
    }
}

@Composable
private fun SignOutIcon(onPressed: () -> Unit) {
    Icon(
        modifier = Modifier
            .size(22.dp)
            .clickable {
                onPressed()
            },
        imageVector = Icons.Filled.Logout,
        contentDescription = ""
    )
}

@Composable
private fun ProfileNameSection(
    userId: String? = null,
    profileUserViewModel: ProfileUserViewModel,
    onSignOutPressed: () -> Unit
) {
    val state = profileUserViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state,
        loadingState = { DiscoverGameCarouselLoading() },
    ) { user ->
        user?.displayName?.let { displayName ->
            ScreenTitle(
                leading =
                    {
                        Icon(
                            modifier = Modifier
                                .size(18.dp)
                                .clickable {
                                    GamePunkNavigator.goBack()
                                },
                            imageVector = Icons.Filled.ArrowBackIos,
                            contentDescription = ""
                        )
                    },
                title = displayName,
                trailing = if (profileUserViewModel.isCurrentUser(userId)) {
                    {
                        SignOutIcon(onSignOutPressed)
                    }
                } else {
                    null
                }
            )
        }
    }
}

@Composable
private fun ProfileFollowUnfollowButton(
    profileUserViewModel: ProfileUserViewModel
) {

    val state = profileUserViewModel.getState().observeAsState().value
    LoadableStateWrapper(state = state) { user ->
        if (user == null) return@LoadableStateWrapper
        if (profileUserViewModel.isCurrentUser(user.id)) return@LoadableStateWrapper
        val isFollowing = profileUserViewModel.isFollowing(user)
        Box(modifier = Modifier
            .clickable {
                profileUserViewModel.followUnfollow(user)
            }
            .fillMaxWidth()
            .padding(12.dp)
            .height(45.dp)
            .clip(RoundedCornerShape(10.dp))
            .border(
                1.dp,
                SolidColor(Color.White.copy(alpha = 0.1f)),
                shape = RoundedCornerShape(10.dp)
            )
            .background(if (isFollowing) Color.White else Color.Transparent)
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = (if (isFollowing) "Unfollow" else "Follow").uppercase(),
                fontSize = 10.sp,
                letterSpacing = 1.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.ExtraBold,
                color = if (isFollowing) Color.Black else Color.White
            )
        }
    }





}

@Composable
private fun ProfileRecentActivitySection() {
    Column {
        SectionTitle(title = "Activity") {

        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(12.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White.copy(alpha = 0.05f))
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "Recent activity of the user",
                color = Color.White,
            )
        }
    }
}

@Composable
private fun ProfileAllGamesSection(
    profileLibraryViewModel: ProfileLibraryViewModel,
    controller: GameProgressBottomSheetController
) {
    val context = LocalContext.current
    val state = profileLibraryViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state,
        failState = { errorMessage -> DiscoverGameFailState(errorMessage) { profileLibraryViewModel.loadState() } },
        loadingState = { GamesCarouselSectionLoadingState(title = "Library") },
    ) { games ->
        Column {
            SectionTitle(title = "Library") {
                GamePunkNavigator.navigate("library")
            }
            val libraryGames = if (games.size > 10) games.subList(0, 9) else games
            ItemCarousel(
                items = libraryGames,
                itemDecorator = ItemCarouselDecorators.pillItemDecorator
            ) { game ->
                GameCarouselItem(
                    game = game,
                    sheetController = controller
                ) { game, gameProgress ->
                    profileLibraryViewModel.updateGameProgress(game, gameProgress)
                }
            }
        }
    }
}

@Composable
fun GamesCarouselSectionLoadingState(
    title: String
) {
    Column {
        SectionTitle(title = title, isLoading = true)
        LazyRow(content = {
            items(4) {index ->
                val showShimmer = remember { mutableStateOf(true) }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(modifier = Modifier
                        .size(
                            120.dp, 160.dp
                        )
                        .padding(
                            start = if (index == 0) 12.dp else 6.dp,
                            end = 6.dp,
                            top = 6.dp,
                            bottom = 6.dp
                        )
                        .clip(RoundedCornerShape(10.dp))
                        .background(shimmerBrush(showShimmer = showShimmer.value))
                    )
                    Box(modifier = Modifier
                        .width(120.dp)
                        .height(40.dp)
                        .padding(
                            start = if (index == 0) 12.dp else 6.dp,
                            end = 6.dp,
                            top = 6.dp,
                            bottom = 6.dp
                        )
                        .clip(RoundedCornerShape(10.dp))
                        .background(shimmerBrush(showShimmer = showShimmer.value)))
                }
            }
        })
    }
}
