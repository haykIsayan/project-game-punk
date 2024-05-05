package com.example.project_game_punk.features.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project_game_punk.features.common.ViewModelState
import com.example.project_game_punk.features.common.composables.LoadableStateWrapper
import com.example.project_game_punk.features.discover.components.DiscoverGameCarouselLoading
import com.example.project_game_punk.features.main.GamePunkNavigator
import com.example.project_game_punk.ui.theme.gamePunkPrimaryDark

//fun ProfileSubHeaderSection




@Composable
fun ProfileSubHeaderSection(
    profileUserViewModel: ProfileUserViewModel,
    libraryViewModel: ProfileLibraryViewModel
) {

    val state = profileUserViewModel.getState().observeAsState().value
    LoadableStateWrapper(
        state = state,
        loadingState = { DiscoverGameCarouselLoading() },
    ) { user ->

        LazyRow {
            item {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White.copy(alpha = /*0.08f*/0.1f))
//                        .background(gamePunkPrimaryDark/*.copy(alpha = 0.8f)*/)

                ) {
                    Icon(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(60.dp),
                        imageVector = Icons.Filled.Person,
                        contentDescription = ""
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.width(12.dp))
            }

            item {
                val games = (libraryViewModel.getState().value as? ViewModelState.SuccessState)?.data ?: emptyList()

                Box(
                    Modifier.clickable {
                        GamePunkNavigator.navigate("library")
                    }
                ) {
                    ProfileSubHeaderItem(
                        count = games.size,
                        text = "Games"
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.width(12.dp))
            }

            item {

                Box(Modifier.clickable {
                    GamePunkNavigator
                        .navigate("following/${user?.id}")
                }) {
                    ProfileSubHeaderItem(
                        count = (user?.following?.size ?: 0),
                        text = "Following"
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.width(12.dp))
            }

            item {

                Box(Modifier.clickable {
                    GamePunkNavigator
                        .navigate("followers/${user?.id}")
                }) {
                    ProfileSubHeaderItem(
                        count = (user?.followers?.size ?: 0),
                        text = "Followers"
                    )
                }
            }
        }
    }
}

@Composable
private fun ProfileSubHeaderItem(
    count: Int,
    text: String
) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .clip(RoundedCornerShape(10.dp))
//            .background(gamePunkPrimaryDark/*.copy(alpha = 0.8f)*/)
            .background(Color.White.copy(alpha = /*0.08f*/0.1f))
    ) {
        Column(
            Modifier
                .padding(3.dp)
                .align(Alignment.Center),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = count.toString(),
                fontSize = 28.sp,
                color = Color.White,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = text,
                fontSize = 12.sp,
                color = Color.White,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}



//32

//18