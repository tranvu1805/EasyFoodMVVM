package com.tranvu1805.easyfood.activities

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.tranvu1805.easyfood.R
import com.tranvu1805.easyfood.databinding.ActivityDetailMealBinding
import com.tranvu1805.easyfood.db.MealDatabase
import com.tranvu1805.easyfood.fragments.HomeFragment
import com.tranvu1805.easyfood.models.Meal
import com.tranvu1805.easyfood.viewModel.MealDetailViewModel
import com.tranvu1805.easyfood.viewModel.MealVMFactory

class DetailMealActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailMealBinding
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var youtubeLink: String
    private lateinit var mealDetailVM: MealDetailViewModel
    private var mealToFavorite: Meal? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getInformationFromIntent()
        setInformationToView()
        loadingCase()
        val mealDatabase = MealDatabase.getDatabase(this)
        val mealDetailVMFactory = MealVMFactory(mealDatabase)
        mealDetailVM = ViewModelProvider(this, mealDetailVMFactory)[MealDetailViewModel::class.java]
        mealDetailVM.getMealDetail(mealId)
        observeLiveData()
        onYoutubeClick()
        onFavoriteClick()
    }

    private fun onFavoriteClick() {
        binding.btnFavorite.setOnClickListener {
            mealToFavorite?.let {
                mealDetailVM.insertMeal(it)
                Toast.makeText(this, "Meal Saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onYoutubeClick() {
        binding.imgYoutube.setOnClickListener {
            if (youtubeLink.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
                startActivity(intent)
            }else{
                Log.d("DetailMealActivity", "onYoutubeClick: Youtube link is empty")
            }
        }
    }

    private fun observeLiveData() {
        mealDetailVM.mealLiveData.observe(this) {
            mealToFavorite = it
            onResponseCase()
            binding.tvCategoryInfo.text = getString(R.string.category, it.strCategory)
            binding.tvAreaInfo.text = getString(R.string.area, it.strArea)
            binding.tvInstructions.text = it.strInstructions
            youtubeLink = it.strYoutube!!
        }
    }

    private fun setInformationToView() {
        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE)
        binding.collapsingToolbar.setExpandedTitleColor(Color.GREEN)
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)
    }

    private fun getInformationFromIntent() {
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID).toString()
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME).toString()
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB).toString()
    }

    private fun loadingCase() {
        binding.progressBar.visibility = VISIBLE
    }

    private fun onResponseCase() {
        binding.progressBar.visibility = INVISIBLE
        binding.btnFavorite.visibility = VISIBLE
        binding.tvCategoryInfo.visibility = VISIBLE
        binding.tvAreaInfo.visibility = VISIBLE
        binding.btnFavorite.visibility = VISIBLE
    }
}