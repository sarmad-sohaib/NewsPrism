package com.sarmad.newsprism.searchnews.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarmad.newsprism.data.entities.Article
import com.sarmad.newsprism.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SearchNewsViewModel"

sealed class SearchNewsFragmentUiState {

    data class Success(val searchedNewsList: List<Article>) : SearchNewsFragmentUiState()
    object Loading: SearchNewsFragmentUiState()
}
@HiltViewModel
class SearchNewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _uiStates = MutableStateFlow<SearchNewsFragmentUiState>(SearchNewsFragmentUiState.Loading)
    val uiState = _uiStates.asStateFlow()

    private fun getSearchedNews(searchQuery: String, pageNumber: Int) = viewModelScope.launch {
        newsRepository.getSearchedNewsStream(searchQuery, pageNumber).collect { searchNewsResponse ->
            _uiStates.value = SearchNewsFragmentUiState.Success(searchNewsResponse.articles)
            Log.i(TAG, "getSearchedNews: ${searchNewsResponse.articles}")
        }
    }

    fun searchNews(searchQuery: String?) = viewModelScope.launch {
        delay(1000L)
        searchQuery?.let {
            getSearchedNews(it, 1)
            Log.i(TAG, "searchNews: $searchQuery")
        }
    }


}