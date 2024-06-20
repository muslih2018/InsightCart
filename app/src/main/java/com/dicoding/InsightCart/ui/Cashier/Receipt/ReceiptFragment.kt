package com.dicoding.InsightCart.ui.Cashier.Receipt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.InsightCart.R
import com.dicoding.InsightCart.databinding.FragmentReceiptBinding
import com.dicoding.InsightCart.ui.Cashier.Records.ChildAdapter
import com.dicoding.InsightCart.ui.Cashier.Records.ChildItem

class ReceiptFragment : Fragment() {

    private var _binding: FragmentReceiptBinding? = null
    private val binding get() = _binding!!
    private val childList = mutableListOf<ChildItem>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ChildAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReceiptBinding.inflate(inflater, container, false)
        val root: View = binding.root

        recyclerView = binding.langRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ChildAdapter(childList)
        recyclerView.adapter = adapter

        // Populate data to adapter (dummy data for demonstration)
        populateDummyData()

        return root
    }

    private fun populateDummyData() {
        // Example dummy data
        childList.add(ChildItem("Kopi", 2, 2.000))
        childList.add(ChildItem("Ayam", 1, 1.5000))
        childList.add(ChildItem("Susu", 3, 5.000))

        // Notify adapter of data changes
        adapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
