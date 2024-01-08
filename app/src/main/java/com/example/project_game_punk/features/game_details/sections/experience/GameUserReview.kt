package com.example.project_game_punk.features.game_details.sections.experience

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.game_punk_domain.domain.entity.GameEntity
import com.example.game_punk_domain.domain.entity.GameExperienceEntity
import com.example.project_game_punk.features.game_details.GameDetailsViewModel


@Composable
fun GameUserReview(
    game: GameEntity,
    gameExperience: GameExperienceEntity,
    gameDetailsViewModel: GameDetailsViewModel
) {
    val userReview = gameExperience.userReview
    val newUserReview = remember { mutableStateOf(userReview) }
    val isEditing = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    Column {
        Box(modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxWidth()
            .animateContentSize()
        ) {
            androidx.compose.animation.AnimatedVisibility(
                modifier = Modifier.align(Alignment.TopEnd),
                visible = isEditing.value) {
                Row {
                    Icon(
                        modifier = Modifier
                            .size(30.dp)
                            .clickable { isEditing.value = false },
                        tint = Color.White,
                        imageVector = Icons.Filled.Close,
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Icon(
                        modifier = Modifier
                            .size(28.dp)
                            .clickable {
                                isEditing.value = false
                                newUserReview.value?.let { newReview ->
                                    gameDetailsViewModel.updateUserReview(
                                        game,
                                        newReview
                                    )
                                }
                            },
                        tint = Color.White,
                        imageVector = Icons.Filled.Check,
                        contentDescription = ""
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
                .clickable {
                    if (!isEditing.value) {
                        newUserReview.value = userReview
                        isEditing.value = true
                    }
                }
                .defaultMinSize(minHeight = 100.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White.copy(alpha = 0.05f))
        ) {
            if (isEditing.value) {
                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }

                TextField(
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .fillMaxSize(),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.White,
                        backgroundColor = Color.Transparent,
                        cursorColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    value = newUserReview.value ?: "",
                    onValueChange = { value ->
                        newUserReview.value = value
                    }
                )
            } else {
                Text(
                    modifier = Modifier.padding(12.dp),
                    text = if (userReview.isNullOrEmpty()) "Add your review" else userReview,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            }
        }
    }
}