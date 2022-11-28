package com.example.project_game_punk.data.game_collection

import androidx.room.TypeConverter
import com.example.project_game_punk.domain.entity.GameEntity
import com.example.project_game_punk.data.models.GameModel
import com.example.project_game_punk.data.models.GameProgressStatus
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken

class GameCollectionConverters {
    @TypeConverter
    fun fromString(value: String?): List<GameEntity> {
        val listType = object : TypeToken<List<GameModel?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: List<GameEntity?>?): String {
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