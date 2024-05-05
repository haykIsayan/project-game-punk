package com.example.game_punk_collection_data.data.review

import com.example.game_punk_collection_data.data.game_collection.GameCollectionFireStoreSource
import com.example.game_punk_collection_data.data.game_collection.GameCollectionModel
import com.example.game_punk_collection_data.data.models.game.GameModel
import com.example.game_punk_collection_data.data.models.review.GameReviewDto
import com.example.game_punk_domain.domain.entity.GameReviewEntity
import com.example.game_punk_domain.domain.interfaces.ReviewRepository
import com.example.game_punk_domain.domain.models.ReviewQueryModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class ReviewFireStoreSource(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
): ReviewRepository {
    override suspend fun getReviews(query: ReviewQueryModel): List<GameReviewEntity> {
        return suspendCoroutine { continuation ->
            var dbQuery = db.collection(GameReviewDto.GAME_REVIEW_DB_COLLECTION_NAME)
                .whereIn(
                    "userId",
                    query.userIds
                )
            dbQuery = if (query.gameId.isNotEmpty()) {
                dbQuery.whereEqualTo("gameId", query.gameId)
            } else {
                dbQuery
            }
            dbQuery.get()
                .addOnSuccessListener { documents ->
                    val gameReviews = documents.toObjects(GameReviewDto::class.java)
                    println(gameReviews)
                    continuation.resume(gameReviews)
                }.addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }
    }

    override suspend fun createReview(gameReview: GameReviewEntity): GameReviewEntity {
        val gameReviewMap = (gameReview as? GameReviewDto)?.toMap() ?: return gameReview
        return suspendCoroutine { continuation ->
            db.collection(GameReviewDto.GAME_REVIEW_DB_COLLECTION_NAME)
                .add(gameReviewMap)
                .addOnSuccessListener { document ->
//                    if (gameReview.id.isNullOrEmpty()) {
                        val id = document.id
                        val updatedGameReview = gameReview.copy(id = id)
                        db.collection(GameReviewDto.GAME_REVIEW_DB_COLLECTION_NAME)
                            .document(id)
                            .update(updatedGameReview.toMap())
                            .addOnSuccessListener {
                                continuation.resume(updatedGameReview)
                            }
                            .addOnFailureListener {
                                continuation.resumeWithException(it)
                            }
//                    } else {
//                        continuation.resume(Unit)
//                    }
                }.addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }
    }

    override suspend fun updateReview(gameReview: GameReviewEntity) {
        if (gameReview !is GameReviewDto) return
        return suspendCoroutine {  continuation ->
            db.collection(GameReviewDto.GAME_REVIEW_DB_COLLECTION_NAME)
                .document(gameReview.id)
                .update(gameReview.toMap())
                .addOnSuccessListener {
                    continuation.resume(Unit)
                }
                .addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }
    }

}




