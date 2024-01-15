package com.example.drinkonapp.ui.search

import com.example.drinkonapp.NetworkUtils
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drinkonapp.SharedPreferencesHelper
import com.example.drinkonapp.databinding.FragmentSearchBinding
import kotlinx.coroutines.*
import com.example.drinkonapp.JsonParser
import com.example.drinkonapp.Drink
import com.google.gson.Gson


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var drinksAdapter: DrinksAdapter
    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setUpSearchInput()

        drinksAdapter = context?.let { DrinksAdapter(it, emptyList()) }!!
        binding.drinksRecyclerView.adapter = drinksAdapter
        binding.drinksRecyclerView.layoutManager = LinearLayoutManager(context)

        return root
    }

    private fun updateUIWithDrinks(drinks: List<Drink>) {
        drinksAdapter = context?.let { DrinksAdapter(it, drinks) }!!
        binding.drinksRecyclerView.adapter = drinksAdapter
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

        CoroutineScope(Dispatchers.IO).launch {
            val jsonResponse = NetworkUtils.getJson("https://www.thecocktaildb.com/api/json/v1/1/search.php?s=$query")
            val drinks = JsonParser.parseDrinksJson(jsonResponse.orEmpty())

            withContext(Dispatchers.Main) {
                drinks?.let {
                    updateUIWithDrinks(it)
                    saveDrinksToSharedPreferences(requireContext(), it) // Save the drinks data
                }
            }
        }
    }

    private fun saveDrinksToSharedPreferences(context: Context, drinks: List<Drink>) {
        val sharedPrefs = context.getSharedPreferences("AllDrinks", Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        val gson = Gson()
        val drinksJson = gson.toJson(drinks) // Serialize the list of drinks to JSON
        editor.putString("allDrinks", drinksJson)
        editor.apply()
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
