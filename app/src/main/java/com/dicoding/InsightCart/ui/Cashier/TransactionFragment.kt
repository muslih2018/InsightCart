package com.dicoding.InsightCart.ui.Cashier

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dicoding.InsightCart.R
import com.dicoding.InsightCart.databinding.FragmentTransactionBinding

class TransactionFragment : Fragment() {

    private var _binding: FragmentTransactionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment using binding
        _binding = FragmentTransactionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val parentFragment = requireParentFragment()
        if (parentFragment is CashierFragment) {
            // OnClickListener for ProfileIcon
            parentFragment.binding.includedMenuLayout3.ProfileIcon.setOnClickListener {
                // Navigate to ProfileFragment
                findNavController().navigate(
                    R.id.action_cashierFragment_to_profileFragment,
                    null
                )
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
