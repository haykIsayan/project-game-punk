package com.example.project_game_punk.features.discover.countdown

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.game_punk_domain.domain.entity.game.GameEntity
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.SectionTitle
import com.example.project_game_punk.features.common.composables.carousels.ItemCarousel
import com.example.project_game_punk.features.common.composables.carousels.ItemCarouselDecorators
import com.example.project_game_punk.features.common.till
import com.example.project_game_punk.features.common.dateToMillis
import com.example.project_game_punk.features.main.GamePunkNavigator
import com.example.project_game_punk.ui.theme.gamePunkPrimaryDark
import com.example.project_game_punk.ui.theme.gamePunkPrimaryLight
import java.util.Date


@Composable
fun CountdownSection(countdownViewModel: CountdownViewModel) {
    val state = countdownViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state,
        failState = { message ->
            Text(text = message)
        }
    ) { gameReleaseDateStates ->
        CountdownSectionLoadedState(gameReleaseDateStates)
    }
}

@Composable
private fun CountdownSectionLoadedState(gameReleaseDateStates: List<GameReleaseDateState>) {
    val hide = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.animateContentSize()
    ) {
        SectionTitle(
            title = "Upcoming",
            trailing = {
                Icon(
                    modifier = Modifier
                        .padding(12.dp)
                        .clickable {
                            hide.value = !hide.value
                        },
                    imageVector = if (hide.value)
                        Icons.Filled.Visibility
                    else
                        Icons.Filled.VisibilityOff,
                    contentDescription = ""
                )
            }
        )
        if (!hide.value) {
            ItemCarousel(
                items = gameReleaseDateStates.filter {
                    val millis = it.releaseDate.dateToMillis()
                    val period = Date().time.till(millis)
                    (period?.days ?: 0) >= 0
                }.sortedBy {
                    it.releaseDate.dateToMillis()
                },
                itemDecorator = ItemCarouselDecorators.pillItemDecorator
            ) { gameReleaseDateState ->
//                CountdownReleaseItem(gameReleaseDateState)
                CountdownReleaseItemNew(gameReleaseDateState)
            }
        }
    }
}


@Composable
private fun CountdownReleaseItemNew(
    gameReleaseDateState: GameReleaseDateState
) {
    val releaseDate = gameReleaseDateState.releaseDate
    val game = gameReleaseDateState.game


    Box(
        modifier = Modifier
            .width(240.dp)
            .height(140.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
//                .width(260.dp)
//                .height(160.dp)
                .clip(RoundedCornerShape(10.dp)),
            model = ImageRequest.Builder(LocalContext.current)
                .data(gameReleaseDateState.background)
                .crossfade(true)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gamePunkPrimaryDark.copy(alpha = 0.8f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Spacer(modifier = Modifier.height(3.dp))
            game.name?.let { name ->
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    text = name,
                    color = Color.White,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
//                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 18.sp
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            CountdownSectionCountdownInfo(releaseDate)
            Spacer(modifier = Modifier.height(3.dp))
        }
    }
}


@Composable
private fun CountdownReleaseItem(
    gameReleaseDateState: GameReleaseDateState
) {
    val releaseDate = gameReleaseDateState.releaseDate
    val game = gameReleaseDateState.game
    Row(
        modifier = Modifier
            .width(360.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White.copy(alpha = 0.05f))
            .clickable {
                game.id?.let { gameId ->
                    GamePunkNavigator.navigate("game/$gameId")
                }
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        NowPlayingSectionItemGameCover(game = game)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
        ) {
            Spacer(modifier = Modifier.height(3.dp))
            game.name?.let { name ->
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 22.dp),
                    text = name,
                    color = Color.White,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 14.sp
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            CountdownSectionCountdownInfo(releaseDate)
            Spacer(modifier = Modifier.height(3.dp))
        }
    }
}


@Composable
private fun NowPlayingSectionItemGameCover(game: GameEntity) {
    AsyncImage(
        modifier = Modifier
            .size(
                100.dp,
                120.dp
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

@Composable
private fun CountdownSectionCountdownInfo(releaseDate: String) {
    val millis = releaseDate.dateToMillis()

    val period = Date().time.till(millis)
    val years = period?.years
    val months = period?.months
    val days = period?.days
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
            CountDownItem(
                years ?: 0,
                if (years == 1) "Year" else "Years"
            )
            Spacer(modifier = Modifier.width(12.dp))
            CountDownItem(
                months ?: 0,
                "Months"
            )
            Spacer(modifier = Modifier.width(12.dp))

        CountDownItem(
            days ?: 0,
            "Days"
        )
    }
}

@Composable
private fun CountDownItem(time: Int, type: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .size(60.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(gamePunkPrimaryLight.copy(alpha = 0.5f))
    ) {
        Text(
            modifier = Modifier.padding(start = 6.dp, end = 6.dp, top = 6.dp, bottom = 3.dp),
            text = time.toString(),
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Text(
            modifier = Modifier.padding(start = 6.dp, end = 6.dp, bottom = 6.dp, top = 3.dp),
            text = type.uppercase(),
            color = Color.White,
            fontSize = 10.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}