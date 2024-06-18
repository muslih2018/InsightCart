package com.dicoding.InsightCart.ui.Cashier

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.dicoding.InsightCart.R
import com.dicoding.InsightCart.databinding.FragmentCashierBinding

class CashierFragment : Fragment() {

    private var _binding: FragmentCashierBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val cashierViewModel =
            ViewModelProvider(this).get(CashierViewModel::class.java)

        _binding = FragmentCashierBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textCashier
        cashierViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        binding.includedMenuLayout3.Menu.text = getString(R.string.Transaction)
//      navigate to profile
        // OnClickListener untuk ProfileIcon
        binding.includedMenuLayout3.ProfileIcon.setOnClickListener {
            // Navigasi ke ProfileFragment
            findNavController().navigate(
                R.id.action_cashierFragment_to_profileFragment,
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