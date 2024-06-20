package com.dicoding.InsightCart.ui.Auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.dicoding.InsightCart.databinding.FragmentLoginBinding
import com.dicoding.InsightCart.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        binding.loginButton.setOnClickListener {
            signInWithEmailPassword()
        }

        return view
    }

    private fun signInWithEmailPassword() {
        val email = binding.emailxEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), "Email atau password tidak boleh kosong", Toast.LENGTH_LONG).show()
            return
        }

        // Show ProgressBar
        binding.pbLoding.visibility = View.VISIBLE

        // Using Coroutine for background task
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val authResult = withContext(Dispatchers.IO) {
                    auth.signInWithEmailAndPassword(email, password).await()
                }
                // Login successful
                val user = authResult.user
                updateUI(user)
            } catch (e: Exception) {
                // Login failed
                Log.w(TAG, "signInWithEmail:failure", e)
                updateUI(null)
            } finally {
                // Hide ProgressBar
                binding.pbLoding.visibility = View.GONE
            }
        }
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            // Show AlertDialog on successful login
            AlertDialog.Builder(requireContext()).apply {
                setTitle("Yeah!")
                setMessage("Akun Anda sudah berhasil login. Selamat datang!")
                setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    requireActivity().finish()
                }
                show()
            }
        } else {
            // Handle UI update if login fails
            Toast.makeText(requireContext(), "Login failed. Please try again.", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "LoginFragment"
    }
}
