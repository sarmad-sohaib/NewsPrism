package com.sarmad.newsprism

import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.sarmad.newsprism.article.ui.ArticleFragment
import com.sarmad.newsprism.databinding.ActivityNewsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityNewsBinding

    @Inject
    lateinit var articleFragment: ArticleFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(mBinding.fragContainerView.id) as NavHostFragment
        val navController = navHostFragment.navController

        mBinding.bottomNavBar
            .setupWithNavController(navController)
    }
}