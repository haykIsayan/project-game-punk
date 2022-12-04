package com.example.project_game_punk.domain.entity

interface GameEntity {
    val id: String?
    val name: String?
    val backgroundImage: String?
    val banners: List<String>?
    val gameArtworks: List<String>?
    val numAdded: Int
    val metaCriticScore: Int
    val isAdded: Boolean
    val gameProgress: GameProgress

    fun updateGameProgress(gameProgress: GameProgress): GameEntity
}




//@PrimaryKey(autoGenerate = true) var uuid: Long = 0,
//@Ignore
//val id: String? = null,
//@Ignore
//val name: String? = null,
//@Ignore
//val background_image: String? = null,
//@Ignore
//val added: Int = 0,
//@Ignore
//val metacritic: Int = 0,
//@Ignore
//val gameStatus: GameStatusModel? = null,
//@Ignore
//val isAdded: Boolean = false,
//) {
//    override fun equals(other: Any?) = (other as? GameModel)?.id == this.id
//}
