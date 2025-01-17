package com.example.rangkul.di

import android.content.SharedPreferences
import com.example.rangkul.data.repository.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun providePostRepository(
        database: FirebaseFirestore,
        appPreferences: SharedPreferences,
        gson: Gson,
        storageReference: StorageReference
    ): PostRepository {
        return PostRepositoryImp(database, appPreferences, gson, storageReference)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        database: FirebaseFirestore,
        auth: FirebaseAuth,
        appPreferences: SharedPreferences,
        gson: Gson
    ): AuthRepository {
        return AuthRepositoryImp(database, auth, appPreferences, gson)
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(
        database: FirebaseFirestore,
    ): CategoryContentRepository {
        return CategoryContentRepositoryImp(database)
    }
}