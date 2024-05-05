package com.example.game_punk_collection_data.data.models.game

import com.example.game_punk_domain.domain.entity.GameVideoEntity

data class VideoModel(
    val video_id: String
): GameVideoEntity {
    override val url: String
        get() = video_id
}