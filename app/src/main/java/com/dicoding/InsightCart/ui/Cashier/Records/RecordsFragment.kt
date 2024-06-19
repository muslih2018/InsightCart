package com.dicoding.InsightCart.ui.Cashier.Records

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.InsightCart.R
import com.dicoding.InsightCart.databinding.FragmentRecordsBinding
import com.dicoding.InsightCart.ui.Cashier.CashierFragment


class RecordsFragment : Fragment() {

    private var _binding: FragmentRecordsBinding? = null
    private val binding get() = _binding!!

    private val parentList = ArrayList<ParentItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment using binding
        _binding = FragmentRecordsBinding.inflate(inflater, container, false)
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


//        RecyclerView
        setUpRecyclerView()
        return root
    }


    private fun setUpRecyclerView() {
        binding.parentRecyclerView.setHasFixedSize(true)
        binding.parentRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        addDataToList()
        val adapter = ParentAdapter(parentList)
        binding.parentRecyclerView.adapter = adapter
    }

//    simulasi add item
    private fun addDataToList() {
        // Transaksi 1
        val childItems1 = ArrayList<ChildItem>()
        childItems1.add(ChildItem("kopi", 20, 2000))
        childItems1.add(ChildItem("ayam", 2, 20000))
        childItems1.add(ChildItem("susu", 2, 8000))
        childItems1.add(ChildItem("ayam bawang", 4, 10000))
        parentList.add(ParentItem("1221122", "03-07-2024 09:00", 200000, childItems1))

        // Transaksi 2
        val childItems2 = ArrayList<ChildItem>()
        childItems2.add(ChildItem("kopi", 20, 2000))
        childItems2.add(ChildItem("ayam", 2, 20000))
        childItems2.add(ChildItem("susu", 2, 8000))
        childItems2.add(ChildItem("ayam bawang", 4, 10000))
        parentList.add(ParentItem("1221122", "03-07-2024 09:00", 200000, childItems2))

        // Transaksi 3
        val childItems3 = ArrayList<ChildItem>()
        childItems3.add(ChildItem("kopi", 20, 2000))
        childItems3.add(ChildItem("ayam", 2, 20000))
        childItems3.add(ChildItem("susu", 2, 8000))
        childItems3.add(ChildItem("ayam bawang", 4, 10000))
        parentList.add(ParentItem("1221122", "03-07-2024 09:00", 200000, childItems3))

        // Transaksi 4
        val childItems4 = ArrayList<ChildItem>()
        childItems4.add(ChildItem("kopi", 20, 2000))
        childItems4.add(ChildItem("ayam", 2, 20000))
        childItems4.add(ChildItem("susu", 2, 8000))
        childItems4.add(ChildItem("ayam bawang", 4, 10000))
        parentList.add(ParentItem("1221122", "03-07-2024 09:00", 200000, childItems4))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
