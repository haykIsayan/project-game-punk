package com.example.project_game_punk.features.game_details.sections.discussions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project_game_punk.features.common.composables.SectionTitle
import com.example.project_game_punk.ui.theme.cyberPunk
import com.example.project_game_punk.ui.theme.redditPrimary
import com.example.project_game_punk.ui.theme.steamPrimary

@Composable
fun GameDiscussionsSection(

) {

    GameDiscussionsSectionLoadedState()
}

@Composable
private fun GameDiscussionsSectionLoadedState() {
    Column {
//        SectionTitle(title = "Discussions")
        LazyRow {
            item {
                Spacer(modifier = Modifier.width(12.dp))
            }

            item {
                GameDiscussionsSectionReviewsItem()
            }

//            item {
//                Spacer(modifier = Modifier.width(12.dp))
//            }

//            item {
//                GameDiscussionsSectionCommentsItem()
//            }

            item {
                Spacer(modifier = Modifier.width(12.dp))
            }

            item {
                GameDiscussionsSectionRedditItem()
            }

            item {
                Spacer(modifier = Modifier.width(12.dp))
            }

            item {
                GameDiscussionsSectionSteamItem()
            }

            item {
                Spacer(modifier = Modifier.width(12.dp))
            }
        }
    }

}


@Composable
private fun GameDiscussionsSectionReviewsItem() {
    Row(modifier = Modifier
        .clip(RoundedCornerShape(10.dp))
        .background(Color.Black)
    ) {

//        Text(
//            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
//            text = "GamePunk",
//            color = Color.White,
//            fontFamily = cyberPunk,
//            fontSize = 16.sp,
//            fontWeight = FontWeight.Bold
//        )

        Text(
            modifier = Modifier.padding(start = 12.dp, end = 6.dp, top = 8.dp, bottom = 8.dp),
            text = "GP",
            color = Color.White,
            fontFamily = cyberPunk,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier.padding(start = 6.dp, end = 12.dp, top = 8.dp, bottom = 8.dp),
            text = "Discussions",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun GameDiscussionsSectionCommentsItem() {
    Row(modifier = Modifier
        .clip(RoundedCornerShape(10.dp))
        .background(Color.Black)
    ) {

//        Text(
//            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
//            text = "GamePunk",
//            color = Color.White,
//            fontFamily = cyberPunk,
//            fontSize = 16.sp,
//            fontWeight = FontWeight.Bold
//        )

        Text(
            modifier = Modifier.padding(start = 12.dp, end = 6.dp, top = 8.dp, bottom = 8.dp),
            text = "GP",
            color = Color.White,
            fontFamily = cyberPunk,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier.padding(start = 6.dp, end = 12.dp, top = 8.dp, bottom = 8.dp),
            text = "Comments",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
private fun GameDiscussionsSectionRedditItem() {
    Row(modifier = Modifier
        .clip(RoundedCornerShape(10.dp))
        .background(redditPrimary)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            text = "Reddit",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
private fun GameDiscussionsSectionSteamItem() {
    Row(modifier = Modifier
        .clip(RoundedCornerShape(10.dp))
        .background(steamPrimary)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            text = "Steam",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}