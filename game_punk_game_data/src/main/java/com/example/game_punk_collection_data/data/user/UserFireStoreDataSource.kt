package com.example.game_punk_collection_data.data.user

import com.example.game_punk_collection_data.data.models.user.UserModel
import com.example.game_punk_domain.domain.entity.GameExperienceEntity
import com.example.game_punk_domain.domain.entity.user.UserAuthModel
import com.example.game_punk_domain.domain.entity.user.UserEntity
import com.example.game_punk_domain.domain.interfaces.GameCollectionRepository
import com.example.game_punk_domain.domain.interfaces.UserRepository
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class UserFireStoreDataSource(
    private val gameCollectionRepository: GameCollectionRepository,
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val scope: CoroutineScope,
): UserRepository {

    override suspend fun createUser(userAuthModel: UserAuthModel): UserEntity {
        return suspendCoroutine { continuation ->
            val email = userAuthModel.email
            val password = userAuthModel.password
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { authResult ->
                    val user = authResult.user
                    val request = UserProfileChangeRequest.Builder()
                        .setDisplayName(userAuthModel.displayName)
                        .build()
                    user?.updateProfile(request)?.addOnSuccessListener {
                        val id = user.uid



                        val userModelMap = userAuthModel.createUser(id).toMap()
                        db.collection(USER_DB_COLLECTION_NAME)
                            .add(userModelMap).addOnSuccessListener {
                                continuation.resume(userAuthModel.createUser(id))
                            }.addOnFailureListener { exception ->
                                continuation.resumeWithException(exception)
                            }


                    }?.addOnFailureListener { exception ->
                        continuation.resumeWithException(exception)
                    }
                }
                .addOnFailureListener { exception ->
                    val signUpException = when (exception) {
                        is FirebaseAuthUserCollisionException -> EmailAlreadyInUseException()
                        is FirebaseAuthWeakPasswordException -> PasswordWeakException()
                        is FirebaseException -> {
                            val reason = exception.message
                            if (reason?.contains(FIREBASE_EMAIL_BADLY_FORMATTED) == true)
                                EmailFormatIncorrectException()
                            else
                                exception
                        }
                        else -> exception
                    }
                    continuation.resumeWithException(signUpException)
                }
        }
    }

    override suspend fun signIn(email: String, password: String): UserEntity {
        return suspendCoroutine { continuation ->
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { authResult ->
                    val firebaseUser = authResult.user
                    val gamePunkUser = UserModel(
                        id = firebaseUser?.uid,
                        email = firebaseUser?.email ?: "",
                        displayName = firebaseUser?.displayName ?: "",
                        password = "",
                        profileIcon = ""
                    )
                    continuation.resume(gamePunkUser)
                }
                .addOnFailureListener { exception ->
                    val signInException = when (exception) {
                        is FirebaseException -> {
                            val reason = exception.message
                            if (reason?.contains(FIREBASE_INVALID_LOGIN_CREDENTIALS) == true)
                                IncorrectPasswordException()
                            else
                                exception
                        }
                        else -> exception
                    }
                    continuation.resumeWithException(signInException)
                }
        }
    }

    override suspend fun signOut() {
        FirebaseAuth.getInstance().signOut()
    }

    override suspend fun getCurrentUser(): UserEntity {
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val id = firebaseUser?.uid ?: return UserModel(
            id = firebaseUser?.uid,
            email = firebaseUser?.email ?: "",
            displayName = firebaseUser?.displayName ?: "",
            password = "",
            profileIcon = ""
        )
        return getUserById(firebaseUser?.uid)
    }

    override suspend fun getUserById(userId: String): UserEntity {
        return suspendCoroutine { continuation ->
            db.collection(USER_DB_COLLECTION_NAME)
                .whereEqualTo("id", userId)
                .get()
                .addOnSuccessListener { documents ->
                    val document = documents.documents.first()
                    val userModel = document.toObject(UserModel::class.java)
                    continuation.resume(userModel!!)
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
    }

    override suspend fun getUserByDisplayName(displayName: String): List<UserEntity> {
        return suspendCoroutine { continuation ->
            db.collection(USER_DB_COLLECTION_NAME)
                .whereEqualTo("displayName", displayName)
                .get()
                .addOnSuccessListener { documents ->
                    val userModels = documents.toObjects(UserModel::class.java)
                    continuation.resume(userModels)
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
    }

    suspend fun updateUser(user: UserEntity) {
        val userId = user.id
        suspendCoroutine<Unit> { continuation ->
            db.collection(USER_DB_COLLECTION_NAME)
                .whereEqualTo("id", userId)
                .get()
                .addOnSuccessListener { snapshot ->
                    if (snapshot.documents.isEmpty()) {
                        continuation.resume(Unit)
                    } else {
                        val reference = snapshot.documents.first().reference.id
                        db.collection(USER_DB_COLLECTION_NAME)
                            .document(reference)
                            .update(user.toMap())
                            .addOnSuccessListener {
                                continuation.resume(Unit)
                            }
                            .addOnFailureListener {
                                continuation.resumeWithException(it)
                            }
                    }
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        }
    }

    override suspend fun followUser(userId: String, userIdToFollow: String) {
        (getUserById(userId) as? UserModel)?.let {
            it.copy(following = it.following?.toMutableList()?.apply { add(userIdToFollow) })
        }?.let { updatedUser ->
            updateUser(updatedUser)
        }
        (getUserById(userIdToFollow) as? UserModel)?.let {
            it.copy(followers = it.followers?.toMutableList()?.apply { add(userId) })
        }?.let { updatedUserToFollow ->
            updateUser(updatedUserToFollow)
        }
    }

    override suspend fun unfollowUser(userId: String, userIdToUnfollow: String) {
        (getUserById(userId) as? UserModel)?.let {
            it.copy(following = it.following?.toMutableList()?.apply { remove(userIdToUnfollow) })
        }?.let { updatedUser ->
            updateUser(updatedUser)
        }
        (getUserById(userIdToUnfollow) as? UserModel)?.let {
            it.copy(followers = it.followers?.toMutableList()?.apply { remove(userId) })
        }?.let { updatedUserToFollow ->
            updateUser(updatedUserToFollow)
        }
    }

    override suspend fun getUserFollowers(userId: String): List<UserEntity> {
        val user = getUserById(userId)
        val followers = user.followers
        val followerUsers = followers?.map { followerUserId ->
            scope.async {
                getUserById(followerUserId)
            }
        }?.awaitAll()
        return followerUsers ?: emptyList()
    }

    override suspend fun getUserFollowing(userId: String): List<UserEntity> {
        val user = getUserById(userId)
        val following = user.following
        val followingUsers = following?.map { followingUserId ->
            scope.async {
                getUserById(followingUserId)
            }
        }?.awaitAll()
        return followingUsers ?: emptyList()
    }

    override suspend fun getFollowingUserReviewsForGame(
        userId: String,
        gameId: String
    ): List<GameExperienceEntity> {
        val following = getUserFollowing(userId)
        return following.map { followingUser ->
            scope.async {
                gameCollectionRepository.getGameCollection(
                    "main",
                    followingUser.id!!
                )?.games?.find {
                    it.id == gameId
                }?.gameExperience
            }
        }.awaitAll().filterNotNull()
    }

    override suspend fun isUserSessionActive(): Boolean {
        return FirebaseAuth.getInstance().currentUser != null
    }

    companion object {
        const val USER_DB_COLLECTION_NAME = "users"
        const val FIREBASE_INVALID_LOGIN_CREDENTIALS = "INVALID_LOGIN_CREDENTIALS"
        const val FIREBASE_EMAIL_BADLY_FORMATTED = "The email address is badly formatted"
    }
}