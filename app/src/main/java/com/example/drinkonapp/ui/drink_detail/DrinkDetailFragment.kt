package com.example.drinkonapp.ui.drink_detail

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.drinkonapp.Drink
import com.example.drinkonapp.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DrinkDetailFragment : Fragment() {

    companion object {
        fun newInstance() = DrinkDetailFragment()
    }

    private lateinit var viewModel: DrinkDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_drink_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val drinkId = (arguments?.getString("drinkId", "0") ?: "0")
        val drink = context?.let { loadDrinkFromSharedPreferences(it, drinkId) }
        Log.d("Retrieved Drink: ", "Drink: $drink")

        drink?.let {
            val imgDrinkImage = view.findViewById<ImageView>(R.id.imgDrinkImage)
            val tvDrinkName = view.findViewById<TextView>(R.id.tvDrinkName)
            val tvInstructions = view.findViewById<TextView>(R.id.tvInstructions)
            val tvIngredients = view.findViewById<TextView>(R.id.tvIngredients)

            tvDrinkName.text = it.name
            tvInstructions.text = it.instructions
            tvIngredients.text = it.ingredients

            Glide.with(this)
                .load(it.thumbnail)
                .placeholder(R.drawable.baseline_image_64)
                .into(imgDrinkImage)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigate(R.id.navigation_search)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    fun loadDrinkFromSharedPreferences(context: Context, drinkId: String): Drink? {
        val sharedPrefs = context.getSharedPreferences("AllDrinks", Context.MODE_PRIVATE)
        val drinksJson = sharedPrefs.getString("allDrinks", null)

        if (drinksJson != null) {
            val gson = Gson()
            val type = object : TypeToken<List<Drink>>() {}.type
            val drinksList = gson.fromJson<List<Drink>>(drinksJson, type) ?: emptyList()

            return drinksList.find { it.id == drinkId }
        }

        return null
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DrinkDetailViewModel::class.java)
    }

}