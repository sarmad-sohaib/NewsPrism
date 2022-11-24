package com.sarmad.newsprism.news.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sarmad.newsprism.data.entities.Article
import com.sarmad.newsprism.data.repository.NewsRepository
import com.sarmad.newsprism.utils.Constants.Companion.US_COUNTRY_CODE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NewsItemListUiState(
    val news: PagingData<Article> = PagingData.empty(),
    val isLoading: Boolean = false,
    val userMessage: String? = null
)

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    init {
        getNewsStream(US_COUNTRY_CODE)
    }

    private val _newsFlow = MutableStateFlow(NewsItemListUiState(isLoading = true))
    val newsFlow = _newsFlow.asStateFlow()

    fun userMessageShown() {
        _newsFlow.update { currentState ->
            currentState.copy(
                userMessage = null
            )
        }
    }

    private fun getNewsStream(countryCode: String) = viewModelScope.launch {
        newsRepository.getBreakingNewsStream(countryCode).cachedIn(viewModelScope).collectLatest {
            _newsFlow.update { currentState ->
                currentState.copy(
                    isLoading = false,
                    userMessage = null,
                    news = it
                )
            }
        }
    }

}