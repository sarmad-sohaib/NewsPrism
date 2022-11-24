package com.sarmad.newsprism.news.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sarmad.newsprism.data.entities.Article
import com.sarmad.newsprism.databinding.FragmentNewsBinding
import com.sarmad.newsprism.news.ui.adapters.ArticleClickListener
import com.sarmad.newsprism.news.ui.adapters.NewsListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.newsFlow.collect { uiState ->

                    if (uiState.isLoading) {
                        newsListLoadingState()
                    }

                    if (!uiState.isLoading) newsListSuccessState(uiState.news)

                    uiState.userMessage?.let {
                        newsListErrorState()
                        Snackbar.make(requireView(), uiState.userMessage, Snackbar.LENGTH_LONG)
                            .show()

                        mViewModel.userMessageShown()
                    }
                }
            }
        }

        mBinding.swipeRefreshLayout.setOnRefreshListener {
            mNewsListAdapter.refresh()
        }
        // Inflate the layout for this fragment
        return mBinding.root
    }

    private fun navigateToArticleWebView(article: Article) {
        val action = NewsFragmentDirections.actionNewsFragmentToArticleFragment(article = article)
        findNavController().navigate(action)
    }

    private fun newsListSuccessState(news: PagingData<Article>) {
        mBinding.apply {
            recyclerViewArticlesList.isVisible = true
            progressBarLoadingNews.isVisible = false
            textViewErrorLoadingNews.isVisible = false

            mNewsListAdapter.submitData(lifecycle, news)
            swipeRefreshLayout.isRefreshing = false
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
        mBinding.apply {
            progressBarLoadingNews.isVisible = false
            textViewErrorLoadingNews.isVisible = false
            recyclerViewArticlesList.isVisible = false
        }
    }

    override fun onArticleClick(article: Article) {
        navigateToArticleWebView(article = article)
    }
}