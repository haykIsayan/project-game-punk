package com.example.game_punk_collection_data.data.game_collection

import com.example.game_punk_collection_data.data.models.game.GameModel
import com.example.game_punk_domain.domain.entity.GameCollectionEntity
import com.example.game_punk_domain.domain.entity.GameMetaQueryModel
import com.example.game_punk_domain.domain.interfaces.GameCollectionRepository
import com.example.game_punk_domain.domain.interfaces.GameRepository
import com.example.game_punk_domain.domain.models.GameQueryModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class GameCollectionFireStoreSource(
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

    override suspend fun createGameCollection(gameCollection: GameCollectionEntity) {

        val gameCollectionMap = (gameCollection as GameCollectionModel).toMap()

        suspendCoroutine<Unit> { continuation ->
            db.collection(GAME_COLLECTION_DB_COLLECTION_NAME)
                .add(gameCollectionMap)
                .addOnSuccessListener { document ->
                    continuation.resume(Unit)
                }.addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }
    }

    override suspend fun getGameCollections(): List<GameCollectionEntity> {
//        return suspendCoroutine<GameCollectionModel?> { continuation ->
//            db.collection(GAME_COLLECTION_DB_COLLECTION_NAME)
//                .whereEqualTo(GameCollectionModel.USER_ID_FIELD, userId)
//                .whereEqualTo(GameCollectionModel.ID_FIELD, id)
//                .get()
//                .addOnSuccessListener { documents ->
//                    val document = documents.documents.first()
//                    val gameCollection = document.toObject<GameCollectionModel>()
//                    continuation.resume(gameCollection)
//                }.addOnFailureListener {
//                    continuation.resumeWithException(it)
//                }
//        }
        TODO("Not yet implemented")
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


                            val betterMap = map?.filter {
                                    entry ->
                                entry.value != null
                            }

                            println(betterMap)

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
        val gamesWithMetaData = gameRepository.getGames(
            GameQueryModel(
                ids = gameIds,
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