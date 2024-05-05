package com.example.project_game_punk.features.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project_game_punk.features.common.composables.carousels.ItemCarouselPill
import com.example.project_game_punk.ui.theme.gamePunkPrimaryLight

//import com.example.project_game_punk.ui.theme.gamePunkPrimary


@Composable
fun SignOutDialog(
    openSignOutDialog: Boolean,
    onDismissPressed: () -> Unit,
    onConfirmPressed:() -> Unit
) {
    if (openSignOutDialog) {
        AlertDialog(
            backgroundColor = gamePunkPrimaryLight,
            shape = RoundedCornerShape(10.dp),
            onDismissRequest = {
                onDismissPressed()
            },
            title = {

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(
                        text = "Do you want to sign out?",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ItemCarouselPill(
                            text = "Yes",
                            onTap = {
                                onConfirmPressed()
                            }
                        )
                        ItemCarouselPill(
                            text = "No",
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
}

