package com.example.project_game_punk.features.profile

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.project_game_punk.features.authentication.GamePunkAuthActivity
import com.example.project_game_punk.features.common.composables.*
import com.example.project_game_punk.features.common.composables.carousels.ItemCarousel
import com.example.project_game_punk.features.common.composables.carousels.ItemCarouselDecorators
import com.example.project_game_punk.features.common.composables.carousels.ItemCarouselPill
import com.example.project_game_punk.features.common.game_progress.GameProgressBottomSheetController
import com.example.project_game_punk.features.discover.components.DiscoverGameCarouselLoading
import com.example.project_game_punk.features.discover.components.DiscoverGameFailState
import com.example.project_game_punk.features.discover.playing.NowPlayingSection
import com.example.project_game_punk.features.discover.playing.NowPlayingState
import com.example.project_game_punk.features.discover.playing.NowPlayingViewModel
import com.example.project_game_punk.ui.theme.gamePunkPrimary

@Composable
fun ProfileScreen(
    nowPlayingViewModel: NowPlayingViewModel,
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
                    SignOutIcon(profileUserViewModel = profileUserViewModel) {
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

//            item {
//                Box(modifier = Modifier.fillMaxWidth()) {
//                    Box(modifier = Modifier.align(Alignment.Center)) {
//                        ProfileNameSection(profileUserViewModel = profileUserViewModel)
//                    }
//                }
//            }

            item {
                NowPlayingSection(
                    nowPlayingViewModel = nowPlayingViewModel
                )
            }

            item {
                YourGamesSection(
                    profileUserViewModel = profileUserViewModel,
                    profileViewModel = profileViewModel,
                    controller = controller
                )
            }

            item {
                FavoriteGamesSection(
                    profileUserViewModel = profileUserViewModel,
                    favoriteGamesViewModel = favoriteGamesViewModel,
                    controller = controller
                )
            }
        }
    }
}


@Composable
private fun ProfileTitleSection() {

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
private fun SignOutIcon(
    profileUserViewModel: ProfileUserViewModel,
    onPressed: () -> Unit
) {
    val context = LocalContext.current
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
private fun ProfileHeaderBackground(nowPlayingViewModel: NowPlayingViewModel) {

    val state = nowPlayingViewModel.getState().observeAsState().value
    LoadableStateWrapper(state = state) { nowPlayingState ->

        val gameCover = when (nowPlayingState) {
            is NowPlayingState.NowPlayingAvailable -> nowPlayingState.nowPlayingGames.random().backgroundImage
            is NowPlayingState.NowPlayingUnavailable -> nowPlayingState.games.random().backgroundImage
        }
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(12.dp)
                .clip(RoundedCornerShape(10.dp))
                .blur(22.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(gameCover)
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.FillWidth
            )
        }
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
            .size(110.dp)
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(60.dp),
                imageVector = Icons.Filled.Person,
                contentDescription = ""
            )
        }


//        AsyncImage(
//            modifier = Modifier
//                .clip(CircleShape)
//                .size(25.dp),
//            model = ImageRequest.Builder(LocalContext.current)
//                .data(user?.profileIcon)
//                .crossfade(true)
//                .build(),
//            contentDescription = "",
//            contentScale = ContentScale.FillHeight
//        )
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
                modifier = Modifier
                    .padding(12.dp),
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
private fun YourGamesSection(
    profileUserViewModel: ProfileUserViewModel,
    profileViewModel: ProfileViewModel,
    controller: GameProgressBottomSheetController
) {
    val state = profileViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state,
        failState = { errorMessage -> DiscoverGameFailState(errorMessage) { profileViewModel.loadState() } },
        loadingState = { DiscoverGameCarouselLoading() },
    ) { games ->
        Column {
            SectionTitle(title = "Your Games") {

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
private fun FavoriteGamesSection(
    profileUserViewModel: ProfileUserViewModel,
    favoriteGamesViewModel: FavoriteGamesViewModel,
    controller: GameProgressBottomSheetController
) {
    val state = favoriteGamesViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state,
        failState = { errorMessage -> DiscoverGameFailState(errorMessage) { favoriteGamesViewModel.loadState() } },
        loadingState = { DiscoverGameCarouselLoading() },
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