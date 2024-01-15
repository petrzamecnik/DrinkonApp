package com.example.drinkonapp.ui.search

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.drinkonapp.Drink
import com.example.drinkonapp.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DrinksAdapter(private val context: Context, private val drinksList: List<Drink>) :
    RecyclerView.Adapter<DrinksAdapter.DrinkViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private var itemClickListener: OnItemClickListener? = null


    class DrinkViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvDrinkName)
        val imgThumbnail: ImageView = view.findViewById(R.id.imgThumbnail)
        val btnFavorite: ImageButton = view.findViewById(R.id.btnFavorite)
//        val tvInstructions: TextView = view.findViewById(R.id.tvDrinkInstructions)

        var itemClickListener: OnItemClickListener? = null


        init {
            view.setOnClickListener {
                itemClickListener?.onItemClick(adapterPosition)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_drink, parent, false)
        val viewHolder = DrinkViewHolder(view)
        viewHolder.itemClickListener = itemClickListener
        return viewHolder
    }

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        val drink = drinksList[position]
        holder.tvName.text = drink.name
        Glide.with(holder.itemView.context).load(drink.thumbnail)
            .placeholder(R.drawable.baseline_image_64).into(holder.imgThumbnail)

        holder.btnFavorite.setImageResource(if (drink.isFavorite) R.drawable.baseline_favorite_32 else R.drawable.baseline_favorite_border_32)
        holder.btnFavorite.setOnClickListener {
            toggleFavorite(drink, holder)
        }
    }
    private fun toggleFavorite(drink: Drink, holder: DrinkViewHolder) {
        drink.isFavorite = !drink.isFavorite
        saveFavoriteState(drink.id, drink.isFavorite)
        holder.btnFavorite.setImageResource(if (drink.isFavorite) R.drawable.baseline_favorite_32 else R.drawable.baseline_favorite_border_32)

        val allDrinks = loadDrinksFromSharedPreferences(context)
        val updatedDrinks = allDrinks.map { if (it.id == drink.id) drink else it }

        saveDrinksToSharedPreferences(context, updatedDrinks)
    }


    fun saveFavoriteState(drinkId: String, isFavorite: Boolean) {
        val sharedPrefs = context.getSharedPreferences("Favorites", Context.MODE_PRIVATE)
        with(sharedPrefs.edit()) {
            putBoolean(drinkId, isFavorite)
            apply()
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

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }



    fun loadFavorites(drinkId: String): Boolean {
        val sharedPrefs = context.getSharedPreferences("Favorites", Context.MODE_PRIVATE)
        return sharedPrefs.getBoolean(drinkId, false)
    }


    override fun getItemCount() = drinksList.size
}
