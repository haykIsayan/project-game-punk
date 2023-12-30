package com.example.project_game_punk.features.game_details.sections

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.entity.GameExperienceEntity
import com.example.game_punk_domain.domain.entity.GamePlatformEntity
import com.example.game_punk_domain.domain.entity.GameProgressStatus
import com.example.game_punk_domain.domain.entity.GameStoreEntity
import com.example.project_game_punk.R
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.carousels.ItemCarousel
import com.example.project_game_punk.features.common.composables.carousels.ItemCarouselDecorators
import com.example.project_game_punk.features.game_details.GameDetailsViewModel
import com.example.project_game_punk.features.game_details.sections.game_stores.GameStoresViewModel

@Composable
fun GameExperienceSection(
    gameDetailsViewModel: GameDetailsViewModel,
    gameStoresViewModel: GameStoresViewModel
) {
    val state = gameDetailsViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state
    ) { game ->
        game?.gameExperience?.let { gameExperience ->
            GameExperienceSectionLoadedState(
                game = game,
                gameExperience = gameExperience,
                gameDetailsViewModel = gameDetailsViewModel,
                gameStoresViewModel = gameStoresViewModel
            )
        }
    }
}

@Composable
private fun GameExperienceSectionLoadedState(
    game: GameEntity,
    gameExperience: GameExperienceEntity,
    gameDetailsViewModel: GameDetailsViewModel,
    gameStoresViewModel: GameStoresViewModel
) {
    val isFollowed = gameExperience.gameProgressStatus != GameProgressStatus.notFollowing
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .animateContentSize()
            .height(
                if (isFollowed)
                    Int.MAX_VALUE.dp
                else
                    0.dp
            )
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White.copy(alpha = 0.05f))
        ,
    ) {
        Column {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Experience",
                fontSize = /*22.sp*/ 16.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Start,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            UserScoreAndFavorite(
                game = game,
                gameExperience = gameExperience,
                gameDetailsViewModel = gameDetailsViewModel
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Platform",
                fontSize = /*22.sp*/ 16.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Start,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            PlatformsLol(
                game = game,
                gameDetailsViewModel = gameDetailsViewModel
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Store",
                fontSize = /*22.sp*/ 16.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Start,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            StoresLol(
                game = game,
                gameStoresViewModel = gameStoresViewModel,
                gameDetailsViewModel = gameDetailsViewModel
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun UserScoreAndFavorite(
    game: GameEntity,
    gameExperience: GameExperienceEntity,
    gameDetailsViewModel: GameDetailsViewModel
) {
    val isFavorite = gameExperience.favorite
    val score = gameExperience.userScore
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 1..5) {
                val isFilled = (score / 20).toInt() >= i
                Icon(
                    modifier = Modifier
                        .size(35.dp)
                        .clickable {
                            val newScore = i * 20f
                            gameDetailsViewModel.updateUserScore(
                                game,
                                newScore
                            )
                        },
                    imageVector = if (isFilled)
                        Icons.Filled.Star
                    else
                        Icons.Filled.StarBorder,
                    contentDescription = "",
                    tint = Color.White
                )
            }
        }
        Box(
            modifier = Modifier
                .width(0.3.dp)
                .height(25.dp)
                .background(Color.White)
        )
        Icon(
            modifier = Modifier
                .size(35.dp)
                .padding(4.dp)
                .clickable {
                    gameDetailsViewModel.updateIsFavorite(game)
                },
            imageVector = if (isFavorite == true)
                Icons.Filled.Favorite
            else
                Icons.Filled.FavoriteBorder,
            contentDescription = "",
            tint = Color.White
        )
    }
}

@Composable
private fun PlatformsLol(
    game: GameEntity,
    gameDetailsViewModel: GameDetailsViewModel
) {
    val platforms = game.gamePlatforms ?: return
    val selectedPlatformId = game.gameExperience?.platformId

    Box(modifier = Modifier.padding(horizontal = 12.dp)) {
        val listState = rememberLazyListState()
        val orderedPlatforms = platforms.sortedBy { platform ->
            platform.id != selectedPlatformId
        }
        ItemCarousel(
            state = listState,
            items = orderedPlatforms,
            itemDecorator = ItemCarouselDecorators.pillItemDecorator
        ) { platform ->
            Box(
                modifier = Modifier
                    .clickable {
                        gameDetailsViewModel.updateGameExperiencePlatform(
                            game,
                            platform.id
                        )
                    }
            ) {
                PlatformItem(
                    isSelected = platform.id == selectedPlatformId,
                    platform = platform
                )
            }
        }
    }
}

@Composable
private fun PlatformItem(
    isSelected: Boolean,
    platform: GamePlatformEntity
) {
    Box(
        modifier = Modifier
            .border(
                1.dp,
                SolidColor(Color.White),
                shape = RoundedCornerShape(15.dp)
            )
            .clip(RoundedCornerShape(15.dp))
            .background(
                if (isSelected)
                    Color.White
                else
                    Color.Transparent
            )
        ,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = platform.name,
            fontSize = 12.sp,
            color = if (isSelected) Color.Black else Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )
    }
}



@Composable
private fun StoresLol(
    game: GameEntity,
    gameStoresViewModel: GameStoresViewModel,
    gameDetailsViewModel: GameDetailsViewModel
) {
    val state = gameStoresViewModel.getState().observeAsState().value

    LoadableStateWrapper(state = state) { stores ->

        val selectedStoreSlug = game.gameExperience?.storeId

        val orderedStores = stores.sortedBy { store ->
            store.slug != selectedStoreSlug
        }

        Box(modifier = Modifier.padding(horizontal = 12.dp)) {
            ItemCarousel(
                items = orderedStores,
                itemDecorator = ItemCarouselDecorators.pillItemDecorator
            ) { store ->
                Box(
                    modifier = Modifier
                        .clickable {
                            gameDetailsViewModel.updateGameExperienceStore(
                                game,
                                store.slug
                            )
                        }
                ) {
                    GameExperienceStoreItem(
                        isSelecting = store.slug == selectedStoreSlug,
                        store = store
                    )
                }
            }
        }
    }
}

@Composable
private fun GameExperienceStoreItem(
    isSelecting: Boolean,
    store: GameStoreEntity
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(
                if (isSelecting) Color.White.copy(alpha = 0.2f)
                else Color.Transparent
            )
    ) {
        Image(
            modifier = Modifier.size(55.dp).padding(4.dp),
            painter = painterResource( when (store.slug) {
                "xbox_marketplace" -> {
                    R.drawable.ic_xbox_marketplace
                }
                "microsoft" -> {
                    R.drawable.ic_microsoft
                }
                "steam" -> {
                    R.drawable.ic_steam
                }
                "epic_game_store" -> {
                    R.drawable.ic_epic_games
                }
                "xbox_game_pass_ultimate_cloud" -> {
                    R.drawable.ic_xbox_game_pass
                }
                "playstation_store_us" -> {
                    R.drawable.ic_playstation_store
                }
                "amazon" -> {
                    R.drawable.ic_amazon
                }
                else -> {
                    R.drawable.ic_playstation_store
                }
            }),
            contentDescription = "Content description for visually impaired"
        )
    }
}

@Composable
private fun GameUserReview(
    game: GameEntity,
    gameExperience: GameExperienceEntity,
    gameDetailsViewModel: GameDetailsViewModel
) {
    val userReview = gameExperience.userReview

}