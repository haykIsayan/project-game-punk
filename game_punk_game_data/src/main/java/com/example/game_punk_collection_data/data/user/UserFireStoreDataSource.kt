package com.example.game_punk_collection_data.data.user

import com.example.game_punk_collection_data.data.models.user.UserModel
import com.example.game_punk_domain.domain.entity.user.UserAuthModel
import com.example.game_punk_domain.domain.entity.user.UserEntity
import com.example.game_punk_domain.domain.interfaces.UserRepository
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.UserProfileChangeRequest
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class UserFireStoreDataSource(
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
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
                        continuation.resume(userAuthModel.createUser(id))
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

    override suspend fun getUser(userId: String): UserEntity {
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        return UserModel(
            id = firebaseUser?.uid,
            email = firebaseUser?.email ?: "",
            displayName = firebaseUser?.displayName ?: "",
            password = "",
            profileIcon = ""
        )
    }

    override suspend fun isUserSessionActive(): Boolean {
        return FirebaseAuth.getInstance().currentUser != null
    }

    companion object {
        const val FIREBASE_INVALID_LOGIN_CREDENTIALS = "INVALID_LOGIN_CREDENTIALS"
        const val FIREBASE_EMAIL_BADLY_FORMATTED = "The email address is badly formatted"
    }
}