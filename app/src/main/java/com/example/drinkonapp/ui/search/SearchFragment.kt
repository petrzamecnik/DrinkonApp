package com.example.drinkonapp.ui.search

import android.app.DownloadManager.Query
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.drinkonapp.SharedPreferencesHelper
import com.example.drinkonapp.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setUpSearchInput()

        return root
    }

    private fun setUpSearchInput() {
        binding.searchInputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch(binding.searchInputEditText.text.toString())
                true
            } else false
        }
    }

    private fun performSearch(query: String) {
        context?.let {
            SharedPreferencesHelper(it).saveSearchQuery(query)
            hideKeyboard()


        }

        Log.v("QUERY", "Query: $query")

    }

    private fun Fragment.hideKeyboard() {
        val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        val currentFocusedView = activity?.currentFocus
        if (currentFocusedView != null && inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocusedView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
