package com.sayyidisal.foodorderingapp.ingredient

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.sayyidisal.foodorderingapp.BaseActivity
import com.sayyidisal.foodorderingapp.DetailAcvitiry
import com.sayyidisal.foodorderingapp.R
import com.sayyidisal.foodorderingapp.ingredient.adapter.MainCategoryAdapter
import com.sayyidisal.foodorderingapp.ingredient.adapter.SubCategoryAdapter
import com.sayyidisal.foodorderingapp.ingredient.database.RecipeDatabase
import com.sayyidisal.foodorderingapp.ingredient.entities.CategoryItems
import com.sayyidisal.foodorderingapp.ingredient.entities.Meal
import com.sayyidisal.foodorderingapp.ingredient.entities.MealsItems
import com.sayyidisal.foodorderingapp.ingredient.interfaces.GetDataService
import com.sayyidisal.foodorderingapp.ingredient.retofitclient.RetrofitClientInstance
import kotlinx.android.synthetic.main.activity_ingredient.*
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class IngredientActivity : BaseActivity() {
    var arrMainCategory = ArrayList<CategoryItems>()
    var arrSubCategory = ArrayList<MealsItems>()

    var mainCategoryAdapter = MainCategoryAdapter()
    var subCategoryAdapter = SubCategoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredient)


        getDataFromDb()

        mainCategoryAdapter.setClickListener(onCLicked)
        subCategoryAdapter.setClickListener(onCLickedSubItem)




    }

    private val onCLicked  = object : MainCategoryAdapter.OnItemClickListener{
        override fun onClicked(categoryName: String) {
            getMealDataFromDb(categoryName)
        }
    }

    private val onCLickedSubItem  = object : SubCategoryAdapter.OnItemClickListener{
        override fun onClicked(id: String) {
            var intent = Intent(this@IngredientActivity,DetailAcvitiry::class.java)
            intent.putExtra("id",id)
            startActivity(intent)
        }
    }

    private fun getDataFromDb(){
        launch {
            this.let {
                var cat = RecipeDatabase.getDatabase(this@IngredientActivity).recipeDao().getAllCategory()
                arrMainCategory = cat as ArrayList<CategoryItems>
                arrMainCategory.reverse()

                getMealDataFromDb(arrMainCategory[0].strcategory)
                mainCategoryAdapter.setData(arrMainCategory)
                rv_main_category.layoutManager = LinearLayoutManager(this@IngredientActivity,LinearLayoutManager.HORIZONTAL,false)
                rv_main_category.adapter = mainCategoryAdapter
            }


        }
    }

    private fun getMealDataFromDb(categoryName:String){
        tvCategory.text = "$categoryName Category"
        launch {
            this.let {
                var cat = RecipeDatabase.getDatabase(this@IngredientActivity).recipeDao().getSpecificMealList(categoryName)
                arrSubCategory = cat as ArrayList<MealsItems>
                subCategoryAdapter.setData(arrSubCategory)
                rv_sub_category.layoutManager = LinearLayoutManager(this@IngredientActivity,LinearLayoutManager.HORIZONTAL,false)
                rv_sub_category.adapter = subCategoryAdapter
            }


        }
    }
}