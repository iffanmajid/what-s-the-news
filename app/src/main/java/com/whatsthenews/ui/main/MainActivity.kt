package com.whatsthenews.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import com.whatsthenews.R
import com.whatsthenews.base.DataBindingActivity
import com.whatsthenews.databinding.ActivityMainBinding
import com.whatsthenews.ui.adapter.NewsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : DataBindingActivity() {

    @VisibleForTesting val newsViewModel : NewsViewModel by viewModels()
    private val binding: ActivityMainBinding by binding(R.layout.activity_main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            lifecycleOwner = this@MainActivity
            adapter = NewsAdapter()
            vm = newsViewModel
        }
    }
}