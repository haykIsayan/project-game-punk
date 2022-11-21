package com.example.project_game_punk.domain.interfaces

interface PunkParamInteractor<ReturnType, ParamType> {
    suspend fun execute(param: ParamType): ReturnType
}