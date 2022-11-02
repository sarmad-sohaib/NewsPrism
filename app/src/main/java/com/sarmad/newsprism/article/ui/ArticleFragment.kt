package com.sarmad.newsprism.article.ui

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sarmad.newsprism.R
import com.sarmad.newsprism.databinding.FragmentArticleBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

const val TAG = "ArticleFragment"

@AndroidEntryPoint
class ArticleFragment @Inject constructor() : Fragment() {

    private lateinit var mBinding: FragmentArticleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mBinding = FragmentArticleBinding.inflate(inflater)

        val args: ArticleFragmentArgs by navArgs()

        val article = args.article

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (canGoBack()) goBack()
            else findNavController().popBackStack()
        }

            mBinding.webViewArticle.webViewClient = WebViewClient()
        if (article != null) {
            article.url?.let { mBinding.webViewArticle.loadUrl(it) }
        }

        // Inflate the layout for this fragment
        return mBinding.root
    }

    private fun canGoBack(): Boolean {
        return mBinding.webViewArticle.canGoBack()
    }

    private fun goBack() {
        mBinding.webViewArticle.goBack()
    }
}