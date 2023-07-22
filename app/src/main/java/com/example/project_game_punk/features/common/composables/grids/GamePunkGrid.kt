package com.example.project_game_punk.features.common.composables.grids

import androidx.compose.runtime.Composable
import com.example.game_punk_domain.domain.entity.GameEntity

@Composable
fun GamePunkGrid(items: List<GameEntity>) {
    val splits = mutableListOf<List<GameEntity>>()
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