package com.example.drinkonapp.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.drinkonapp.R
import com.example.drinkonapp.SharedPreferencesHelper
import com.example.drinkonapp.databinding.FragmentHistoryBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private lateinit var clearHistoryBtn: FloatingActionButton
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sharedPreferencesHelper = SharedPreferencesHelper(requireContext())
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        context?.let {
            val history = SharedPreferencesHelper(it).getSearchHistory()
            val formattedHistory = history.replace(",", "\n")
            binding.textHistory.text = formattedHistory
        }

        clearHistoryBtn = root.findViewById(R.id.clear_history)
        clearHistoryBtn.setOnClickListener {
            sharedPreferencesHelper.clearSearchHistory()
            updateHistory()
        }

        return root
    }


    override fun onResume() {
        super.onResume()
        updateHistory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun updateHistory() {
        context?.let {
            val historyList = SharedPreferencesHelper(it).getReversedSearchHistory()
            val formattedHistory = historyList.joinToString("\n")
            binding.textHistory.text = formattedHistory
        }
    }
}