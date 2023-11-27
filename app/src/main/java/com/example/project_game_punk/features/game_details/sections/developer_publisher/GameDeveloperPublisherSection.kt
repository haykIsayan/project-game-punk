package com.example.project_game_punk.features.game_details.sections.developer_publisher

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.game_punk_domain.domain.entity.GameCompanyEntity
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.shimmerBrush


@Composable
fun GameDeveloperPublisherSection(
    gameDeveloperPublisherViewModel: GameDeveloperPublisherViewModel
) {
    val state = gameDeveloperPublisherViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state,
        loadingState = { GameDeveloperPublisherSectionLoadingState() }
    ) { companies ->
        GameDeveloperPublisherSectionLoadedState(
            companies = companies
        )
    }
}

@Composable
private fun GameDeveloperPublisherSectionLoadingState() {
    val showShimmer = remember { mutableStateOf(true) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(6.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(shimmerBrush(showShimmer = showShimmer.value))
        )
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(6.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(shimmerBrush(showShimmer = showShimmer.value))
        )
    }
}

@Composable
private fun GameDeveloperPublisherSectionLoadedState(
    companies: List<GameCompanyEntity>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        companies.find { it.isDeveloper }?.let { developer ->
            GameDeveloperPublisherItem(
                label = "Developer",
                name = developer.name
            )
        }
        companies.find { it.isPublisher }?.let { publisher ->
            GameDeveloperPublisherItem(
                label = "Publisher",
                name = publisher.name
            )
        }
    }
}

@Composable
private fun GameDeveloperPublisherItem(
    label: String,
    name: String
) {
    Row(
        modifier = Modifier.padding(6.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            modifier = Modifier.alignByBaseline(),
            text = label,
            color = Color.LightGray,
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold
        )
        Box(modifier = Modifier.width(6.dp))
        Text(
            modifier = Modifier.alignByBaseline(),
            text = name,
            color = Color.White,
            fontSize = 16.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold
        )
    }
}
