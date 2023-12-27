package com.example.project_game_punk.features.discover.featured

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.entity.GameProgressStatus
import com.example.project_game_punk.R
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.discover.components.DiscoverGameFailState
import com.example.project_game_punk.features.common.composables.carousels.ItemCarousel
import com.example.project_game_punk.features.common.composables.carousels.ItemCarouselDecorators
import com.example.project_game_punk.features.common.composables.shimmerBrush
import com.example.project_game_punk.features.common.game_progress.GameProgressBottomSheetController
import com.example.project_game_punk.features.common.game_progress.GameProgressButton
import com.example.project_game_punk.features.game_details.GameDetailsActivity
import com.example.project_game_punk.features.game_details.largeRadialGradientBrush
import com.example.project_game_punk.ui.theme.gamePunkAlt
import com.example.project_game_punk.ui.theme.gamePunkPrimary
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

@Composable
fun FeaturedGameSection(
    featuredGameViewModel: FeaturedGameViewModel,
    sheetController: GameProgressBottomSheetController
) {
    val  state = featuredGameViewModel.getState().observeAsState().value

    Column {
        LoadableStateWrapper(
            state = state,
            failState = { errorMessage ->
                DiscoverGameFailState(text = errorMessage) {
                    featuredGameViewModel.loadState()
                }
            }, loadingState = { FeaturedGameLoadingState() },
        ) { uiModel ->
            FeatureGameLoadedState(
                uiModel = uiModel,
                sheetController = sheetController
            ) { game, progress ->
                featuredGameViewModel.updateGameProgress(game, progress)
            }
        }
    }
}

sealed class FeaturedGameItem(
    val game: GameEntity?
) {
    class ScreenshotFeaturedGameItem(
        game: GameEntity? = null,
        val screenshot: String? = null
    ): FeaturedGameItem(game)

    class CoverFeaturedGameItem(
        game: GameEntity? = null,
    ): FeaturedGameItem(game)

    class DetailsFeaturedGameItem(
        game: GameEntity? = null
    ): FeaturedGameItem(game)
}

@Composable
private fun FeatureGameLoadedState(
    uiModel: FeaturedGameUiModel,
    sheetController: GameProgressBottomSheetController,
    onProgressSelected: (GameEntity, GameProgressStatus) -> Unit,
) {
    val context = LocalContext.current

    val scope = rememberCoroutineScope()
    val colorOne = remember {
        Animatable(gamePunkAlt)
    }
    val colorTwo = remember {
        Animatable(gamePunkPrimary)
    }

    val items = mutableListOf<FeaturedGameItem>()
    items.add(FeaturedGameItem.CoverFeaturedGameItem(uiModel.game))
    items.add(FeaturedGameItem.DetailsFeaturedGameItem(uiModel.game))

    items.addAll(uiModel.screenshots.map { screenshot ->
        FeaturedGameItem.ScreenshotFeaturedGameItem(uiModel.game, screenshot)
    })
    Box(
        modifier = Modifier.clickable {
            context.startActivity(
                Intent(
                    context,
                    GameDetailsActivity::class.java
                ).apply {
                    putExtra(
                        GameDetailsActivity.GAME_ID_INTENT_EXTRA,
                        uiModel.game.id
                    )
                }
            )
        }
    ) {
        ItemCarousel(
            items = items,
            itemDecorator = ItemCarouselDecorators.pillItemDecorator
        ) {
            when (it) {
                is FeaturedGameItem.CoverFeaturedGameItem -> {
                    it.game?.let { game ->
                        FeaturedGameCover(
                            game = game,
                            sheetController = sheetController,
                            onProgressSelected = onProgressSelected
                        ) {
                            scope.launch {
                                listOf(
                                    async {
                                        colorOne.animateTo(
                                            Color(it).copy(alpha = 0.7f),
                                            animationSpec = tween(500)
                                        ) },
                                    async {
                                        colorTwo.animateTo(
                                            Color(it).copy(alpha = 0.4f),
                                            animationSpec = tween(500)
                                        ) }
                                ).awaitAll()
                            }
                        }
                    }


                }
                is FeaturedGameItem.ScreenshotFeaturedGameItem -> {
                    it.screenshot?.let { screenshot ->
                        FeaturedGameScreenshot(screenshot = screenshot)
                    }
                }
                is FeaturedGameItem.DetailsFeaturedGameItem -> {
                    it.game?.let { game ->
                        FeaturedGameDetails(
                            game = game,
                            colorOne = colorOne.value,
                            colorTwo = colorTwo.value
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FeaturedGameDetails(
    game: GameEntity,
    colorOne: Color,
    colorTwo: Color
) {
    Box(modifier = Modifier
        .size(
            250.dp,
            192.dp
        )
        .clip(RoundedCornerShape(10.dp))
        .background(Color.Black)
    ) {
        Box(
            modifier = Modifier
                .size(
                    250.dp,
                    192.dp
                )
                .clip(RoundedCornerShape(10.dp))
                .background(
                    largeRadialGradientBrush(
                        listOf(
                            colorOne.copy(alpha = 0.9f),
                            colorTwo.copy(alpha = 0.7f),
                        )
                    )
                )
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = "Featured Game",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 10.sp
                )
                game.name?.let { name ->
                    Text(
                        text = name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = game.gameGenres?.joinToString(", ") { it.name } ?: "",
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = game.description ?: "",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
private fun FeaturedGameScreenshot(screenshot: String) {
    Box(
        modifier = Modifier
            .size(
                280.dp,
                192.dp
            )
            .clip(RoundedCornerShape(10.dp))
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(screenshot)
                .crossfade(true)
                .build(),
            contentDescription = "",
            contentScale = ContentScale.FillHeight,
        )
    }
}


@Composable
private fun FeaturedGameCover(
    game: GameEntity,
    sheetController: GameProgressBottomSheetController,
    onProgressSelected: (GameEntity, GameProgressStatus) -> Unit,
    onColorLoaded: (Int) -> Unit
) {
    Column {
        Box(
            modifier = Modifier
                .size(
                    110.dp,
                    150.dp
                )
                .clip(RoundedCornerShape(10.dp))
        ) {
            val context = LocalContext.current
            val loader = ImageLoader(LocalContext.current)
            val req = ImageRequest.Builder(LocalContext.current)
                .data(game.backgroundImage)
                .allowHardware(false)
                .target { result ->
                    val bitmap = (result as BitmapDrawable).bitmap
                    Palette.from(bitmap).generate {
                        it?.let { palette ->
                            val dominantColor = palette.getDominantColor(
                                ContextCompat.getColor(
                                    context,
                                    R.color.black
                                )
                            )
                            onColorLoaded.invoke(dominantColor)
                        }
                    }
                }
                .build()
            loader.enqueue(req)
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(game.backgroundImage)
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.FillHeight,
            )
        }
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(12.dp))
        GameProgressButton(
            game = game,
            modifier = Modifier.width(110.dp),
            onProgressSelected = onProgressSelected,
            controller = sheetController
        )
    }
}

@Composable
private fun FeaturedGameLoadingState() {
    val items = mutableListOf<FeaturedGameItem>()
    items.add(FeaturedGameItem.CoverFeaturedGameItem())
    items.add(FeaturedGameItem.DetailsFeaturedGameItem())
    for (i in 0..5) {
        items.add(FeaturedGameItem.ScreenshotFeaturedGameItem())
    }
    val showShimmer = remember { mutableStateOf(true) }
    ItemCarousel(
        items = items,
        itemDecorator = ItemCarouselDecorators.pillItemDecorator
    ) {
        when (it) {
            is FeaturedGameItem.CoverFeaturedGameItem -> {

                Column {
                    Box(
                        modifier = Modifier
                            .size(110.dp, 150.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(shimmerBrush(showShimmer = showShimmer.value))
                    )
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(12.dp))
                    Box(modifier = Modifier
                        .width(110.dp)
                        .height(30.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(shimmerBrush(showShimmer = showShimmer.value)))
                }
            }
            is FeaturedGameItem.ScreenshotFeaturedGameItem -> {
                Box(
                    modifier = Modifier
                        .size(
                            280.dp,
                            192.dp
                        )
                        .clip(RoundedCornerShape(10.dp))
                        .background(shimmerBrush(showShimmer = showShimmer.value))
                )
            }
            is FeaturedGameItem.DetailsFeaturedGameItem -> {
                Box(
                    modifier = Modifier
                        .size(
                            250.dp,
                            192.dp
                        )
                        .clip(RoundedCornerShape(10.dp))
                        .background(shimmerBrush(showShimmer = showShimmer.value))
                )
            }
        }
    }
}