package com.example.miniproject.ui.home

import android.app.Activity
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.miniproject.R
//import com.example.miniproject.Injection
import com.example.miniproject.databinding.UnsplashSearchFragmentBinding
import com.example.miniproject.model.UnsplashPhoto
import com.example.miniproject.service.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class UnsplashSearchFragment : Fragment(),  SessionManager.LogoutListener {

    private val viewModel: UnsplashSearchViewModel by viewModels()
    private lateinit var binding: UnsplashSearchFragmentBinding

    @Inject
    lateinit var photoAdapter: PhotoAdapter

    private lateinit var appBarState: AppBarState

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UnsplashSearchFragmentBinding.inflate(inflater, container, false)
        binding.bindState(
            uiState = viewModel.state,
            pagingData = viewModel.pagingDataFlow,
            uiActions = viewModel.accept
        )
        SessionManager.registerSessionListener(this)

        appBarState = AppBarState.IDLE

        binding.unsplashPickerClearImageView.setOnClickListener {
            appBarState = AppBarState.IDLE
            updateUiFromState()
        }
        binding.unsplashPickerSearchImageView.setOnClickListener {
            appBarState = AppBarState.SEARCHING
            updateUiFromState()
        }

        binding.unsplashProfile.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_unsplashSearchFragment_to_logoutFragment)
        }

        return binding.root
    }

    private fun UnsplashSearchFragmentBinding.bindState(
        uiState: StateFlow<UiState>,
        pagingData: Flow<PagingData<UnsplashPhoto>>,
        uiActions: (UiAction) -> Unit
    ) {
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
        unsplashPickerEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_DONE  ) {
                updateRepoListFromInput(onQueryChanged)
                activity?.hideSoftKeyboard()
                true
            } else {
                false
            }
        }
        unsplashPickerEditText.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateRepoListFromInput(onQueryChanged)
                activity?.hideSoftKeyboard()
                true
            } else {
                false
            }
        }

        lifecycleScope.launch {
            uiState
                .map { it.query }
                .distinctUntilChanged()
                .collect(unsplashPickerEditText::setText)
        }
    }

    private fun UnsplashSearchFragmentBinding.updateRepoListFromInput(onQueryChanged: (UiAction.Search) -> Unit) {
        unsplashPickerEditText.text.trim().let {
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

        lifecycleScope.launch {
            photoAdapter.loadStateFlow.collect { loadState ->
                val isListEmpty = loadState.refresh is LoadState.NotLoading && photoAdapter.itemCount == 0
                // show empty list
                emptyList.isVisible = isListEmpty
                // Only show the list if refresh succeeds, either from the the local db or the remote.
                list.isVisible =  loadState.source.refresh is LoadState.NotLoading || loadState.mediator?.refresh is LoadState.NotLoading
                // Show loading spinner during initial load or refresh.
                progressBar.isVisible = loadState.mediator?.refresh is LoadState.Loading
                // Show the retry state if initial load or refresh fails.
                retryButton.isVisible = loadState.mediator?.refresh is LoadState.Error && photoAdapter.itemCount == 0
            }
        }

    }

    private fun updateUiFromState() {
        when (appBarState) {
            AppBarState.IDLE -> {
                // back and search buttons visible
                binding.unsplashPickerSearchImageView.visibility = View.VISIBLE

                // edit text cleared and gone
                if (!TextUtils.isEmpty(binding.unsplashPickerEditText.text)) {
                    binding.unsplashPickerEditText.setText("")
                }
                binding.unsplashPickerEditText.visibility = View.GONE

                // action bar with unsplash
                binding.unsplashPickerTitleTextView.text ="Unsplash"

                // right clear button on top of edit text gone
                binding.unsplashPickerClearImageView.visibility = View.GONE
                binding.unsplashProfile.visibility = View.VISIBLE

            }
            AppBarState.SEARCHING -> {
                // search buttons gone
                binding.unsplashPickerSearchImageView.visibility = View.GONE
                binding.unsplashProfile.visibility = View.GONE

                // edit text visible and focused
                binding.unsplashPickerEditText.visibility = View.VISIBLE

                // keyboard up
                binding.unsplashPickerEditText.requestFocus()

                // right clear button on top of edit text visible
                binding.unsplashPickerClearImageView.visibility = View.VISIBLE

            }
        }
    }

    override fun doLogout() {
        activity?.runOnUiThread {
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_unsplashSearchFragment_to_loginFragment)
        }
    }
}

enum class AppBarState {
    IDLE, SEARCHING
}

fun Activity.hideSoftKeyboard(){
    (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).apply {
        hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}
