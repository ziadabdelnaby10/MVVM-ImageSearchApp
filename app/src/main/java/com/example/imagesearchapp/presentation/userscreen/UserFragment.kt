package com.example.imagesearchapp.presentation.userscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.imagesearchapp.databinding.FragmentUserBinding
import com.example.imagesearchapp.presentation.MainActivity
import com.example.imagesearchapp.presentation.searchscreen.adapter.ImageLoadStateAdapter
import com.example.imagesearchapp.presentation.userscreen.adapter.UserImagesPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserFragment : Fragment() {

    private val viewModel by viewModels<UserViewModel>()
    private lateinit var binding: FragmentUserBinding
    private lateinit var adapter: UserImagesPagingAdapter
    private val args: UserFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        init()
        initObservers()
        return binding.root
    }

    private fun init() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        viewModel.userName.postValue(args.userName)
        adapter = UserImagesPagingAdapter() { _, image ->
            findNavController().navigate(
                UserFragmentDirections.actionUserFragmentToPhotoFragment(
                    image.id
                )
            )
        }
        binding.rvUserPhotos.adapter = adapter.withLoadStateHeaderAndFooter(
            header = ImageLoadStateAdapter { adapter.retry() },
            footer = ImageLoadStateAdapter { adapter.retry() }
        )
        binding.rvUserPhotos.setHasFixedSize(true)
        binding.rvUserPhotos.itemAnimator = null
        val layout = StaggeredGridLayoutManager(2 , LinearLayout.VERTICAL)
        layout.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        binding.rvUserPhotos.layoutManager = layout
    }

    private fun initObservers() {
        binding.buttonRetry.setOnClickListener { adapter.retry() }

        adapter.addLoadStateListener { loadState ->
//            binding.progressShimmer.isVisible = loadState.source.refresh is LoadState.Loading
            binding.rvUserPhotos.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
            binding.textViewError.isVisible = loadState.source.refresh is LoadState.Error

            if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && adapter.itemCount < 1) {
                binding.rvUserPhotos.isVisible = false
                binding.textViewEmpty.isVisible = true
            } else {
                binding.textViewEmpty.isVisible = false
            }
        }

        lifecycleScope.launch {
            viewModel.photos.observe(viewLifecycleOwner) {
//                binding.progressShimmer.visibility = View.GONE
                adapter.submitData(viewLifecycleOwner.lifecycle, it)
            }
            viewModel.userName.observe(viewLifecycleOwner) {
                if (it != null)
                    viewModel.getUserData(it)
            }
            viewModel.user.observe(viewLifecycleOwner){
                if(it!=null)
                    (requireActivity() as MainActivity).supportActionBar?.title = it.name
            }
        }
    }

}