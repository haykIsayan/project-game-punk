package com.example.project_game_punk.data.game_collection

import androidx.room.TypeConverter
import com.example.project_game_punk.domain.models.GameModel
import com.example.project_game_punk.domain.models.GameProgressStatus
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken

class GameCollectionConverters {
    @TypeConverter
    fun fromString(value: String?): List<GameModel> {
        val listType = object : TypeToken<List<GameModel?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: List<GameModel?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun toGameProgressStatus(value: String?): GameProgressStatus {
        if (value == null) return GameProgressStatus.playing
        return GameProgressStatus.valueOf(value)
    }

    @TypeConverter
    fun fromGameProgressStatus(gameProgressStatus: GameProgressStatus?): String? {
        return gameProgressStatus?.name
    }
}