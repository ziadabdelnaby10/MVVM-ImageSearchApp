package com.example.imagesearchapp.presentation.searchscreen

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.imagesearchapp.R
import com.example.imagesearchapp.databinding.FragmentSearchBinding
import com.example.imagesearchapp.presentation.searchscreen.adapter.ImageLoadStateAdapter
import com.example.imagesearchapp.presentation.searchscreen.adapter.SearchPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel by viewModels<SearchViewModel>()
    private lateinit var adapter: SearchPagingAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        init()
        initObserver()
        setupMenu()
        return binding.root
    }

    private fun init() {
        binding.lifecycleOwner = viewLifecycleOwner
        adapter = SearchPagingAdapter({ image ->
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToPhotoFragment(
                    image.id
                )
            )
        }, { image ->
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToUserFragment(
                    image.user.username
                )
            )
        })
        binding.rvSearch.adapter = adapter.withLoadStateHeaderAndFooter(
            header = ImageLoadStateAdapter { adapter.retry() },
            footer = ImageLoadStateAdapter { adapter.retry() }
        )
        binding.rvSearch.setHasFixedSize(true)
    }

    private fun initObserver() {
        lifecycleScope.launchWhenCreated {
            viewModel.photos.observe(viewLifecycleOwner) {
                binding.progress.visibility = View.GONE
                adapter.submitData(viewLifecycleOwner.lifecycle, it)
            }
        }

        binding.buttonRetry.setOnClickListener { adapter.retry() }

        adapter.addLoadStateListener { loadState ->
            binding.progress.isVisible = loadState.source.refresh is LoadState.Loading
            binding.rvSearch.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
            binding.textViewError.isVisible = loadState.source.refresh is LoadState.Error

            if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && adapter.itemCount < 1) {
                binding.rvSearch.isVisible = false
                binding.textViewEmpty.isVisible = true
            } else {
                binding.textViewEmpty.isVisible = false
            }
        }
    }

    private fun setupMenu() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.search_menu, menu)
                val searchItem = menu.findItem(R.id.action_search)
                val searchView = searchItem.actionView as SearchView

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(p0: String?): Boolean {
                        if (p0 != null) {
                            binding.rvSearch.scrollToPosition(0)
                            viewModel.searchPhotos(p0)
                            searchView.clearFocus()
                        }
                        return true
                    }

                    override fun onQueryTextChange(p0: String?): Boolean {
                        return true
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

}