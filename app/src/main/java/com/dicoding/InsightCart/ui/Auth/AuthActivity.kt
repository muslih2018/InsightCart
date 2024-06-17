package com.dicoding.InsightCart.ui.Auth

import android.os.Bundle
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.InsightCart.R
import com.dicoding.InsightCart.databinding.ActivityAuthBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class AuthActivity : AppCompatActivity() {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_login,
            R.string.tab_signup
        )
    }

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding.viewPager.adapter = sectionsPagerAdapter

//      button close
        binding.closeButton.setOnClickListener {
         finish()
        }

        // Atur TabLayout dengan custom view
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            val customTabView = layoutInflater.inflate(R.layout.custom_tab_layout, null) as TextView
            customTabView.text = resources.getString(TAB_TITLES[position])
            tab.customView = customTabView
        }.attach()


        // Atur warna teks untuk tab yang tidak dipilih
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


        supportActionBar?.elevation = 0f
    }

    private fun updateTabTextView(tab: TabLayout.Tab?, isSelected: Boolean) {
        val customView = tab?.customView as TextView?
        customView?.let {
            it.isSelected = isSelected // Memperbarui status terpilih atau tidak terpilih
            it.setTextColor(getColor(if (isSelected) R.color.tab_text_color_selected else R.color.tab_text_color_unselected))
        }
    }
    class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> LoginFragment()
                1 -> SignUpFragment()
                else -> throw IllegalArgumentException("Invalid position")
            }
        }

        override fun getItemCount(): Int {
            return 2
        }
    }
}
