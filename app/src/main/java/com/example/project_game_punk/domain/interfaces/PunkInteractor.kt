package com.example.project_game_punk.domain.interfaces

interface PunkInteractor<ReturnType> {
    suspend fun execute(): ReturnType
}