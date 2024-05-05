package com.example.project_game_punk.features.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.shimmerBrush
import com.example.project_game_punk.features.main.GamePunkNavigator

@Composable
fun SearchField(
    index: Int,
    searchGamesViewModel: SearchGamesViewModel,
    searchUsersViewModel: SearchUsersViewModel,
    searchFiltersViewModel: SearchFiltersViewModel
) {
    val state = searchFiltersViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state,
        loadingState = {
            val shimmer = remember { mutableStateOf(true) }
            Box(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                TextField(
                    value = "",
                    onValueChange = {},
                    shape = RoundedCornerShape(10.dp),
                    enabled = false,
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        cursorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(shimmerBrush(showShimmer = shimmer.value))
                )
            }
        }
    ) { uiModel ->
        val gameQuery = searchFiltersViewModel.gameFiltersUIModelToGameQuery(uiModel)
        searchGamesViewModel.searchGames(gameQuery)
        Box(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            val text = remember {
                mutableStateOf(uiModel.query)
            }
            TextField(
                value = text.value,
                onValueChange = {
                    text.value = it
                    when (index) {
                        0 -> {
                            searchFiltersViewModel.updateQuery(it)
                        }
                        1 -> {
                            searchUsersViewModel.searchUsers(it)
                        }
                    }
                },
                shape = RoundedCornerShape(10.dp),
                singleLine = true,
                placeholder = {
                    Text(
                        text = when (index){
                            0 -> "Search Games"
                            1 -> "Search Users"
                            else -> ""
                        },
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.5f)
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White.copy(alpha = 0.05f),
                    cursorColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp)),
                leadingIcon = {
                    Icon(
                        modifier = Modifier.clickable {
                          GamePunkNavigator.goBack()
                        },
                        imageVector = Icons.Filled.KeyboardArrowLeft,
                        tint = Color.White,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    if (text.value.isNotEmpty()) {
                        Icon(
                            modifier = Modifier.clickable {
                                text.value = ""
                            },
                            imageVector = Icons.Filled.Close,
                            tint = Color.White,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    }
}