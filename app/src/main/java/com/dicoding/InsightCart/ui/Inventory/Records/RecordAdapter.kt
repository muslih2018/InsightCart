package com.dicoding.InsightCart.ui.Inventory.Records

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.InsightCart.R
import com.dicoding.InsightCart.databinding.RecordListItemBinding

data class Record(val name: String, val quantity: String, val isPositive: Boolean)

class RecordAdapter(private val recordList: List<Record>) : RecyclerView.Adapter<RecordAdapter.RecordViewHolder>() {

    inner class RecordViewHolder(private val binding: RecordListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(record: Record) {
            binding.recordItemName.text = record.name
            binding.recordItemQuantity.text = record.quantity
            binding.recordItemQuantity.setTextColor(
                if (record.isPositive) binding.root.context.getColor(R.color.green) else binding.root.context.getColor(R.color.red)
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val binding = RecordListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.bind(recordList[position])
    }

    override fun getItemCount(): Int = recordList.size
}
