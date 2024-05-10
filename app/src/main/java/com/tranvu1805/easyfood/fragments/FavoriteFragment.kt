package com.tranvu1805.easyfood.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.tranvu1805.easyfood.activities.MainActivity
import com.tranvu1805.easyfood.adapters.MealAdapter
import com.tranvu1805.easyfood.databinding.FragmentFavoriteBinding
import com.tranvu1805.easyfood.viewModel.HomeViewModel


class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var favoriteAdapter: MealAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).homeViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        observeFavorite()

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val deletedMeal = favoriteAdapter.differ.currentList[position]
                viewModel.deleteMeal(deletedMeal)
                Snackbar.make(requireView(), "Meal Deleted", Snackbar.LENGTH_LONG).setAction(
                    "Undo"
                ) {
                    viewModel.insertMeal(deletedMeal)
                }.show()
                favoriteAdapter.differ.currentList[position]

            }

        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavoriteMeal)
    }

    private fun prepareRecyclerView() {
        favoriteAdapter = MealAdapter()
        binding.rvFavoriteMeal.apply {
            layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
            adapter = favoriteAdapter
        }

    }

    private fun observeFavorite() {
        viewModel.favoriteMeal.observe(viewLifecycleOwner) {
            favoriteAdapter.differ.submitList(it)

        }
    }

}