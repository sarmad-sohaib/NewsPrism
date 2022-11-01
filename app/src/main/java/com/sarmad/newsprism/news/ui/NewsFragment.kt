package com.sarmad.newsprism.news.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sarmad.newsprism.data.entities.Article
import com.sarmad.newsprism.data.entities.NewsResponse
import com.sarmad.newsprism.data.entities.Source
import com.sarmad.newsprism.databinding.FragmentNewsBinding
import com.sarmad.newsprism.news.ui.adapters.ArticleClickListener
import com.sarmad.newsprism.news.ui.adapters.NewsListAdapter
import com.sarmad.newsprism.news.ui.adapters.NewsListUiState
import com.sarmad.newsprism.news.ui.adapters.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

//https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885_1280.jpg

const val TAG = "NewsFragment"

@AndroidEntryPoint
class NewsFragment : Fragment(), ArticleClickListener {

    private lateinit var mBinding: FragmentNewsBinding
    private val mNewsListAdapter = NewsListAdapter(this)
    private val mViewModel: NewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mBinding = FragmentNewsBinding.inflate(inflater)

        mBinding.apply {
            recyclerViewArticlesList.adapter = mNewsListAdapter
            recyclerViewArticlesList.layoutManager = LinearLayoutManager(context)
        }

        //collecting ui states from viewModel
        lifecycleScope.launch {
            lifecycleScope.launchWhenStarted {
                mViewModel.newsListUiState.collect { state ->
                    when(state) {
                        is NewsListUiState.Error -> newsListErrorState()
                        NewsListUiState.Loading -> newsListLoadingState()
                        is NewsListUiState.Success -> newsListSuccessState(state.newsList)
                    }
                }
            }
        }

        // Inflate the layout for this fragment
        return mBinding.root
    }

    private fun newsListSuccessState(newsList: List<Article>) {
        mBinding.apply {
            recyclerViewArticlesList.isVisible = true
            progressBarLoadingNews.isVisible = false
            textViewErrorLoadingNews.isVisible = false

            mNewsListAdapter.submitList(newsList)
            recyclerViewArticlesList.adapter = mNewsListAdapter
        }
    }

    private fun newsListLoadingState() {
        mBinding.apply {
            progressBarLoadingNews.isVisible = true
            textViewErrorLoadingNews.isVisible = false
            recyclerViewArticlesList.isVisible = false
        }
    }

    private fun newsListErrorState() {
        TODO("Not yet implemented")
    }

    override fun onArticleClick(article: Article) {
        Log.i(TAG, "onArticleClick: ${article.source}")
    }
}