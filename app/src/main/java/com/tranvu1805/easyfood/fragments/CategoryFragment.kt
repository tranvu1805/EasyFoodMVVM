package com.tranvu1805.easyfood.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.tranvu1805.easyfood.activities.CategoryActivity
import com.tranvu1805.easyfood.activities.MainActivity
import com.tranvu1805.easyfood.adapters.CategoryAdapter
import com.tranvu1805.easyfood.databinding.FragmentCategoryBinding
import com.tranvu1805.easyfood.models.Category
import com.tranvu1805.easyfood.viewModel.HomeViewModel


class CategoryFragment : Fragment() {
    private lateinit var binding: FragmentCategoryBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).homeViewModel

        viewModel.getCategory()
        observeLiveData()
        prepareRecyclerView()
        onCategoryClick()
    }

    private fun observeLiveData() {
        viewModel.categoryLiveData.observe(viewLifecycleOwner) {
            categoryAdapter.setList(it as ArrayList<Category>)
        }
    }

    private fun prepareRecyclerView() {
        categoryAdapter = CategoryAdapter()
        binding.rvCategory.apply {
            layoutManager = GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false)
            adapter = categoryAdapter
        }
    }
    private fun onCategoryClick() {
        categoryAdapter.onItemClickListener = {
            val intent = Intent(activity, CategoryActivity::class.java)
            intent.putExtra(HomeFragment.CATEGORY_NAME, it.strCategory)
            startActivity(intent)
        }
    }
}