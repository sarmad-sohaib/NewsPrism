package com.sarmad.newsprism.news.ui.adapters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarmad.newsprism.data.entities.Article
import com.sarmad.newsprism.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class NewsListUiState {
    data class Success(val newsList: List<Article>): NewsListUiState()
    data class Error(val error: String): NewsListUiState()
    object Loading: NewsListUiState()
}

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    init {
        getAllNews("us")
    }

    private val _newsListUiState = MutableStateFlow<NewsListUiState>(NewsListUiState.Loading)
    val newsListUiState = _newsListUiState

    private fun getAllNews(countryCode: String) = viewModelScope.launch {
        newsRepository.getBreakingNewsStream(countryCode, 1).collect { newsResponse ->
            if (newsResponse.articles.isEmpty()) {
                _newsListUiState.value = NewsListUiState.Error("Error")
            }
            else _newsListUiState.value = NewsListUiState.Success(newsResponse.articles)
        }
    }

}