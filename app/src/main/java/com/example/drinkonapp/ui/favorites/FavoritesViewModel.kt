package com.example.drinkonapp.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.drinkonapp.Drink

class FavoritesViewModel : ViewModel() {

    private val _favorites = MutableLiveData<List<Drink>>()
    val favorites: LiveData<List<Drink>> = _favorites

    fun loadFavorites() {

    }
}