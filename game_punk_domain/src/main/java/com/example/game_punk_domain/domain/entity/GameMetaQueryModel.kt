package com.example.game_punk_domain.domain.entity

data class GameMetaQueryModel(
    val cover: Boolean = true,
    val slug: Boolean = true,
    val banner: Boolean = false,
    val platforms: Boolean = false,
    val genres: Boolean = false,
    val screenshots: Boolean = false,
    val synopsis: Boolean = false,
    val ageRating: Boolean = false,
    val score: Boolean = false,
    val releaseDate: Boolean = false,
    val stores: Boolean = false,
    val similarGames: Boolean = false,
    val dlcs: Boolean = false,
    val steamId: Boolean = false,
    val keywords: Boolean = false,
) {
    override fun equals(other: Any?): Boolean {
        val otherGame = other as? GameMetaQueryModel ?: return false
        return otherGame.let {
            cover == it.cover &&
                    slug == it.slug &&
                    banner == it.banner &&
                    platforms == it.platforms &&
                    genres == it.genres &&
                    screenshots == it.screenshots &&
                    synopsis == it.synopsis &&
                    ageRating == it.ageRating &&
                    score == it.score &&
                    releaseDate == it.releaseDate &&
                    similarGames == it.similarGames &&
                    stores == it.stores &&
                    dlcs == it.dlcs &&
                    steamId == it.steamId &&
                    keywords == it.steamId
        }
    }
}