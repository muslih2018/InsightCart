package com.dicoding.InsightCart.ui.Inventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.InsightCart.R
import com.dicoding.InsightCart.databinding.FragmentInventoryBinding
import com.dicoding.InsightCart.ui.Inventory.Item.ItemFragment
import com.dicoding.InsightCart.ui.Inventory.Records.RecordsFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class InventoryFragment : Fragment() {

    private var _binding: FragmentInventoryBinding? = null
    private val binding get() = _binding!!

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.item,
            R.string.Records
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInventoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val pagerAdapter = InventoryPagerAdapter(this)
        binding.viewPager.adapter = pagerAdapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            val customTabView = layoutInflater.inflate(R.layout.custom_tab_layout_submenu, null) as TextView
            customTabView.text = resources.getString(TAB_TITLES[position])
            tab.customView = customTabView
        }.attach()

        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                updateTabTextView(tab, true)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                updateTabTextView(tab, false)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        // OnClickListener untuk ProfileIcon
        binding.includedMenuLayout2.ProfileIcon.setOnClickListener {
            // Navigasi ke ProfileFragment
            findNavController().navigate(
                R.id.action_navigation_inventory_to_profileFragment2,
                null,
            )
        }

        binding.includedMenuLayout2.Menu.text = getString(R.string.inventory)

        return root
    }

    private fun updateTabTextView(tab: TabLayout.Tab?, isSelected: Boolean) {
        val customView = tab?.customView as TextView?
        customView?.let {
            it.isSelected = isSelected
            it.setTextColor(resources.getColor(if (isSelected) R.color.tab_text_color_selected else R.color.tab_text_color_unselected))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private inner class InventoryPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = TAB_TITLES.size

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> ItemFragment()
                1 -> RecordsFragment()
                else -> throw IndexOutOfBoundsException("Invalid fragment position")
            }
        }
    }
}
