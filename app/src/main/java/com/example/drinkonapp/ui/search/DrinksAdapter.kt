package com.example.drinkonapp.ui.search

import android.content.Context
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

class DrinksAdapter(private val context: Context, private val drinksList: List<Drink>) : RecyclerView.Adapter<DrinksAdapter.DrinkViewHolder>() {

    class DrinkViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvDrinkName)
        val imgThumbnail: ImageView = view.findViewById(R.id.imgThumbnail)
        val btnFavorite: ImageButton = view.findViewById(R.id.btnFavorite)
//        val tvInstructions: TextView = view.findViewById(R.id.tvDrinkInstructions)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_drink, parent, false)
        return DrinkViewHolder(view)
    }

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        val drink = drinksList[position]
        holder.tvName.text = drink.name
//        holder.tvInstructions.text = drink.instructions

        Glide.with(holder.itemView.context)
            .load(drink.thumbnail)
            .placeholder(R.drawable.baseline_image_64)
            .into(holder.imgThumbnail)


        holder.btnFavorite.setOnClickListener {
            toggleFavorite(drink, holder)
//            holder.btnFavorite.setImageResource(if (drink.isFavorite) R.drawable.baseline_favorite_32 else R.drawable.baseline_favorite_32)
        }


    }

    fun toggleFavorite(drink: Drink, holder: DrinkViewHolder) {
        drink.isFavorite = !drink.isFavorite
        saveFavoriteState(drink.id, drink.isFavorite)
        holder.btnFavorite.setImageResource(if (drink.isFavorite) R.drawable.baseline_favorite_32 else R.drawable.baseline_favorite_border_32)
    }
    fun saveFavoriteState(drinkId: String, isFavorite: Boolean) {
        val sharedPrefs = context.getSharedPreferences("Favorites", Context.MODE_PRIVATE)
        with(sharedPrefs.edit()) {
            putBoolean(drinkId, isFavorite)
            apply()
        }
    }


    fun loadFavorites(drinkId: String): Boolean {
        val sharedPrefs = context.getSharedPreferences("Favorites", Context.MODE_PRIVATE)
        return sharedPrefs.getBoolean(drinkId, false)
    }


    override fun getItemCount() = drinksList.size
}
