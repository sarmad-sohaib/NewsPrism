package com.sarmad.newsprism.di

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.sarmad.newsprism.article.ui.ArticleFragment
import com.sarmad.newsprism.data.remotedatasource.api.NewsApi
import com.sarmad.newsprism.data.localdatasource.ArticleDao
import com.sarmad.newsprism.data.localdatasource.ArticleDatabase
import com.sarmad.newsprism.data.localdatasource.RemoteKeysDao
import com.sarmad.newsprism.data.repository.NewsRepository
import com.sarmad.newsprism.data.repository.NewsRepositoryImpl
import com.sarmad.newsprism.utils.Constants.Companion.ARTICLE_DATABASE
import com.sarmad.newsprism.utils.Constants.Companion.BASE_URL
import com.sarmad.newsprism.utils.connectivityobservers.ConnectivityObserver
import com.sarmad.newsprism.utils.connectivityobservers.InternetConnectivityObserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NewsModule {

    @Provides
    @Singleton
    fun providesArticleDatabase(@ApplicationContext appContext: Context):
            ArticleDatabase = Room.databaseBuilder(
        appContext,
        ArticleDatabase::class.java,
        ARTICLE_DATABASE
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun providesArticlesDao(articleDatabase: ArticleDatabase): ArticleDao =
        articleDatabase.articleDao()

    @Provides
    fun providesArticleRemoteKeysDao(articleDatabase: ArticleDatabase): RemoteKeysDao =
        articleDatabase.articleRemoteKeyDao()

    @Provides
    @Singleton
    fun providesNewsRepository(newsRepositoryImpl: NewsRepositoryImpl):
            NewsRepository = newsRepositoryImpl

    @Provides
    @Singleton
    fun providesArticleFragment(fragment: ArticleFragment) =
        fragment as Fragment

    @Provides
    @Singleton
    fun providesOkHttpClient(
        @ApplicationContext context: Context
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(
        @ApplicationContext context: Context
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(providesOkHttpClient(context))
            .build()

    @Provides
    @Singleton
    fun providesApi(retrofit: Retrofit): NewsApi = retrofit.create(NewsApi::class.java)

    @Provides
    fun providesInternetConnectivityObserver(internetConnectivityObserver: InternetConnectivityObserver)
            : ConnectivityObserver = internetConnectivityObserver
}