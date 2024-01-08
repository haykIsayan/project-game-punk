package com.example.project_game_punk.features.profile

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
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
import com.example.project_game_punk.features.common.composables.*
import com.example.project_game_punk.features.common.composables.carousels.ItemCarousel
import com.example.project_game_punk.features.common.composables.carousels.ItemCarouselDecorators
import com.example.project_game_punk.features.common.composables.carousels.ItemCarouselPill
import com.example.project_game_punk.features.common.game_progress.GameProgressBottomSheetController
import com.example.project_game_punk.features.discover.components.DiscoverGameCarouselLoading
import com.example.project_game_punk.features.discover.components.DiscoverGameFailState
import com.example.project_game_punk.features.discover.playing.ProfileNowPlayingSection
import com.example.project_game_punk.features.discover.playing.NowPlayingViewModel
import com.example.project_game_punk.features.profile.artworks.ProfileArtworksViewModel
import com.example.project_game_punk.features.profile.favorite_games.ProfileFavoriteGamesSection
import com.example.project_game_punk.features.profile.favorite_games.FavoriteGamesViewModel
import com.example.project_game_punk.features.profile.game_collection.ProfileGameCollectionsSection
import com.example.project_game_punk.ui.theme.gamePunkPrimary

@Composable
fun ProfileScreen(
    nowPlayingViewModel: NowPlayingViewModel,
    profileArtworksViewModel: ProfileArtworksViewModel,
    profileUserViewModel: ProfileUserViewModel,
    favoriteGamesViewModel: FavoriteGamesViewModel,
    profileViewModel: ProfileViewModel,
    controller: GameProgressBottomSheetController
) {
    val context = LocalContext.current
    val openSignOutDialog = remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
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

        LazyColumn {

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    EditProfileIcon()
                    ProfileNameSection(profileUserViewModel = profileUserViewModel)
                    SignOutIcon {
                        openSignOutDialog.value = true
                    }
                }
            }

            item {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier
                        .align(Alignment.Center)
                        .padding(12.dp)) {
                        ProfileIconSection(profileUserViewModel = profileUserViewModel)
                    }
                }
            }

            item {
                ProfileNowPlayingSection(
                    nowPlayingViewModel = nowPlayingViewModel
                )
            }

            item {
                ProfileGamesSection(
                    profileViewModel = profileViewModel,
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
                ProfileGameCollectionsSection()
            }
        }
    }
}

@Composable
private fun EditProfileIcon() {
    Icon(
        modifier = Modifier
            .padding(12.dp)
            .clickable {
            },
        imageVector = Icons.Filled.Settings,
        contentDescription = ""
    )
}

@Composable
private fun SignOutIcon(onPressed: () -> Unit) {
    Icon(
        modifier = Modifier
            .padding(12.dp)
            .clickable {
                onPressed()
            },
        imageVector = Icons.Filled.Logout,
        contentDescription = ""
    )
}

@Composable
private fun ProfileIconSection(profileUserViewModel: ProfileUserViewModel) {
    val state = profileUserViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state,
        loadingState = { DiscoverGameCarouselLoading() },
    ) { user ->
        Box(modifier = Modifier
            .clip(CircleShape)
            .background(gamePunkPrimary)
            .border(
                1.dp,
                SolidColor(Color.White),
                CircleShape
            )
            .size(80.dp)
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(60.dp),
                imageVector = Icons.Filled.Person,
                contentDescription = ""
            )
        }
    }
}

@Composable
private fun ProfileNameSection(profileUserViewModel: ProfileUserViewModel) {
    val state = profileUserViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state,
        loadingState = { DiscoverGameCarouselLoading() },
    ) { user ->

        user?.displayName?.let { displayName ->
            Text(
                text = displayName,
                modifier = Modifier.padding(12.dp),
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                fontSize = 18.sp,
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
private fun ProfileGamesSection(
    profileViewModel: ProfileViewModel,
    controller: GameProgressBottomSheetController
) {
    val state = profileViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state,
        failState = { errorMessage -> DiscoverGameFailState(errorMessage) { profileViewModel.loadState() } },
        loadingState = { GamesCarouselSectionLoadingState(title = "Your Games") },
    ) { games ->
        Column {
            SectionTitle(title = "All Games") {

            }
            ItemCarousel(
                items = games,
                itemDecorator = ItemCarouselDecorators.pillItemDecorator
            ) { game ->
                GameCarouselItem(
                    game = game,
                    sheetController = controller
                ) { game, gameProgress ->
                    profileViewModel.updateGameProgress(game, gameProgress)
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
                            120.dp,
                            160.dp
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

@Composable
private fun SignOutDialog(
    openSignOutDialog: Boolean,
    onDismissPressed: () -> Unit,
    onConfirmPressed:() -> Unit
) {
    if (openSignOutDialog) {
        AlertDialog(
            onDismissRequest = {
                onDismissPressed()
            },
            title = {

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(
                        text = "Do you want to sign out?",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ItemCarouselPill(
                            text = "Yes",
                            onTap = {
                                onConfirmPressed()
                            }
                        )
                        ItemCarouselPill(
                            text = "No",
                            onTap = {
                                onDismissPressed()
                            }
                        )
                    }
                }
            },
            buttons = {}
        )
    }
}