package com.sarmad.newsprism.searchnews.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sarmad.newsprism.R
import com.sarmad.newsprism.data.entities.Article
import com.sarmad.newsprism.databinding.FragmentSearchNewsBinding
import com.sarmad.newsprism.news.ui.adapters.ArticleClickListener
import com.sarmad.newsprism.news.ui.adapters.NewsListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "SearchNewsFragment"

@AndroidEntryPoint
class SearchNewsFragment : Fragment(), ArticleClickListener {

    private lateinit var mBinding: FragmentSearchNewsBinding
    private val mSearchNewsViewModel: SearchNewsViewModel by viewModels()
    private val mAdapter = NewsListAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mBinding = FragmentSearchNewsBinding.inflate(inflater)

        val menuHost: MenuHost = requireActivity()

        mBinding.apply {
            recyclerViewSearchedArticlesList.layoutManager = LinearLayoutManager(context)
            recyclerViewSearchedArticlesList.adapter = mAdapter
        }

        //collecting ui states
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            mSearchNewsViewModel.uiState.collect { state ->
                when (state) {
                    SearchNewsFragmentUiState.Loading -> uiStateLoading()
                    is SearchNewsFragmentUiState.Success -> uiStateSuccess(state.searchedNewsList)
                }
            }
        }

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.search_news_menu, menu)

                var job: Job? = null

                val searchItem = menu.findItem(R.id.item_searchNews)
                val searchView = searchItem.actionView as SearchView

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(searchQuery: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {

                        job?.cancel()
                        job = MainScope().launch {
                            delay(1000L)

                            newText?.let {
                                mSearchNewsViewModel.searchNews(it)
                            }
                        }
                        return true
                    }


                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                TODO("Not yet implemented")
            }
        })

        // Inflate the layout for this fragment
        return mBinding.root
    }

    private fun uiStateSuccess(searchedNewsList: List<Article>) {
        mBinding.apply {
            progressBarLoadingSearchedNews.isVisible = false
            recyclerViewSearchedArticlesList.isVisible = true

            mAdapter.submitList(searchedNewsList)

            recyclerViewSearchedArticlesList.adapter = mAdapter
        }
    }

    private fun uiStateLoading() {
        mBinding.progressBarLoadingSearchedNews.isVisible = true
        mBinding.recyclerViewSearchedArticlesList.isVisible = false
    }

    override fun onArticleClick(article: Article) {
        TODO("Not yet implemented")
    }
}