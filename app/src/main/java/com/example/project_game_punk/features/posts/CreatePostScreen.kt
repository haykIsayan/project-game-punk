package com.example.project_game_punk.features.posts

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project_game_punk.features.common.ViewModelState
import com.example.project_game_punk.ui.theme.gamePunkPrimaryLight

@Composable
fun CreatePostScreen(
    viewModel: CreatePostViewModel
) {
//    LaunchedEffect(Unit) {
//        if (context is Activity) {
//            context.forre
//        }
//    }


    val state = viewModel.getState().observeAsState().value as? ViewModelState.SuccessState
//    Column {
    Box {
        TextField(
            modifier = Modifier.fillMaxSize(),
            value = "state.data.text",
            onValueChange = {},
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = gamePunkPrimaryLight,
                focusedIndicatorColor =  gamePunkPrimaryLight, //hide the indicator
                unfocusedIndicatorColor = gamePunkPrimaryLight
            )
        )
        CreatePostBottomPanel(modifier = Modifier.align(Alignment.BottomStart))

    }

}

@Composable
private fun CreatePostBottomPanel(modifier: Modifier) {
    Box(modifier = modifier
        .padding(12.dp)
    ) {
        Box(
            modifier = Modifier
                .border(
                    1.dp,
                    SolidColor(Color.White.copy(alpha = 0.1f)),
                    shape = RoundedCornerShape(15.dp)
                )
                .align(Alignment.BottomStart)
                .clip(RoundedCornerShape(15.dp))
                .background(
//                    if (isSelected)
//                        Color.White
//                    else
                    Color.Transparent
                )
            ,
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = /*platform.name*/"Add Game",
                fontSize = 14.sp,
                color = /*if (isSelected) Color.Black else */Color.White,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(8.dp)
            )
        }

    }

}