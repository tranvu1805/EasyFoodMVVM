package com.tranvu1805.easyfood.fragments.bottomSheet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tranvu1805.easyfood.activities.DetailMealActivity
import com.tranvu1805.easyfood.activities.MainActivity
import com.tranvu1805.easyfood.databinding.FragmentBottomSheetBinding
import com.tranvu1805.easyfood.fragments.HomeFragment
import com.tranvu1805.easyfood.viewModel.HomeViewModel


private const val MEAL_ID = "param1"

class BottomSheetFragment : BottomSheetDialogFragment() {
    private var mealId: String? = null
    private lateinit var binding: FragmentBottomSheetBinding
    private lateinit var viewModel: HomeViewModel
    private var mealThumb: String? = null
    private var mealName: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEAL_ID)
        }
        viewModel = (activity as MainActivity).homeViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mealId?.let {
            viewModel.getMealById(it)

        }
        onObserverLiveData()
        onClick()
    }

    private fun onClick() {
        binding.bottomSheet.setOnClickListener {
            if (mealName != null && mealThumb != null) {
                val intent = Intent(activity, DetailMealActivity::class.java)
                intent.apply {
                    putExtra(HomeFragment.MEAL_ID, mealId)
                    putExtra(HomeFragment.MEAL_NAME, mealName)
                    putExtra(HomeFragment.MEAL_THUMB, mealThumb)
                }
                startActivity(intent)
            }
        }
    }

    private fun onObserverLiveData() {
        viewModel.bottomSheetMealLiveData.observe(viewLifecycleOwner) {
            Glide.with(this).load(it.strMealThumb).into(binding.imgMeal)
            binding.apply {
                tvMealName.text = it.strMeal
                tvCategory.text = it.strCategory
                tvCountry.text = it.strArea
            }

            mealThumb = it.strMealThumb
            mealName = it.strMeal
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            BottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, param1)
                }
            }
    }
}