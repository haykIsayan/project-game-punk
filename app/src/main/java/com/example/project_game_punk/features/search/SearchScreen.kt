package com.example.project_game_punk.features.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.project_game_punk.domain.interactors.game.GetGameQueryWithRecentDatesInteractor
import com.example.project_game_punk.domain.models.GameQueryModel
import com.example.project_game_punk.domain.models.GameSort
import com.example.project_game_punk.features.common.composables.GameListItem
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.game_progress.GameProgressBottomSheetController
import com.example.project_game_punk.features.common.game_progress.GameProgressButton

@Composable
fun SearchScreen(
    searchResultsViewModel: SearchResultsViewModel,
    sheetController: GameProgressBottomSheetController,
) {
    val state = searchResultsViewModel.getState().observeAsState().value
    Column {
        SearchField { text ->
            searchResultsViewModel.searchGames(GetGameQueryWithRecentDatesInteractor().execute().copy(query = text))
        }
        LoadableStateWrapper(
            state = state,
            failState = {

            },
            loadingState = {
                Box(contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .padding(6.dp)) {
                    CircularProgressIndicator()
                }
        }) { games ->
            LazyColumn {
                items(games) { game ->
                    GameListItem(
                        game = game,
                        trailingButton = {
                            GameProgressButton(
                                game = game,
                                modifier = Modifier.padding(8.dp),
                                controller = sheetController,
                                onProgressSelected = { game, gameProgress ->
//                                    searchResultsViewModel.updateGameProgress(game, gameProgress)
                                }
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchField(onTextChanged: (String) -> Unit) {
    val text = remember {
        mutableStateOf("")
    }
    TextField(
        value = text.value,
        onValueChange = {
            text.value = it
            onTextChanged.invoke(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .padding(8.dp),
        leadingIcon = @Composable {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null
            )
        }
    )
}