package com.dicoding.InsightCart.ui.analisis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dicoding.InsightCart.R
import com.dicoding.InsightCart.databinding.FragmentAnalisisBinding


class AnalisisFragment : Fragment() {

    private var _binding: FragmentAnalisisBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val analisisViewModel =
            ViewModelProvider(this).get(AnalisisViewModel::class.java)

        _binding = FragmentAnalisisBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textAnalisis
        analisisViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        binding.includedMenuLayout4.Menu.text = getString(R.string.analysis)
//        navigate to profile
        // OnClickListener untuk ProfileIcon
        binding.includedMenuLayout4.ProfileIcon.setOnClickListener {
            // Navigasi ke ProfileFragment
            findNavController().navigate(
                R.id.action_navigation_analisis_to_profileFragment2,
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