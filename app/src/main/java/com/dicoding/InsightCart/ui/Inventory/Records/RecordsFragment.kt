package com.dicoding.InsightCart.ui.Inventory.Records

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.InsightCart.databinding.FragmentRecordsInventoryBinding

class RecordsFragment : Fragment() {

    private var _binding: FragmentRecordsInventoryBinding? = null
    private val binding get() = _binding!!

    private val recordList = ArrayList<Record>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecordsInventoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = RecordAdapter(recordList)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
