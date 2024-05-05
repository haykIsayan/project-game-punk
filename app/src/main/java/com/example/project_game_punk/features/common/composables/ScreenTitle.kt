package com.example.project_game_punk.features.common.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ScreenTitle(
    leading: @Composable (() -> Unit)? = null,
    title: String,
    trailing: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {


        Box(
            Modifier
                .padding(12.dp)
                .weight(1f),
        ) {
            leading?.invoke()
        }


//
//        Icon(
//            modifier = Modifier
//                .padding(12.dp)
//                .weight(1f)
//                .clickable { onBackPressed() },
//            imageVector = Icons.Filled.KeyboardArrowLeft,
//            contentDescription = "",
//            tint = Color.White
//        )



        Text(
            text = title,
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

        Box(
            Modifier
                .padding(12.dp)
                .weight(1f)
        ) {
            trailing?.invoke()
        }

//
//        Icon(
//            modifier = Modifier
//                .padding(12.dp)
//                .weight(1f)
//                .clickable { onBackPressed() },
//            imageVector = Icons.Filled.MoreHoriz,
//            contentDescription = "",
//            tint = Color.White
//        )
    }
}
