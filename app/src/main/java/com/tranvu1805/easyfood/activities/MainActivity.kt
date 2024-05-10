package com.tranvu1805.easyfood.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.tranvu1805.easyfood.R
import com.tranvu1805.easyfood.databinding.ActivityMainBinding
import com.tranvu1805.easyfood.db.MealDatabase
import com.tranvu1805.easyfood.viewModel.HomeVMFactory
import com.tranvu1805.easyfood.viewModel.HomeViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val homeViewModel: HomeViewModel by lazy {
        val mealDatabase = MealDatabase.getDatabase(this)
        val homeVMFactory = HomeVMFactory(mealDatabase)
        ViewModelProvider(this, homeVMFactory)[HomeViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bnvHome, navController)
    }
}