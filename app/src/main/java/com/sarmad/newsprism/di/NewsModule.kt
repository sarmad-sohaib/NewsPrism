package com.sarmad.newsprism.di

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.sarmad.newsprism.article.ui.ArticleFragment
import com.sarmad.newsprism.data.localdatasource.ArticleDao
import com.sarmad.newsprism.data.localdatasource.ArticleDatabase
import com.sarmad.newsprism.data.repository.NewsRepository
import com.sarmad.newsprism.data.repository.NewsRepositoryImpl
import com.sarmad.newsprism.utils.Constants.Companion.ARTICLE_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class NewsModule {

    @Provides
    fun providesArticleDatabase(@ApplicationContext appContext: Context):
            ArticleDatabase = Room.databaseBuilder(
        appContext,
        ArticleDatabase::class.java,
        ARTICLE_DATABASE
    ).build()

    @Provides
    fun providesArticlesDao(articleDatabase: ArticleDatabase): ArticleDao =
        articleDatabase.articleDao()

    @Provides
    fun providesNewsRepository(newsRepositoryImpl: NewsRepositoryImpl ) :
            NewsRepository = newsRepositoryImpl

    @Provides
    fun providesArticleFragment(fragment: ArticleFragment) =
        fragment as Fragment
}