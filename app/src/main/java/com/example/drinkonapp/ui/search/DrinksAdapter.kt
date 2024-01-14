package com.example.drinkonapp.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.drinkonapp.Drink
import com.example.drinkonapp.R

class DrinksAdapter(private val drinksList: List<Drink>) : RecyclerView.Adapter<DrinksAdapter.DrinkViewHolder>() {

    class DrinkViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvDrinkName)
        val tvInstructions: TextView = view.findViewById(R.id.tvDrinkInstructions)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_drink, parent, false)
        return DrinkViewHolder(view)
    }

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        val drink = drinksList[position]
        holder.tvName.text = drink.name
        holder.tvInstructions.text = drink.instructions
    }

    override fun getItemCount() = drinksList.size
}
