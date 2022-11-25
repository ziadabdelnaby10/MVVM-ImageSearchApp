package com.example.imagesearchapp.presentation.homescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.imagesearchapp.databinding.FragmentHomeBinding
import com.example.imagesearchapp.presentation.searchscreen.adapter.ImageLoadStateAdapter
import com.example.imagesearchapp.presentation.searchscreen.adapter.SearchPagingAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: SearchPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        init()
        initObservers()
        return binding.root
    }

    private fun init() {
        binding.lifecycleOwner = viewLifecycleOwner
        adapter = SearchPagingAdapter({ image ->
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToPhotoFragment(
                    image.id
                )
            )
        }, { image ->
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToUserFragment(
                    image.user.username
                )
            )
        })
        binding.rvImage.adapter = adapter.withLoadStateHeaderAndFooter(
            header = ImageLoadStateAdapter { adapter.retry() },
            footer = ImageLoadStateAdapter { adapter.retry() }
        )
        binding.rvImage.setHasFixedSize(true)
    }

    private fun initObservers() {
        lifecycleScope.launchWhenCreated {
            viewModel.photos.collect {
                adapter.submitData(pagingData = it)
            }
        }

        binding.buttonRetry.setOnClickListener { adapter.retry() }

        adapter.addLoadStateListener { loadState ->
            binding.progress.isVisible = loadState.source.refresh is LoadState.Loading
            binding.rvImage.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
            binding.textViewError.isVisible = loadState.source.refresh is LoadState.Error

            if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && adapter.itemCount < 1) {
                binding.rvImage.isVisible = false
                binding.textViewEmpty.isVisible = true
            } else {
                binding.textViewEmpty.isVisible = false
            }
        }
    }


}