package com.example.drinkonapp.ui.drink_detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.drinkonapp.R

class DrinkDetailFragment : Fragment() {

    companion object {
        fun newInstance() = DrinkDetailFragment()
    }

    private lateinit var viewModel: DrinkDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_drink_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DrinkDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}