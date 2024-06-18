package com.dicoding.InsightCart.ui.Profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dicoding.InsightCart.R
import com.dicoding.InsightCart.databinding.FragmentProfileBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Mengatur BottomNavigationView di MainActivity menjadi tidak terlihat
        val bottomNavView = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavView?.visibility = View.GONE

        binding.arrowIcon.setOnClickListener {
            // Kembali ke fragment sebelumnya
            activity?.onBackPressed()
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Mengembalikan visibilitas BottomNavigationView ke Visible ketika fragment di-destroy
        val bottomNavView = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavView?.visibility = View.VISIBLE

        _binding = null
    }

}
