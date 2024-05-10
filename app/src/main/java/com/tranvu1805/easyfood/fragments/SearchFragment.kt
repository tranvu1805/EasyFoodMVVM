package com.tranvu1805.easyfood.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.tranvu1805.easyfood.activities.MainActivity
import com.tranvu1805.easyfood.adapters.MealAdapter
import com.tranvu1805.easyfood.databinding.FragmentSearchBinding
import com.tranvu1805.easyfood.viewModel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var mealsAdapter: MealAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).homeViewModel
        prepareRecyclerView()
        searchMeals()
        observeLiveData()
    }

    private fun searchMeals() {
//        binding.ivSearch.setOnClickListener {
//            val searchQuery = binding.etSearch.text.toString()
//            if (searchQuery.isNotEmpty()) {
//                viewModel.searchMeal(searchQuery)
//            }else{
//                Toast.makeText(context, "Please enter a search query", Toast.LENGTH_SHORT).show()
//            }
//        }
        var searchJob: Job? = null
        binding.etSearch.addTextChangedListener {
            searchJob?.cancel()
            searchJob= lifecycleScope.launch{
                delay(500)
                val searchQuery = binding.etSearch.text.toString()
                if (searchQuery.isNotEmpty()) {
                    viewModel.searchMeal(searchQuery)
                }
            }
        }
    }

    private fun observeLiveData() {
        viewModel.mealBySearch.observe(viewLifecycleOwner) {
            mealsAdapter.differ.submitList(it)
        }
    }

    private fun prepareRecyclerView() {
        mealsAdapter = MealAdapter()
        binding.rvMeal.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = mealsAdapter
        }
    }

}