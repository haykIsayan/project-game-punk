package com.example.project_game_punk.features.game_details.sections.experience

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.entity.GameExperienceEntity
import com.example.game_punk_domain.domain.entity.GamePlatformEntity
import com.example.game_punk_domain.domain.entity.GameStoreEntity
import com.example.project_game_punk.R
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.SectionTitle
import com.example.project_game_punk.features.common.composables.carousels.ItemCarousel
import com.example.project_game_punk.features.common.composables.carousels.ItemCarouselDecorators
import com.example.project_game_punk.features.game_details.GameDetailsViewModel
import com.example.project_game_punk.features.game_details.sections.game_stores.GameStoresViewModel
import kotlinx.coroutines.launch

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
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .clip(RoundedCornerShape(10.dp)),
    ) {
        Column {
            Spacer(modifier = Modifier.height(6.dp))
            GameExperienceUserScoreAndFavorite(
                game = game,
                gameExperience = gameExperience,
                gameDetailsViewModel = gameDetailsViewModel
            )
            GameUserReview(
                game = game,
                gameExperience = gameExperience,
                gameDetailsViewModel = gameDetailsViewModel
            )
            SectionTitle(title = "Platform")
            PlatformsLol(
                game = game,
                gameDetailsViewModel = gameDetailsViewModel
            )
            SectionTitle(title = "Store")
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
private fun PlatformsLol(
    game: GameEntity,
    gameDetailsViewModel: GameDetailsViewModel
) {
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val platforms = game.gamePlatforms ?: return
    val selectedPlatformId = game.gameExperience?.platformId
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
                    scope.launch {
                        listState.animateScrollToItem(index = 0)
                    }
                }
        ) {
            PlatformItem(
                isSelected = platform.id == selectedPlatformId,
                platform = platform
            )
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
            fontWeight = FontWeight.ExtraBold,
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
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val state = gameStoresViewModel.getState().observeAsState().value

    LoadableStateWrapper(state = state) { stores ->

        val selectedStoreSlug = game.gameExperience?.storeId

        val orderedStores = stores.sortedBy { store ->
            store.slug != selectedStoreSlug
        }
        ItemCarousel(
            items = orderedStores,
            state = listState,
            itemDecorator = ItemCarouselDecorators.pillItemDecorator
        ) { store ->
            Box(
                modifier = Modifier
                    .clickable {
                        gameDetailsViewModel.updateGameExperienceStore(
                            game,
                            store.slug
                        )
                        scope.launch {
                            listState.animateScrollToItem(0)
                        }
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
            modifier = Modifier
                .size(55.dp)
                .padding(4.dp),
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
