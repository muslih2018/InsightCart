package com.dicoding.InsightCart.ui.Auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.InsightCart.databinding.FragmentLoginBinding
import com.dicoding.InsightCart.ui.MainActivity // Pastikan ini sesuai dengan nama Activity yang ingin Anda tuju

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root

        // Menetapkan OnClickListener untuk tombol login
        binding.loginButton.setOnClickListener {
            // Intent untuk memulai MainActivity
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)

            // Menutup Activity saat ini jika diperlukan
            activity?.finish()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
