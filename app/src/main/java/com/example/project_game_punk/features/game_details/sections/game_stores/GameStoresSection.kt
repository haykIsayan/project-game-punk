package com.example.project_game_punk.features.game_details.sections.game_stores

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.game_punk_domain.domain.entity.GameStoreEntity
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.SectionTitle
import com.example.project_game_punk.features.common.composables.carousels.ItemCarousel
import com.example.project_game_punk.features.common.composables.carousels.ItemCarouselDecorators
import com.example.project_game_punk.features.common.composables.grids.GamePunkGrid
import com.example.project_game_punk.features.common.composables.shimmerBrush
import com.example.project_game_punk.features.game_details.GameWebViewActivity

@Composable
fun GameStoresSection(
    gameStoresViewModel: GameStoresViewModel,
    reload: () -> Unit = {}
) {
    val state = gameStoresViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state,
        failState = {
            GameStoresSectionFailedState {
                reload()
            }
        },
        loadingState = { GameStoresSectionLoadingState() }
    ) { stores ->
        GameStoresSectionLoadedState(stores)
    }
}


@Composable
private fun GameStoresSectionFailedState(reload: () -> Unit) {
    val showShimmer = remember { mutableStateOf(true) }
    Column {
        SectionTitle(title = "Stores", isLoading = true)
        Box(modifier = Modifier
            .padding(12.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(shimmerBrush(showShimmer = showShimmer.value))
            .fillMaxWidth()
            .height(45.dp)) {
            Icon(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(30.dp)
                    .clickable {
                        reload()
                    },
                imageVector = Icons.Filled.Sync,
                tint = Color.White,
                contentDescription = ""
            )
        }
    }
}

@Composable
private fun GameStoresSectionLoadingState() {
    Column {
        SectionTitle(title = "Stores", isLoading = true)
        val showShimmer = remember { mutableStateOf(true) }
        LazyRow {
            items(4) {
                Box(
                    modifier = Modifier
                        .padding(12.dp)
                        .background(
                            shimmerBrush(
                                showShimmer = showShimmer.value
                            ),
                            shape = CircleShape
                        )
                        .size(45.dp)
                )
            }
        }
    }
}

@Composable
private fun GameStoresSectionLoadedState(
    stores: List<GameStoreEntity>
) {
    if (stores.isEmpty()) return
    Column {
        SectionTitle(title = "Stores")
        ItemCarousel(
            items = stores,
            itemDecorator = ItemCarouselDecorators.pillItemDecorator
        ) {
            GameStoresSectionItem(store = it)
        }
    }
}

@Composable
private fun GameStoresSectionItem(store: GameStoreEntity) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .clickable {
                onGameStoresItemClicked(
                    context,
                    store
                )
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(45.dp),
            painter = painterResource( when (store.slug) {
                "xbox_marketplace" -> {
                    com.example.project_game_punk.R.drawable.ic_xbox_marketplace
                }
                "microsoft" -> {
                    com.example.project_game_punk.R.drawable.ic_microsoft
                }
                "steam" -> {
                    com.example.project_game_punk.R.drawable.ic_steam
                }
                "epic_game_store" -> {
                    com.example.project_game_punk.R.drawable.ic_epic_games
                }
                "xbox_game_pass_ultimate_cloud" -> {
                    com.example.project_game_punk.R.drawable.ic_xbox_game_pass
                }
                "playstation_store_us" -> {
                    com.example.project_game_punk.R.drawable.ic_playstation_store
                }
                "amazon" -> {
                    com.example.project_game_punk.R.drawable.ic_amazon
                }
                else -> {
                    com.example.project_game_punk.R.drawable.ic_playstation_store
                }
            }),
            contentDescription = "Content description for visually impaired"
        )
    }
}


private fun onGameStoresItemClicked(context: Context, store: GameStoreEntity) {
    context.startActivity(
        Intent(
            context,
            GameWebViewActivity::class.java
        ).apply {
            putExtra(
                GameWebViewActivity.URL_INTENT_EXTRA,
                store.url
            )
        }
    )
}