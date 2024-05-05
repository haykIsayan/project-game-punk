package com.example.project_game_punk.features.game_collection.game_collection_details

import androidx.compose.animation.animateContentSize
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.LibraryAdd
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.game_punk_domain.domain.entity.GameCollectionEntity
import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.project_game_punk.features.common.composables.GameCarouselItem
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.common.composables.carousels.ItemCarouselPill
import com.example.project_game_punk.features.common.composables.grids.GamePunkGrid
import com.example.project_game_punk.features.game_details.sections.GamePunkTab
import com.example.project_game_punk.features.main.GamePunkNavigator
import com.example.project_game_punk.features.profile.ProfileLibraryViewModel
import com.example.project_game_punk.ui.theme.gamePunkPrimaryDark
import com.example.project_game_punk.ui.theme.gamePunkPrimaryLight
//import com.example.project_game_punk.ui.theme.gamePunkPrimary
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GameCollectionDetailsScreen(
    gameCollectionId: String?,
    profileLibraryViewModel: ProfileLibraryViewModel,
    gameCollectionDetailsViewModel: GameCollectionDetailsViewModel
) {
    val isEditing = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val modalState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val openUpdateGameCollectionTitleDialog = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        gameCollectionDetailsViewModel.loadState(gameCollectionId)
    }
    val state = gameCollectionDetailsViewModel.getState().observeAsState().value
    val index = remember { mutableStateOf(0) }

    LoadableStateWrapper(
        state = state
    ) { gameCollectionState ->
        val gameCollection = gameCollectionState.gameCollection ?: return@LoadableStateWrapper
        Box {
            Box(modifier = Modifier.align(Alignment.Center)) {
                if (openUpdateGameCollectionTitleDialog.value) {
                    UpdateGameCollectionTitleDialog(
                        gameCollection,
                        onDismissPressed = {
                            openUpdateGameCollectionTitleDialog.value = false
                        },
                        onConfirmPressed = { name ->
                            gameCollectionDetailsViewModel.updateGameCollectionName(
                                name,
                                gameCollection
                            )
                            openUpdateGameCollectionTitleDialog.value = false
                        }
                    )
                }
            }
            Column(
                modifier = Modifier.animateContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GameCollectionTitleSection(gameCollection = gameCollection) {
                    if (gameCollectionState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(12.dp)
                                .size(20.dp),
                            strokeWidth = 3.dp,
                            color = Color.White
                        )
                    } else if (isEditing.value) {
                        Text(
                            modifier = Modifier
                                .padding(12.dp)
                                .clickable {
                                    isEditing.value = !isEditing.value
                                    index.value = 0
                                },
                            text = "Done",
                            fontSize = 16.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }  else {
                        Icon(
                            modifier = Modifier
                                .padding(12.dp)
                                .weight(1f)
                                .clickable {
                                    scope.launch {
                                        modalState.show()
                                    }
                                },
                            imageVector = Icons.Filled.MoreHoriz,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                }

                if (gameCollectionState.isLoading) {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 12.dp,
                                vertical = 12.dp
                            )
                            .clip(RoundedCornerShape(10.dp))
                        ,
                        color = Color.White,
                    )
                }


                if (isEditing.value) {
                    GamePunkTab(
                        selectedItemIndex = index.value,
                        items = listOf(
                            "Collection",
                            "Library"
                        ),
                        onClick = { newIndex ->
                            index.value = newIndex
                        }
                    )
                }

                when (index.value) {
                    0 -> {
                        GameCollectionGames(
                            gameCollection = gameCollection,
                            gameCollectionDetailsViewModel = gameCollectionDetailsViewModel,
                            isEditing = isEditing.value
                        )
                    }
                    1 -> {
                        GameLibraryGames(
                            gameCollection = gameCollection,
                            profileLibraryViewModel = profileLibraryViewModel,
                            gameCollectionDetailsViewModel = gameCollectionDetailsViewModel
                        )
                    }
                }
            }

            GameCollectionOptionsSheet(
                state = modalState,
                options = listOf(
                    OptionData(
                        icon = Icons.Filled.Edit,
                        text = "Change Name",
                        onOptionPressed = {
                            scope.launch { modalState.hide() }
                            openUpdateGameCollectionTitleDialog.value = true
                        }
                    ),
                    OptionData(
                        icon = Icons.Filled.LibraryAdd,
                        text = "Add/Remove Game",
                        onOptionPressed = {
                            isEditing.value = true
                            scope.launch { modalState.hide() }
                        }
                    ),
                    OptionData(
                        icon = Icons.Filled.Delete,
                        text = "Delete",
                        onOptionPressed = {
                            scope.launch { modalState.hide() }
                            gameCollectionDetailsViewModel
                                .deleteGameCollection(
                                    gameCollection
                                ) {
                                    GamePunkNavigator.goBack()
                                }
                        }
                    )
                )
            )
        }
    }
}

@Composable
private fun GameCollectionGames(
    gameCollection: GameCollectionEntity,
    gameCollectionDetailsViewModel: GameCollectionDetailsViewModel,
    isEditing: Boolean
) {
    GamePunkGrid(
        modifier = Modifier,
        span = 3,
        items = gameCollection.games
    ) { game ->
        GameCollectionItem(
            game = game,
            gameCollection = gameCollection,
            isAdded = gameCollection.games.contains(game),
            isEditing = isEditing,
            gameCollectionDetailsViewModel = gameCollectionDetailsViewModel
        )
    }
}

@Composable
private fun GameLibraryGames(
    gameCollection: GameCollectionEntity,
    gameCollectionDetailsViewModel: GameCollectionDetailsViewModel,
    profileLibraryViewModel: ProfileLibraryViewModel
) {

    LaunchedEffect(Unit) {
        profileLibraryViewModel.loadState()
    }
    val state = profileLibraryViewModel.getState().observeAsState().value
    LoadableStateWrapper(state = state) { games ->
        GamePunkGrid(
            modifier = Modifier,
            span = 3,
            items = games
        ) { game ->
            GameCollectionItem(
                game = game,
                gameCollection = gameCollection,
                isAdded = gameCollection.games.contains(game),
                isEditing = true,
                gameCollectionDetailsViewModel = gameCollectionDetailsViewModel
            )
        }
    }
}


@Composable
private fun GameCollectionItem(
    game: GameEntity,
    gameCollection: GameCollectionEntity,
    isAdded: Boolean,
    isEditing: Boolean,
    gameCollectionDetailsViewModel: GameCollectionDetailsViewModel,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            if (isEditing) {
                Icon(
                    modifier = Modifier
                        .padding(4.dp)
                        .align(Alignment.TopEnd)
                        .clickable {
                            if (isAdded) {
                                gameCollectionDetailsViewModel.removeGameFromGameCollection(
                                    game,
                                    gameCollection
                                )
                            } else {
                                gameCollectionDetailsViewModel.addGameToGameCollection(
                                    game,
                                    gameCollection
                                )
                            }
                        },
                    imageVector = if (isAdded)
                        Icons.Filled.RemoveCircle
                    else
                        Icons.Filled.AddCircle,
                    tint = Color.White,
                    contentDescription = ""
                )
            }
        }
        GameCarouselItem(
            game = game
        )
    }
}

@Composable
private fun GameCollectionTitleSection(
    gameCollection: GameCollectionEntity,
    trailing: @Composable () -> Unit
) {
    gameCollection.name?.let { name ->
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Icon(
                modifier = Modifier
                    .padding(12.dp)
                    .weight(1f)
                    .clickable {
                        GamePunkNavigator.goBack()
                    },
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = "",
                tint = Color.White
            )
            Text(
                text = name,
                modifier = Modifier
                    .weight(6f)
                    .padding(6.dp),
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                fontSize = 16.sp,
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            trailing.invoke()
        }
    }
}

@Composable
fun UpdateGameCollectionTitleDialog(
    gameCollection: GameCollectionEntity,
    onDismissPressed: () -> Unit,
    onConfirmPressed: (title: String) -> Unit
) {
    val gameCollectionTitle = remember { mutableStateOf(gameCollection.name ?: "") }
    AlertDialog(
        backgroundColor = gamePunkPrimaryLight,
        shape = RoundedCornerShape(10.dp),
        onDismissRequest = { onDismissPressed() },
        title = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    gameCollectionTitle.value,
                    onValueChange = { gameCollectionTitle.value = it },
                    shape = CircleShape,
                    textStyle = TextStyle(
                        fontSize = 12.sp,
                    ),
                    placeholder = {
                        Text(
                            text = "New Name",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.5f)
                        )
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.White,
                        backgroundColor = Color.Transparent,
                        cursorColor = Color.White,
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
                    trailingIcon = {
                        if (gameCollectionTitle.value.isNotEmpty()) {
                            Icon(
                                modifier = Modifier
                                    .size(18.dp)
                                    .clickable {
                                        gameCollectionTitle.value = ""
                                    },
                                imageVector = Icons.Filled.Cancel,
                                tint = Color.White,
                                contentDescription = ""
                            )
                        }
                    }
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ItemCarouselPill(
                        text = "Update",
                        onTap = {
                            if (gameCollectionTitle.value.isNotEmpty()) {
                                onConfirmPressed(gameCollectionTitle.value)
                            }
                        }
                    )
                    ItemCarouselPill(
                        text = "Cancel",
                        onTap = {
                            onDismissPressed()
                        }
                    )
                }
            }
        },
        buttons = {}
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun GameCollectionOptionsSheet(
    state: ModalBottomSheetState,
    options: List<OptionData>
) {
    ModalBottomSheetLayout(
        sheetState = state,
        sheetShape = RoundedCornerShape(
            topStart = 10.dp,
            topEnd = 10.dp
        ),
        sheetBackgroundColor = Color.Black,
        sheetContent = {
            Column(
                modifier = Modifier
                    .background(gamePunkPrimaryLight)
                    .padding(horizontal = 12.dp),
            ) {
                options.map { optionData ->
                    OptionItem(optionData = optionData)
                }
            }
        }) {

    }
}

data class OptionData(
    val icon: ImageVector,
    val text: String,
    val onOptionPressed: () -> Unit
)

 @Composable
 private fun OptionItem(
     optionData: OptionData
 ) {
     Row(
         modifier = Modifier
             .fillMaxWidth()
             .height(50.dp)
             .clickable {
                 optionData.onOptionPressed()
             },
         verticalAlignment = Alignment.CenterVertically
     ) {
         Icon(
             imageVector = optionData.icon,
             tint = Color.White,
             contentDescription = ""
         )
         Spacer(modifier = Modifier.width(22.dp))
         Text(
             color = Color.White,
             fontSize = 18.sp,
             text = optionData.text,
             textAlign = TextAlign.Center
         )
     }
}