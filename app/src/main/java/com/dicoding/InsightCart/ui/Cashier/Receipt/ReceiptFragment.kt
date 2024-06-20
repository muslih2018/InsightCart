package com.dicoding.InsightCart.ui.Cashier.Receipt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.InsightCart.data.Api.koneksi.ApiConfig
import com.dicoding.InsightCart.data.Api.koneksi.ApiService
import com.dicoding.InsightCart.data.Api.koneksi.ReceiptResponse
import com.dicoding.InsightCart.databinding.FragmentReceiptBinding
import com.dicoding.InsightCart.ui.Cashier.Records.ChildAdapter
import com.dicoding.InsightCart.ui.Cashier.Records.ChildItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReceiptFragment : Fragment() {

    private var _binding: FragmentReceiptBinding? = null
    private val binding get() = _binding!!
    private val childList = mutableListOf<ChildItem>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ChildAdapter
    private lateinit var apiService: ApiService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReceiptBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.langRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ChildAdapter(childList)
        recyclerView.adapter = adapter
        apiService = ApiConfig.getContentApiService()

        binding.arrowIcon.setOnClickListener {
            activity?.onBackPressed()
        }

        val transactionId = arguments?.getString("transactionId")

        transactionId?.let { id ->
            apiService.getReceipt(id).enqueue(object : Callback<ReceiptResponse> {
                override fun onResponse(call: Call<ReceiptResponse>, response: Response<ReceiptResponse>) {
                    if (response.isSuccessful) {
                        val receipt = response.body()

                        receipt?.let {
                            // Clear existing items before adding new ones
                            childList.clear()

                            // Add items from API response to childList
                            for (item in it.items) {
                                childList.add(ChildItem(item.name, item.quantity, item.price))
                            }

                            // Notify adapter of data changes
                            adapter.notifyDataSetChanged()

                            // Set other data to TextViews if needed
                            binding.parentdateTv.text = "Tanggal: ${it.tanggal}"
                            binding.parentIdTv.text = "ID Transaksi: ${it.idTransaksi}"
                            binding.parenttotalTv.text = "Grand Total: Rp ${it.grandTotal}"
                        }
                    } else {
                        Toast.makeText(requireContext(), "Gagal mengambil data receipt", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ReceiptResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
