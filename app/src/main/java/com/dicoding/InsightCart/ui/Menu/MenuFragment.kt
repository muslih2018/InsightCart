package com.dicoding.InsightCart.ui.Menu

import MenuAdapter
import MenuResponse
import MenuResponseItem
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.InsightCart.R
import com.dicoding.InsightCart.data.Api.koneksi.ApiConfig
import com.dicoding.InsightCart.databinding.FragmentHomeBinding
import com.dicoding.InsightCart.databinding.FragmentMenuBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class MenuFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var menuAdapter: MenuAdapter
    private var menuItems: MutableList<MenuResponseItem> = mutableListOf()
    private var _binding: FragmentMenuBinding? = null

    // Property binding hanya valid antara onCreateView dan onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Inisialisasi RecyclerView dari binding
        recyclerView = binding.recyclerMenuItems
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Inisialisasi Adapter
        menuAdapter = MenuAdapter(menuItems)
        recyclerView.adapter = menuAdapter

        // Panggil fungsi untuk mengambil data dari API
        getMenuItems()

        // Mengatur teks pada TextView di included layout
        binding.includedMenuLayout45.Menu.text = getString(R.string.menu)

        // OnClickListener untuk ProfileIcon
        binding.includedMenuLayout45.ProfileIcon.setOnClickListener {
            // Navigasi ke ProfileFragment
            findNavController().navigate(
                R.id.action_navigation_menu_to_profileFragment2,
                null,
            )
        }

        return root
    }

    private fun getMenuItems() {
        val service = ApiConfig.getContentApiService()
        service.getMenu().enqueue(object : Callback<List<MenuResponseItem>> {
            override fun onResponse(call: Call<List<MenuResponseItem>>, response: Response<List<MenuResponseItem>>) {
                if (response.isSuccessful) {
                    response.body()?.let { menuResponseItems ->
                        menuItems.clear()
                        menuItems.addAll(menuResponseItems)
                        menuAdapter.notifyDataSetChanged()
                    }
                } else {
                    // Handle error
                    Log.e(TAG, "Failed to get menu items: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<MenuResponseItem>>, t: Throwable) {
                // Handle failure
                Log.e(TAG, "Error fetching menu items: ${t.message}")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Membersihkan binding saat fragment dihancurkan
    }
}
