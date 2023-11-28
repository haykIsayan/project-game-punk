package com.example.project_game_punk.features.game_details.sections.game_stores

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import com.example.game_punk_domain.domain.entity.GameStoreEntity
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.SectionTitle
import com.example.project_game_punk.features.common.composables.carousels.ItemCarousel
import com.example.project_game_punk.features.common.composables.carousels.ItemCarouselDecorators
import com.example.project_game_punk.features.common.composables.shimmerBrush
import com.example.project_game_punk.features.game_details.GameWebViewActivity

@Composable
fun GameStoresSection(gameStoresViewModel: GameStoresViewModel) {
    val state = gameStoresViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state,
        loadingState = { GameStoresSectionLoadingState() }
    ) { stores ->
        GameStoresSectionLoadedState(stores)
    }
}

@Composable
private fun GameStoresSectionLoadingState() {
    val showShimmer = remember { mutableStateOf(true) }
    Box(modifier = Modifier
        .padding(12.dp)
        .clip(RoundedCornerShape(10.dp))
        .fillMaxWidth()
        .background(shimmerBrush(showShimmer = showShimmer.value))
        .height(40.dp)
    )
}

@Composable
private fun GameStoresSectionLoadedState(
    stores: List<GameStoreEntity>
) {
    if (stores.isEmpty()) return
    Column {
        SectionTitle(title = "Available")
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
    Box(
        modifier = Modifier
            .border(
                1.dp,
                SolidColor(Color.White),
                shape = RoundedCornerShape(15.dp)
            ).clickable {
                onGameStoresItemClicked(
                    context,
                    store
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = store.name,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
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