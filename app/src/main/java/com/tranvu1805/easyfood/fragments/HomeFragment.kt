package com.tranvu1805.easyfood.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.tranvu1805.easyfood.R
import com.tranvu1805.easyfood.activities.CategoryActivity
import com.tranvu1805.easyfood.activities.DetailMealActivity
import com.tranvu1805.easyfood.activities.MainActivity
import com.tranvu1805.easyfood.adapters.CategoryAdapter
import com.tranvu1805.easyfood.adapters.PopularItemAdapter
import com.tranvu1805.easyfood.databinding.FragmentHomeBinding
import com.tranvu1805.easyfood.fragments.bottomSheet.BottomSheetFragment
import com.tranvu1805.easyfood.models.Category
import com.tranvu1805.easyfood.models.Meal
import com.tranvu1805.easyfood.models.MealByCategory
import com.tranvu1805.easyfood.viewModel.HomeViewModel


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularAdapter: PopularItemAdapter
    private lateinit var categoryAdapter: CategoryAdapter

    companion object {
        const val MEAL_ID = "com.tranvu1805.easyfood.viewModel.idMeal"
        const val MEAL_NAME = "com.tranvu1805.easyfood.viewModel.nameMeal"
        const val MEAL_THUMB = "com.tranvu1805.easyfood.viewModel.thumbMeal"
        const val CATEGORY_NAME = "com.tranvu1805.easyfood.viewModel.categoryName"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = (activity as MainActivity).homeViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        viewModel.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()

        viewModel.getPopularItems("Seafood")
        preparePopularAdapter()
        observerPopularItemsLiveData()
        onPopularItemClick()
        onPopularItemLongClick()

        viewModel.getCategory()
        prepareCategoryAdapter()
        observerCategoryLiveData()
        onCategoryClick()

        onSeacrhIconClick()
    }

    private fun onSeacrhIconClick() {
        binding.imgSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment2)
        }
    }

    private fun onPopularItemLongClick() {
        popularAdapter.onLongClickItem = {
            val bottomSheetFragment = BottomSheetFragment.newInstance(it.idMeal)
            bottomSheetFragment.show(childFragmentManager, "BottomSheetDialog")
        }
    }

    private fun onCategoryClick() {
        categoryAdapter.onItemClickListener = {
            val intent = Intent(activity, CategoryActivity::class.java)
            intent.putExtra(CATEGORY_NAME, it.strCategory)
            startActivity(intent)
        }
    }

    private fun observerCategoryLiveData() {
        viewModel.categoryLiveData.observe(viewLifecycleOwner) {
            categoryAdapter.setList(it as ArrayList<Category>)
        }
    }

    private fun onPopularItemClick() {
        popularAdapter.onItemClick={
            val intent = Intent(activity, DetailMealActivity::class.java)
            intent.putExtra(MEAL_ID, it.idMeal)
            intent.putExtra(MEAL_NAME, it.strMeal)
            intent.putExtra(MEAL_THUMB, it.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularAdapter() {
        binding.rvPopularItem.apply {
            popularAdapter = PopularItemAdapter()
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularAdapter
        }
    }
    private fun prepareCategoryAdapter() {
        binding.rvCategory.apply {
            categoryAdapter = CategoryAdapter()
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoryAdapter
        }
    }

    private fun observerPopularItemsLiveData() {
        viewModel.popularItemsLiveData.observe(viewLifecycleOwner) {
            popularAdapter.setList(it as ArrayList<MealByCategory>)
        }
    }

    private fun onRandomMealClick() {
        binding.imgRdMeal.setOnClickListener {
            val intent = Intent(activity, DetailMealActivity::class.java)
            if (::randomMeal.isInitialized){
                intent.putExtra(MEAL_ID, randomMeal.idMeal)
                intent.putExtra(MEAL_NAME, randomMeal.strMeal)
                intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
                startActivity(intent)
            }else{
                Toast.makeText(activity, "Random Meal is null", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun observerRandomMeal() {
        viewModel.randomMealLiveData.observe(
            viewLifecycleOwner
        ) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal.strMealThumb)
                .into(binding.imgRdMeal)
            this.randomMeal = meal
        }
    }
}