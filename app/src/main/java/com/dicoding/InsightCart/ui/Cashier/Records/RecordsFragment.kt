package com.dicoding.InsightCart.ui.Cashier.Records


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.InsightCart.R
import com.dicoding.InsightCart.data.Api.koneksi.AllTransactionItem
import com.dicoding.InsightCart.data.Api.koneksi.ApiConfig
import com.dicoding.InsightCart.databinding.FragmentRecordsBinding
import com.dicoding.InsightCart.ui.Cashier.CashierFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecordsFragment : Fragment() {

    private var _binding: FragmentRecordsBinding? = null
    private val binding get() = _binding!!

    private val parentList = ArrayList<ParentItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment using binding
        _binding = FragmentRecordsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val parentFragment = requireParentFragment()
        if (parentFragment is CashierFragment) {

            // OnClickListener for ProfileIcon
            parentFragment.binding.includedMenuLayout3.ProfileIcon.setOnClickListener {
                // Navigate to ProfileFragment
                findNavController().navigate(
                    R.id.action_cashierFragment_to_profileFragment,
                    null
                )
            }
        }

        // Setup RecyclerView
        setUpRecyclerView()

        // Load data from API
        loadDataFromApi()

        return root
    }

    private fun setUpRecyclerView() {
        binding.parentRecyclerView.setHasFixedSize(true)
        binding.parentRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = ParentAdapter(parentList)
        binding.parentRecyclerView.adapter = adapter
    }

    private fun loadDataFromApi() {
        binding.pbLoding.visibility = View.VISIBLE
        val apiService = ApiConfig.getContentApiService()
        val call = apiService.getAllReceipts()
        call.enqueue(object : Callback<List<AllTransactionItem>> {
            override fun onResponse(
                call: Call<List<AllTransactionItem>>,
                response: Response<List<AllTransactionItem>>
            ) {
                binding.pbLoding.visibility = View.GONE
                if (response.isSuccessful) {
                    response.body()?.let { transactions ->
                        addDataToList(transactions) // Populate data to parentList from API response
                    }
                } else {
                    Toast.makeText(requireContext(), "Gagal memuat Records", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<AllTransactionItem>>, t: Throwable) {
                // Handle failure to connect or other errors
            }
        })
    }

    private fun addDataToList(transactions: List<AllTransactionItem>) {
        parentList.clear() // Clear existing data
        for (transaction in transactions) {
            val childItems = ArrayList<ChildItem>()
            val menuname = transaction.productId ?: "Default Product"
            childItems.add(
                ChildItem(
                    menuname,
                    transaction.quantity,
                    transaction.unitPrice
                )
            )

            // Check if transaction.transactionId is null or empty before creating ParentItem
            val transactionId = transaction.transactionId ?: ""

            parentList.add(
                ParentItem(
                    transactionId,
                    "${transaction.transactionDate} ${transaction.transactionTime}",
                    transaction.lineItemAmount, // Make sure this matches the type in ParentItem
                    childItems
                )
            )
        }
        binding.parentRecyclerView.adapter?.notifyDataSetChanged() // Notify adapter that data has changed
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
