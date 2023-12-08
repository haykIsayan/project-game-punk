package com.example.game_punk_domain.domain.interactors.game

import com.example.game_punk_domain.domain.models.GameQueryModel
import com.example.game_punk_domain.domain.models.GameSort
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class GetGameQueryWithUpcomingDatesInteractor {
    @Suppress("NewApi")
    fun execute(): GameQueryModel {
        val dateStartEndPair = if (/*Build.VERSION.SDK_INT >= Build.VERSION_CODES.O*/true) {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val currentDate = LocalDateTime.now()
            val afterDate = currentDate.withYear(currentDate.year + 1)
            val dateStart = currentDate.format(formatter)
            val dateEnd = afterDate.format(formatter)
            Pair(dateStart, dateEnd)
        } else {
            Pair("","")
        }
        return GameQueryModel(
            sort = GameSort.trending,
            dateRangeStart = dateStartEndPair.first,
            dateRangeEnd = dateStartEndPair.second
        )
    }
}