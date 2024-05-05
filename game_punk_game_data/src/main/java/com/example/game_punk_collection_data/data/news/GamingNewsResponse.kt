package com.example.game_punk_collection_data.data.news

import com.example.game_punk_domain.domain.entity.GamingNewsEntity

data class GamingNewsResponse(
    val articles: List<GamingNewsArticle>
)

data class GamingNewsArticle(
    override val title: String,
    override val author: String,
    val source: GamingNewsArticleSource
): GamingNewsEntity {
    override val newsSource: String
        get() = source.name
}

data class GamingNewsArticleSource(
    val id: String,
    val name: String
)