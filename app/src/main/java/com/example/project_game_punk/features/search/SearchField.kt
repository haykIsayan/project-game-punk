package com.example.project_game_punk.features.search

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
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
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper

@Composable
fun SearchField(
    searchResultsViewModel: SearchResultsViewModel,
    searchFiltersViewModel: SearchFiltersViewModel
) {
    val state = searchFiltersViewModel.getState().observeAsState().value
    LoadableStateWrapper(state = state) { uiModel ->
        val gameQuery = searchFiltersViewModel.gameFiltersUIModelToGameQuery(uiModel)
        searchResultsViewModel.searchGames(gameQuery)
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
//                searchResultsViewModel.searchGames(
//                    GameQueryModel(
//                        query = it,
//                        sort = GameSort.none
//                    )
//                )
                    searchFiltersViewModel.updateQuery(it)
                },
                shape = CircleShape,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,

                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
                    .border(
                        1.dp,
                        SolidColor(Color.White),
                        shape = RoundedCornerShape(15.dp)
                    ),
                leadingIcon = @Composable {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        tint = Color.White,
                        contentDescription = null
                    )
                }
            )
        }
    }
}