package com.example.project_game_punk.features.search

import com.example.game_punk_domain.domain.entity.GameGenreEntity
import com.example.game_punk_domain.domain.entity.GamePlatformEntity
import com.example.game_punk_domain.domain.interactors.GetAllAvailableGameGenresInteractor
import com.example.game_punk_domain.domain.interactors.GetAllAvailableGamePlatformsInteractor
import com.example.game_punk_domain.domain.models.GameFilter
import com.example.game_punk_domain.domain.models.GameQueryModel
import com.example.game_punk_domain.domain.models.GameSort
import com.example.project_game_punk.features.common.StateViewModel
import com.example.project_game_punk.features.common.ViewModelState
import com.example.project_game_punk.features.common.update
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


val defaultGameFilterUIModel = GameFiltersUIModel(
    gameFilters = GameFilter.values().filter { it != GameFilter.none }.map { filter ->
        GameFilterUIModel(
            filter,
        filter == GameFilter.trending
        )
    }.toList(),
    gameSorts = GameSort.values().map { sort ->
        GameSortUIModel(
            sort,
        false
        )
    }
)

data class GameFiltersUIModel(
    val query: String = "",
    val gameFilters: List<GameFilterUIModel>,
    val gameSorts: List<GameSortUIModel>,
    val gamePlatforms: List<GamePlatformUIModel> = emptyList(),
    val gameGenres: List<GameGenreUIModel> = emptyList(),
    val dateRangeStart: String = "",
    val dateRangeEnd: String = ""
)

data class GameFilterUIModel(
    val filter: GameFilter,
    val isEnabled: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        return (other as? GameFilterUIModel)?.filter == filter
    }
}

data class GamePlatformUIModel(
    val platform: GamePlatformEntity,
    val isEnabled: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        return (other as? GamePlatformUIModel)?.platform?.name == platform.name
    }
}

data class GameGenreUIModel(
    val genre: GameGenreEntity,
    val isEnabled: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        return (other as? GameGenreUIModel)?.genre?.name == genre.name
    }
}

data class GameSortUIModel(
    val sort: GameSort,
    val isEnabled: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        return (other as? GameSortUIModel)?.sort == sort
    }
}

@HiltViewModel
class SearchFiltersViewModel @Inject constructor(
    private val getAllAvailableGamePlatformsInteractor: GetAllAvailableGamePlatformsInteractor,
    private val getAllAvailableGameGenresInteractor: GetAllAvailableGameGenresInteractor
) : StateViewModel<GameFiltersUIModel, Unit>() {

    init {
        loadState()
    }

    override suspend fun loadData(param: Unit?): GameFiltersUIModel {
        val genres = getAllAvailableGameGenresInteractor.execute().map {
            GameGenreUIModel(genre = it)
        }
        val platforms = getAllAvailableGamePlatformsInteractor.execute().map {
            GamePlatformUIModel(platform = it)
        }

        return defaultGameFilterUIModel.copy(
            gamePlatforms = platforms,
            gameGenres = genres
        )
    }

    fun gameFiltersUIModelToGameQuery(
        gameFiltersUIModel: GameFiltersUIModel
    ): GameQueryModel {
        return GameQueryModel(
            query = gameFiltersUIModel.query,
            filter = gameFiltersUIModel.gameFilters.find { it.isEnabled }?.filter ?: GameFilter.none,
            genres = gameFiltersUIModel.gameGenres.filter { it.isEnabled }.map { it.genre },
            sort = gameFiltersUIModel.gameSorts.find { it.isEnabled }?.sort ?: GameSort.none
        )
    }

    fun updateQuery(query: String) {
        getData()?.let { uiModel ->
            val updatedUIModel = uiModel.copy(query = query)
            emit(ViewModelState.SuccessState(updatedUIModel))
        }
    }

    fun updateFilter(filter: GameFilter) {
        getData()?.let { uiModel ->
            val filters = uiModel.gameFilters
            filters.find { it.filter == filter }?.let { filterToUpdate ->
                val updatedFilter = filterToUpdate.copy(isEnabled = !filterToUpdate.isEnabled)
                val updatedFilters = uiModel.gameFilters.toMutableList().apply { update(updatedFilter) }
                val updatedUIModel = uiModel.copy(gameFilters = updatedFilters)
                emit(ViewModelState.SuccessState(updatedUIModel))
            }
        }
    }

    fun updateGenre(genre: GameGenreEntity) {
        getData()?.let { uiModel ->
            val filters = uiModel.gameGenres
            filters.find { it.genre == genre }?.let { genreToUpdate ->
                val updatedGenre = genreToUpdate.copy(isEnabled = !genreToUpdate.isEnabled)
                val updatedGenres = uiModel.gameGenres.toMutableList().apply { update(updatedGenre) }
                val updatedUIModel = uiModel.copy(gameGenres = updatedGenres)
                emit(ViewModelState.SuccessState(updatedUIModel))
            }
        }
    }

    fun updateSort(sort: GameSort) {
        getData()?.let { uiModel ->
            val sorts = uiModel.gameSorts
            sorts.find { it.sort == sort }?.let { sortToUpdate ->
                val updatedSort = sortToUpdate.copy(isEnabled = !sortToUpdate.isEnabled)
                val updatedSorts = uiModel.gameSorts.toMutableList().apply { update(updatedSort) }
                val updatedUIModel = uiModel.copy(gameSorts = updatedSorts)
                emit(ViewModelState.SuccessState(updatedUIModel))
            }
        }
    }

}