package com.dicoding.InsightCart.ui.Cashier.Transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dicoding.InsightCart.R
import com.dicoding.InsightCart.databinding.FragmentTransactionBinding
import com.dicoding.InsightCart.ui.Cashier.CashierFragment

class TransactionFragment : Fragment() , View.OnClickListener{

    private var _binding: FragmentTransactionBinding? = null
    private val binding get() = _binding!!
    // Data for dummy list
    private val receiptsList = listOf("Kopi", "Kue", "Susu", "Roti Bakar")
    private val receiptersList = ArrayList<Receipt>()

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
        binding.buttonAdd.setOnClickListener(this)
        binding.buttonSubmitList.setOnClickListener(this)
        return root
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_add -> addView()
            R.id.button_submit_list -> {
                if (checkIfValidAndRead()) {
                    Toast.makeText(requireContext(), "Submit List clicked!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Please enter valid menu!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun checkIfValidAndRead(): Boolean {
        receiptersList.clear()
        var result = true
        var errorMessage = ""

        for (i in 0 until binding.layoutList.childCount) {
            val receipterView = binding.layoutList.getChildAt(i)

            val autoCompleteTextView = receipterView.findViewById<AutoCompleteTextView>(R.id.auto_complete_text_view)
            val spinnerTeam = receipterView.findViewById<Spinner>(R.id.spinner_team)

            val receipter = Receipt()

            val receipterNameText = autoCompleteTextView.text.toString()
            if (receiptsList.contains(receipterNameText)) {
                receipter.receiptName = receipterNameText
            } else {
                result = false
                errorMessage = "Menu not found!"
                autoCompleteTextView.error = errorMessage
                break
            }

            if (spinnerTeam.selectedItemPosition != 0) {
                receipter.receiptName = receiptsList[spinnerTeam.selectedItemPosition - 1] // -1 karena posisi 0 adalah hint di Spinner
            } else {
                result = false
                errorMessage = "Please select a team!"
                break
            }

            receiptersList.add(receipter)
        }

        if (receiptersList.isEmpty()) {
            result = false
            errorMessage = "Add Receipters First!"
        }

        if (!result) {
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }

        return result && receiptersList.isNotEmpty()
    }

    private fun addView() {
        val receipterView = layoutInflater.inflate(R.layout.row_add_transaction, null, false)

        val autoCompleteTextView = receipterView.findViewById<AutoCompleteTextView>(R.id.auto_complete_text_view)
        val spinnerTeam = receipterView.findViewById<Spinner>(R.id.spinner_team)
        val imageClose = receipterView.findViewById<ImageView>(R.id.image_remove)

        // Set up AutoCompleteTextView
        val autoCompleteAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, receiptsList)
        autoCompleteTextView.setAdapter(autoCompleteAdapter)
        autoCompleteTextView.setOnItemClickListener { parent, _, position, _ ->
            spinnerTeam.setSelection(position + 1) // +1 karena posisi 0 adalah hint di Spinner
        }

        // Set up Spinner
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, receiptsList)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTeam.adapter = spinnerAdapter
        spinnerTeam.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                autoCompleteTextView.setText(receiptsList[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }

        imageClose.setOnClickListener { removeView(receipterView) }

        binding.layoutList.addView(receipterView)
    }

    private fun removeView(view: View) {
        binding.layoutList.removeView(view)
        checkIfValidAndRead() // Update submit button status after removing a view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
