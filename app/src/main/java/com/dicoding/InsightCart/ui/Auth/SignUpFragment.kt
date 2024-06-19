package com.dicoding.InsightCart.ui.Auth

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.dicoding.InsightCart.R
import com.dicoding.InsightCart.databinding.FragmentSignUpBinding
import com.dicoding.InsightCart.ui.Cashier.CashierFragment
import com.dicoding.picodiploma.loginwithanimation.data.Api.Response.SignUpResponse

class SignUpFragment : Fragment() {


    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private val signUpViewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAction()
        signUpViewModel.isLoading.observe(requireActivity()) {
            showLoading(it)
        }
    }
    private fun showLoading(state: Boolean) {
        binding.pbLoding.isVisible = state

    }
    private fun setupAction() {
        binding.signupButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val name = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val confirmPassword = binding.confirmPasswordEditText.text.toString()

            // Validasi confirm password
            if (password != confirmPassword) {
                binding.confirmPasswordEditText.error = "Confirm Password tidak sesuai"
                return@setOnClickListener
            }

            signUpViewModel.postSignUp(name, email, password)

            // Sembunyikan keyboard
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)

            // Amati respons dari sign up
            signUpViewModel.signUpResponse.observe(viewLifecycleOwner) { data ->
                processSignUp(data)
                if (!data.error) {
                    // Bersihkan input fields jika sukses sign up
                    binding.usernameEditText.text = null
                    binding.emailEditText.text = null
                    binding.passwordEditText.text = null
                    binding.confirmPasswordEditText.text = null
                }
            }
        }
    }

    private fun processSignUp(data: SignUpResponse) {
        if (data.error) {
            Toast.makeText(requireContext(), "Gagal Sign Up", Toast.LENGTH_LONG).show()
        } else {
            val activity = requireActivity()
            if (activity is AuthActivity) {
                AlertDialog.Builder(activity).apply {
                    setTitle("Yeah!")
                    setMessage("Akun anda sudah jadi nih. Yuk, login")
                    setPositiveButton("Lanjut") { _, _ ->
                        activity.binding.tabs.getTabAt(0)?.select()
                    }
                    create()
                    show()
                }
            }
        }
    }




}