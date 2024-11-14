package com.example.project_game_punk.features.game_details.sections

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.project_game_punk.features.game_details.GameDetailsViewModel
import com.example.project_game_punk.features.game_details.sections.header.GameDetailsTitle
import com.example.project_game_punk.features.main.GamePunkNavigator
import com.example.project_game_punk.ui.theme.gamePunkPrimaryDark
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalPagerApi::class)
@Composable
fun GameHeaderBackgroundSection(
    gameDetailsViewModel: GameDetailsViewModel,
    screenshots: List<String>
) {

    val state = rememberPagerState()

    Box(
        modifier = Modifier
            .height(260.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 10.dp,
                    topEnd = 10.dp
                )
            )
    ) {

        HorizontalPager(
            count = screenshots.size,
            state = state
        ) { page ->
            val screenshot = screenshots[page]
            GameScreenshotItem(screenshot = screenshot)
        }

        GameHeaderBackgroundSectionCap(
            modifier = Modifier
                .align(Alignment.TopCenter),
            pagerState = state
        )

        GameDetailsTitle(
            modifier = Modifier.align(Alignment.BottomStart),
            gameDetailsViewModel
        ) {

        }

    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun GameHeaderBackgroundSectionCap(
    modifier: Modifier,
    pagerState: PagerState,
) {
    Row(
        modifier = modifier.fillMaxWidth()
            .padding(6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {


        Box(
            Modifier
//                .padding(horizontal = 12.dp)
                .weight(1f)
                .clip(RoundedCornerShape(10.dp))
                .background(gamePunkPrimaryDark.copy(alpha = 0.8f))
            ,
        ) {
            IconButton(onClick = {
                GamePunkNavigator.goBack()
            }) {
                Icon(imageVector = Icons.Filled.KeyboardArrowLeft, contentDescription = "", tint = Color.White)
            }
//            leading?.invoke()
        }


        Box(modifier = Modifier.weight(6f).padding(6.dp)) {
            HorizontalPagerIndicator(
                modifier = Modifier
                    .align(Alignment.Center),
                pagerState = pagerState,
                activeColor = Color.White,
                inactiveColor = Color.White.copy(alpha = 0.5f),
            )
        }



        Box(
            Modifier
//                .padding(horizontal = 12.dp)
                .weight(1f)
                .clip(RoundedCornerShape(10.dp))
                .background(gamePunkPrimaryDark.copy(alpha = 0.8f))
            ,
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "", tint = Color.White)
            }
//            leading?.invoke()
        }
    }
}

@Composable
private fun GameScreenshotItem(screenshot: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = ImageRequest.Builder(LocalContext.current)
                .data(screenshot)
                .crossfade(true)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.Crop,
        )
    }
}