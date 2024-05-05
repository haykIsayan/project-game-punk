package com.example.game_punk_collection_data.data.game_collection

import com.example.game_punk_collection_data.data.models.game.GameModel
import com.example.game_punk_domain.domain.entity.GameCollectionEntity
import com.example.game_punk_domain.domain.entity.GameMetaQueryModel
import com.example.game_punk_domain.domain.interfaces.GameCollectionRepository
import com.example.game_punk_domain.domain.interfaces.GameRepository
import com.example.game_punk_domain.domain.models.GameQueryModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class GameCollectionFireStoreSource(
    private val scope: CoroutineScope,
    private val gameRepository: GameRepository,
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) : GameCollectionRepository {

    override suspend fun updateGameCollection(gameCollection: GameCollectionEntity) {
        if (gameCollection !is GameCollectionModel) return
        suspendCoroutine<Unit> { continuation ->
            db.collection(GAME_COLLECTION_DB_COLLECTION_NAME)
                .whereEqualTo(GameCollectionModel.USER_ID_FIELD, gameCollection.userId)
                .whereEqualTo(GameCollectionModel.ID_FIELD, gameCollection.id)
                .get()
                .addOnSuccessListener { snapshot ->

                    if (snapshot.documents.isEmpty()) {
                        continuation.resume(Unit)
                    } else {
                        val reference = snapshot.documents.first().reference.id
                        db.collection(GAME_COLLECTION_DB_COLLECTION_NAME)
                            .document(reference)
                            .update(gameCollection.toMap())
                            .addOnSuccessListener {
                                continuation.resume(Unit)
                            }
                            .addOnFailureListener {
                                continuation.resumeWithException(it)
                            }
                    }
                }.addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }
    }

    override suspend fun createGameCollection(
        gameCollection: GameCollectionEntity
    ): GameCollectionEntity {

        val gameCollectionMap = (gameCollection as GameCollectionModel).toMap()

        return suspendCoroutine { continuation ->
            db.collection(GAME_COLLECTION_DB_COLLECTION_NAME)
                .add(gameCollectionMap)
                .addOnSuccessListener { document ->
                    if (gameCollection.id.isNullOrEmpty()) {
                        val id = document.id
                        val updatedGameCollection = gameCollection.copy(id = id)
                        db.collection(GAME_COLLECTION_DB_COLLECTION_NAME)
                                .document(id)
                                .update(updatedGameCollection.toMap())
                                .addOnSuccessListener {
                                    continuation.resume(updatedGameCollection)
                                }
                                .addOnFailureListener {
                                    continuation.resumeWithException(it)
                                }
                    } else {
                        continuation.resume(gameCollection)
                    }
                }.addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }
    }

    override suspend fun deleteGameCollection(gameCollection: GameCollectionEntity) {
        suspendCoroutine<Unit> { continuation ->
            db.collection(GAME_COLLECTION_DB_COLLECTION_NAME)
                .document(gameCollection.id!!)
                .delete()
                .addOnSuccessListener {
                    continuation.resume(Unit)
                }.addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }
    }

    override suspend fun getGameCollections(userId: String): List<GameCollectionEntity> {
        val gameCollections = suspendCoroutine<List<GameCollectionModel>> { continuation ->
            db.collection(GAME_COLLECTION_DB_COLLECTION_NAME)
                .whereEqualTo(GameCollectionModel.USER_ID_FIELD, userId)
                .get()
                .addOnSuccessListener { documents ->
                    val gameCollections = documents
                        .toObjects(GameCollectionModel::class.java)
                        .map { gameCollectionModel ->

                            val document = documents.find {
                                it.reference.id == gameCollectionModel.id
                            }
                            val gameModels = (document?.get("games") as? ArrayList<*>)?.map {
                                val map = it as? HashMap<*, *>
                                val json = JSONObject(map?.toMutableMap()!!)
                                Gson().fromJson(json.toString(), GameModel::class.java)
                            } ?: emptyList()
                            gameCollectionModel.copy(gameModels = gameModels)
                        }
                    println(gameCollections)
                    continuation.resume(gameCollections)
                }.addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }
        val updatedGameCollections =
            gameCollections.map { gameCollection ->
                scope.async {
                    val games = gameCollection.games
                    val gameIds = games.mapNotNull { it.id }.toList()
                    if (gameIds.isEmpty()) return@async gameCollection
                    val gamesWithMetaData = gameRepository.getGames(
                        GameQueryModel(
                            ids = gameIds,
                            limit = gameIds.size,
                            gameMetaQuery = GameMetaQueryModel(
                                cover = true
                            )
                        )
                    ).map {
                        it as GameModel
                    }
                    gameCollection.copy(gameModels = gamesWithMetaData)
                }
            }.toList().awaitAll()
        return updatedGameCollections
    }

    override suspend fun getGameCollection(id: String, userId: String): GameCollectionEntity? {
        val gameCollection =  suspendCoroutine<GameCollectionModel?> { continuation ->
            db.collection(GAME_COLLECTION_DB_COLLECTION_NAME)
                .whereEqualTo(GameCollectionModel.USER_ID_FIELD, userId)
                .whereEqualTo(GameCollectionModel.ID_FIELD, id)
                .get()
                .addOnSuccessListener { documents ->
                    if (documents.documents.isEmpty()) {
                        continuation.resume(null)
                    } else {
                        val document = documents.documents.first()

                        val gameModels = (document.get("games") as? ArrayList<*>)?.map {
                            val map = it as? HashMap<*, *>
                            val json = JSONObject(map?.toMutableMap()!!)
                            Gson().fromJson(json.toString(), GameModel::class.java)
                        } ?: emptyList()



                        println(gameModels)

                        val gameCollection = document.toObject(GameCollectionModel::class.java)

                        continuation.resume(gameCollection?.copy(gameModels = gameModels))
                    }
                }.addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }

        val games = gameCollection?.games ?: return gameCollection
        val gameIds = games.mapNotNull { it.id }.toList()
        if (gameIds.isEmpty()) return gameCollection
        val gamesWithMetaData = gameRepository.getGames(
            GameQueryModel(
                ids = gameIds,
                limit = gameIds.size,
                gameMetaQuery = GameMetaQueryModel(
                    cover = true
                )
            )
        )

        val gamesWithMetaAndExperience = gamesWithMetaData.map { gameWithMeta ->
            val experience = games.find {
                it.id == gameWithMeta.id
            }?.gameExperience ?: return@map gameWithMeta
            gameWithMeta.updateGameExperience(experience)
        }.map {
            it as GameModel
        }

        return gameCollection.copy(gameModels = gamesWithMetaAndExperience)
    }


    companion object {
        const val GAME_COLLECTION_DB_COLLECTION_NAME = "game_collections"
    }

}