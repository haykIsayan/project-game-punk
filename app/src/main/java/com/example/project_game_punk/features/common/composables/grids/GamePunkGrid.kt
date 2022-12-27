package com.example.project_game_punk.features.common.composables.grids

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.example.project_game_punk.data.game.rawg.models.GameModel

@Composable
fun GamePunkGrid(items: List<GameModel>) {
    val splits = mutableListOf<List<GameModel>>()
    var indess = 0
    while (indess <= items.size) {
        val section = items.subList(indess, indess + 3)
        splits.add(section)
        indess += 3
    }



//LazyVerticalGrid(
//columns = GridCells.Adaptive(minSize = 128.dp)
//) {
//    items(photos) { photo ->
//        PhotoItem(photo)
//    }
//}
}