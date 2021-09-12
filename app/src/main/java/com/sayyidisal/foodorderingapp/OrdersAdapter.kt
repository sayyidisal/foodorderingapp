package com.sayyidisal.foodorderingapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_orders.view.*

/**
 * Created by Sayyid Shalahuddin on 9/8/2021.
 */

class OrdersAdapter(
    private var context: Context,
    private var titleProduct: ArrayList<String>,
    private var created_at: ArrayList<String>,
    private var alerted_at: ArrayList<String>,
    private var expired_at: ArrayList<String>,
    private var quantity: ArrayList<String>,
    private var titleaddon: ArrayList<String>,
    private var quantityaddon: ArrayList<String>,
) : RecyclerView.Adapter<OrdersAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersAdapter.MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_orders, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return titleProduct.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var nameproduct: TextView = itemView.findViewById<View>(R.id.productName) as TextView
        var additionalproduct: TextView = itemView.findViewById<View>(R.id.additional) as TextView
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.nameproduct.text = titleProduct[position]
        holder.additionalproduct.text = titleaddon[position]
    }
}