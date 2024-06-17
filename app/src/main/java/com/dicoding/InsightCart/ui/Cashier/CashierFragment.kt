package com.dicoding.InsightCart.ui.Cashier

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}