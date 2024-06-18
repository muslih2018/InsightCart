package com.dicoding.InsightCart.ui.Inventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dicoding.InsightCart.R
import com.dicoding.InsightCart.databinding.FragmentInventoryBinding


class InventoryFragment : Fragment() {

    private var _binding: FragmentInventoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val inventoryViewModel =
            ViewModelProvider(this).get(InventoryViewModel::class.java)

        _binding = FragmentInventoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textInventory
        inventoryViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        binding.includedMenuLayout2.Menu.text = getString(R.string.inventory_icon)

//        navigate to profile
        // OnClickListener untuk ProfileIcon
        binding.includedMenuLayout2.ProfileIcon.setOnClickListener {
            // Navigasi ke ProfileFragment
            findNavController().navigate(
                R.id.action_navigation_inventory_to_profileFragment2,
                null,
            )
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}