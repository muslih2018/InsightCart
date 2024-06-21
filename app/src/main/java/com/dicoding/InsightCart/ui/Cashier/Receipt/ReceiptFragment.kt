package com.dicoding.InsightCart.ui.Cashier.Receipt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.InsightCart.R
import com.dicoding.InsightCart.data.Api.koneksi.ApiConfig
import com.dicoding.InsightCart.data.Api.koneksi.ApiService
import com.dicoding.InsightCart.data.Api.koneksi.ReceiptResponse
import com.dicoding.InsightCart.databinding.FragmentReceiptBinding
import com.dicoding.InsightCart.ui.Cashier.Records.ChildAdapter
import com.dicoding.InsightCart.ui.Cashier.Records.ChildItem
import com.google.android.material.bottomnavigation.BottomNavigationView
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
    private var bottomNavVisibility = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReceiptBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Hide bottom navigation view
        val bottomNavView = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavVisibility = bottomNavView?.isVisible ?: true
        bottomNavView?.visibility = View.GONE

        recyclerView = binding.langRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ChildAdapter(childList)
        recyclerView.adapter = adapter
        apiService = ApiConfig.getContentApiService()

        binding.arrowIcon.setOnClickListener {
            removeThisFragment()
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
                                childList.add(ChildItem(item.namaMenu, item.jumlah, item.harga))
                            }

                            // Notify adapter of data changes
                            adapter.notifyDataSetChanged()

                            // Set other data to TextViews if needed
                            binding.parentdateTv.text = it.tanggal
                            binding.parentIdTv.text = it.idTransaksi
                            binding.parenttotalTv.text = "Rp."+"${it.grandTotal}"
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

        // Handle back press
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                removeThisFragment()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Restore bottom navigation visibility
        val bottomNavView = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        if (bottomNavVisibility) {
            bottomNavView?.visibility = View.VISIBLE
        }

        _binding = null
    }

    private fun removeThisFragment() {
        // Remove current fragment from FragmentManager
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        fragmentManager.beginTransaction().remove(this).commit()
    }
}
