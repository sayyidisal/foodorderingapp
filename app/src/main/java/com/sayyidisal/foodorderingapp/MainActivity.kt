package com.sayyidisal.foodorderingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sayyidisal.foodorderingapp.ingredient.IngredientActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        orders.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,OrdersActivity::class.java)
            startActivity(intent)
        })

        ingredient.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, IngredientActivity::class.java)
            startActivity(intent)
        })

    }
    }