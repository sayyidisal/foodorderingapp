package com.sayyidisal.foodorderingapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Sayyid Shalahuddin on 9/9/2021.
 */
class CustomAdapter(private val mDataList: ArrayList<product>) : RecyclerView.Adapter<CustomAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_orders, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.productName.text = mDataList[position].productname
        holder.productNameaddon.text = mDataList[position].productaddon
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var productName: TextView
        internal var productNameaddon: TextView

        init {
            productName = itemView.findViewById<View>(R.id.productName) as TextView
            productNameaddon = itemView.findViewById<View>(R.id.additional) as TextView
        }
    }

}