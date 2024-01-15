package com.example.drinkonapp.ui.favorites

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drinkonapp.Drink
import com.example.drinkonapp.databinding.FragmentFavoritesBinding
import com.example.drinkonapp.ui.search.DrinksAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var allDrinks: List<Drink>
    private lateinit var drinksAdapter: DrinksAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val favoritesViewModel =
            ViewModelProvider(this).get(FavoritesViewModel::class.java)

        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val allDrinks = loadDrinksFromSharedPreferences(requireContext())
        for (drink in allDrinks) {
            Log.d("Drink", "Name: ${drink.name}, ID: ${drink.id}, Thumbnail: ${drink.thumbnail}, IsFavorite: ${drink.isFavorite}")
        }



        return root
    }

    fun loadDrinksFromSharedPreferences(context: Context): List<Drink> {
        val sharedPrefs = context.getSharedPreferences("AllDrinks", Context.MODE_PRIVATE)
        val drinksJson = sharedPrefs.getString("allDrinks", null)
        return if (drinksJson != null) {
            val gson = Gson()
            val type = object : TypeToken<List<Drink>>() {}.type
            gson.fromJson(drinksJson, type) ?: emptyList()
        } else {
            emptyList()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}