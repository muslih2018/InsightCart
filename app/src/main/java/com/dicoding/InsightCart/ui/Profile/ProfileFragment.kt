package com.dicoding.InsightCart.ui.Profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dicoding.InsightCart.R
import com.dicoding.InsightCart.databinding.FragmentProfileBinding
import com.dicoding.InsightCart.ui.onboaring.OnboardingActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

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

        // Set bottom navigation visibility to GONE
        val bottomNavView = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavView?.visibility = View.GONE

        binding.arrowIcon.setOnClickListener {
            activity?.onBackPressed()
        }

        binding.btnLogout.setOnClickListener {
            signOut()
        }

        // Menampilkan informasi pengguna saat fragment dibuat
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val email = user.email
            val displayName = user.displayName
            binding.user.text = displayName
            binding.emailValue.text = email
            binding.usernameValue.text = displayName ?: "No Display Name"
        }

        return root
    }

    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(requireActivity(), OnboardingActivity::class.java))
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Set bottom navigation visibility kembali to VISIBLE when fragment is destroyed
        val bottomNavView = activity?.findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavView?.visibility = View.VISIBLE

        _binding = null
    }
}
