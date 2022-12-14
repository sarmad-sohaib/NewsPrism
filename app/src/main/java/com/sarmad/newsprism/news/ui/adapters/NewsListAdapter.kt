package com.sarmad.newsprism.news.ui.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.sarmad.newsprism.data.entities.Article
import com.sarmad.newsprism.databinding.ItemArticlePreviewBinding


class NewsListAdapter (
    private val articleClickListener: ArticleClickListener
        ) :
    PagingDataAdapter<Article, NewsListAdapter.ArticleViewHolder>(ArticleDiffCallBack()) {

    inner class ArticleViewHolder(private val binding: ItemArticlePreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article, articleClickListener: ArticleClickListener) {

            val requestBuilder: RequestBuilder<Drawable> =
                Glide.with(binding.root.context)
                    .asDrawable().sizeMultiplier(0.05f)

            binding.apply {
                textViewArticleSource.text = (article.source?.name ?: imageViewArticleImage.layout(0, 0, 0, 0)) as CharSequence?
                Glide.with(binding.root.context).load(article.urlToImage)
                    .thumbnail(requestBuilder)
                    .apply(RequestOptions().centerCrop().override(1200, 600))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageViewArticleImage)
                textViewArticleTitle.text = article.title
                textViewArticlePublishedTime.text = article.publishedAt
            }

            binding.root.setOnClickListener {
                articleClickListener.onArticleClick(article)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            ItemArticlePreviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        if (article != null) {
            holder.bind(article, articleClickListener)
        }
    }

    private class ArticleDiffCallBack : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Article, newItem: Article) = oldItem == newItem

    }
}

interface ArticleClickListener {
    fun onArticleClick(article: Article)
}