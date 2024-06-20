package com.dicoding.InsightCart.ui.Cashier.Records

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.InsightCart.R

class ChildAdapter(private val childList: List<ChildItem>) :
    RecyclerView.Adapter<ChildAdapter.ChildViewHolder>() {

    inner class ChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val quantity: TextView = itemView.findViewById(R.id.childquantityTv)
        val harga: TextView = itemView.findViewById(R.id.childhargaTv)
        val menuname: TextView = itemView.findViewById(R.id.childMenuNameTv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.child_item, parent, false)
        return ChildViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        holder.menuname.text = childList[position].menuname
        holder.quantity.text = childList[position].quantity.toString()
        holder.harga.text = childList[position].harga.toString()
    }

    override fun getItemCount(): Int {
        return childList.size
    }

}
