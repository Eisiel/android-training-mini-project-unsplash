package com.example.miniproject.ui.home

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
//import com.example.miniproject.Injection
import com.example.miniproject.databinding.UnsplashSearchFragmentBinding
import com.example.miniproject.model.UnsplashPhoto
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class UnsplashSearchFragment : Fragment() {

    private val viewModel: UnsplashSearchViewModel by viewModels()
    private lateinit var binding: UnsplashSearchFragmentBinding

    @Inject
    lateinit var photoAdapter: PhotoAdapter
//    private lateinit var viewModel: UnsplashSearchViewModel

//    @Inject
//    internal lateinit var viewModelFactory: UnsplashSearchViewModelFactory
//
//    private val viewModel: UnsplashSearchViewModel by viewModels {
//        GenericSavedStateViewModelFactory(viewModelFactory, this)
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UnsplashSearchFragmentBinding.inflate(inflater, container, false)
//        viewModel = ViewModelProvider(requireActivity()
//            , Injection.provideViewModelFactory(context = requireActivity() ,owner = this))
//            .get(UnsplashSearchViewModel::class.java)
        binding.bindState(
            uiState = viewModel.state,
            pagingData = viewModel.pagingDataFlow,
            uiActions = viewModel.accept
        )
        return binding.root
    }

    private fun UnsplashSearchFragmentBinding.bindState(
        uiState: StateFlow<UiState>,
        pagingData: Flow<PagingData<UnsplashPhoto>>,
        uiActions: (UiAction) -> Unit
    ) {
//        val photoAdapter = PhotoAdapter()
        list.adapter = photoAdapter
        list.setHasFixedSize(true)
        list.itemAnimator = null
        list.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        bindSearch(
            uiState = uiState,
            onQueryChanged = uiActions
        )

        bindList(
            photoAdapter = photoAdapter,
            uiState = uiState,
            pagingData = pagingData,
            onScrollChanged = uiActions
        )
    }

    private fun UnsplashSearchFragmentBinding.bindSearch(
        uiState: StateFlow<UiState>,
        onQueryChanged: (UiAction.Search) -> Unit
    ) {
        searchRepo.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateRepoListFromInput(onQueryChanged)
                true
            } else {
                false
            }
        }
        searchRepo.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateRepoListFromInput(onQueryChanged)
                true
            } else {
                false
            }
        }

        lifecycleScope.launch {
            uiState
                .map { it.query }
                .distinctUntilChanged()
                .collect(searchRepo::setText)
        }
    }

    private fun UnsplashSearchFragmentBinding.updateRepoListFromInput(onQueryChanged: (UiAction.Search) -> Unit) {
        searchRepo.text.trim().let {
            if (it.isNotEmpty()) {
                list.scrollToPosition(0)
                onQueryChanged(UiAction.Search(query = it.toString()))
            }
        }
    }

    private fun UnsplashSearchFragmentBinding.bindList(
        photoAdapter: PhotoAdapter,
        uiState: StateFlow<UiState>,
        pagingData: Flow<PagingData<UnsplashPhoto>>,
        onScrollChanged: (UiAction.Scroll) -> Unit
    ) {
        list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy != 0) onScrollChanged(UiAction.Scroll(currentQuery = uiState.value.query))
            }
        })

        val notLoading = photoAdapter.loadStateFlow
            // Only emit when REFRESH LoadState for the paging source changes.
            .distinctUntilChangedBy { it.source.refresh }
            // Only react to cases where REFRESH completes i.e., NotLoading.
            .map { it.source.refresh is LoadState.NotLoading }

        val hasNotScrolledForCurrentSearch = uiState
            .map { it.hasNotScrolledForCurrentSearch }
            .distinctUntilChanged()

        val shouldScrollToTop = combine(
            notLoading,
            hasNotScrolledForCurrentSearch,
            Boolean::and
        )
            .distinctUntilChanged()

        lifecycleScope.launch {
            pagingData.collectLatest(photoAdapter::submitData)
        }

        lifecycleScope.launch {
            shouldScrollToTop.collect { shouldScroll ->
                if (shouldScroll) list.scrollToPosition(0)
            }
        }

    }

}