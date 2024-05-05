package com.example.project_game_punk.features.profile.game_collection

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project_game_punk.features.common.composables.carousels.ItemCarouselPill
import com.example.project_game_punk.ui.theme.gamePunkPrimaryLight

@Composable
fun CreateGameCollectionDialog(
    onDismissPressed: () -> Unit,
    onConfirmPressed: (title: String) -> Unit
) {
    val gameCollectionTitle = remember { mutableStateOf("") }
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
                            text = "New Collection Title",
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
                        text = "Create",
                        onTap = {
                            onConfirmPressed(gameCollectionTitle.value)
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