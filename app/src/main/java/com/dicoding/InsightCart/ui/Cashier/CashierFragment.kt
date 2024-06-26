package com.dicoding.InsightCart.ui.Cashier

import TransactionFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.InsightCart.R
import com.dicoding.InsightCart.databinding.FragmentCashierBinding
import com.dicoding.InsightCart.ui.Cashier.Records.RecordsFragment

import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CashierFragment : Fragment() {

    private var _binding: FragmentCashierBinding? = null
    val binding get() = _binding!!

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.Transaction,
            R.string.Records
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCashierBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Setup ViewPager2 with adapter
        val pagerAdapter = CashierPagerAdapter(this)
        binding.viewPager.adapter = pagerAdapter

        // Setup TabLayout with ViewPager2
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        // Customize tab layout with a custom view
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            val customTabView = layoutInflater.inflate(R.layout.custom_tab_layout_submenu, null) as TextView
            customTabView.text = resources.getString(TAB_TITLES[position])
            tab.customView = customTabView
        }.attach()

        // Set text color for unselected tabs
        binding.includedMenuLayout3.Menu.text = getString(R.string.cashier)
        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                updateTabTextView(tab, true)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                updateTabTextView(tab, false)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Optional: Handle reselection if needed
            }
        })

        // Set the third tab as initially selected
        binding.tabs.getTabAt(0)?.select()

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

    // Adapter for ViewPager2
    private inner class CashierPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = TAB_TITLES.size

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> TransactionFragment()
                1 -> RecordsFragment()
                else -> throw IndexOutOfBoundsException("Invalid fragment position")
            }
        }
    }
}
