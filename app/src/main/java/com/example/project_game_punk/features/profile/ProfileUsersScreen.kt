package com.example.project_game_punk.features.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.game_punk_domain.domain.entity.user.UserEntity
import com.example.project_game_punk.features.common.StateViewModel
import com.example.project_game_punk.features.common.composables.GameCarouselItem
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.ScreenTitle
import com.example.project_game_punk.features.common.composables.grids.GamePunkGrid
import com.example.project_game_punk.features.main.GamePunkNavigator

@Composable
fun ProfileUsersScreen(
    userId: String?,
    title: String,
    stateViewModel: StateViewModel<List<UserEntity>, String>,
) {
    userId ?: return

    LaunchedEffect(Unit) {
        stateViewModel.loadState(userId)
    }

    val state = stateViewModel.getState().observeAsState().value

    Column {
        ScreenTitle(
            leading = {
                Icon(
                    modifier = Modifier.clickable {
                        GamePunkNavigator.goBack()
                    },
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = ""
                )
            },
            title = title
        )
        LoadableStateWrapper(state = state) { users ->
            LazyColumn {
                items(users) { user ->
                    UserResultItem(user = user)
                }
            }
        }
    }
}


@Composable
private fun UserResultItem(user: UserEntity) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .clickable {
                GamePunkNavigator.navigate("user/${user.id}")
            },
    ) {

        user.profileIcon?.let {

        } ?: Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White.copy(alpha = 0.05f)),
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.Center)
                    .clip(RoundedCornerShape(10.dp))
                    .size(60.dp),
                imageVector = Icons.Filled.Person,
                contentDescription = ""
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            user.displayName?.let { displayName ->
                Text(
                    text = displayName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(12.dp),
                    fontSize = 18.sp
                )
            }
        }
    }
}