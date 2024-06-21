import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dicoding.InsightCart.R
import com.dicoding.InsightCart.data.Api.Response.CheckoutResponse
import com.dicoding.InsightCart.data.Api.koneksi.ApiConfig
import com.dicoding.InsightCart.data.Api.koneksi.CheckoutRequest
import com.dicoding.InsightCart.data.Api.koneksi.OrderItemRequest
import com.dicoding.InsightCart.databinding.FragmentTransactionBinding
import com.dicoding.InsightCart.ui.Cashier.CashierFragment
import com.dicoding.InsightCart.ui.Cashier.Receipt.ReceiptFragment
import com.dicoding.InsightCart.ui.Cashier.Transaction.Receipt
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransactionFragment : Fragment() {

    private var _binding: FragmentTransactionBinding? = null
    private val binding get() = _binding!!
    private val receiptsList = listOf("Searching menu....", "Chocolate syrup","Morning Sunrise Chai Rg","English Breakfast Lg","Ethiopia")
    private val receiptersList = ArrayList<Receipt>()
    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransactionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Submitting...")
        progressDialog.setCancelable(false)

        val parentFragment = requireParentFragment()
        if (parentFragment is CashierFragment) {
            parentFragment.binding.includedMenuLayout3.ProfileIcon.setOnClickListener {
                findNavController().navigate(
                    R.id.action_cashierFragment_to_profileFragment,
                    null
                )
            }
        }

        binding.buttonAdd.setOnClickListener() {
            addView()
        }
        binding.buttonSubmitList.setOnClickListener() {
            if (checkIfValidAndRead()) {
                performCheckout()
            } else {
                Toast.makeText(requireContext(), "Please enter valid menu!", Toast.LENGTH_SHORT).show()
            }
        }
        return root
    }

    private fun checkIfValidAndRead(): Boolean {
        receiptersList.clear()
        var result = true
        var errorMessage = ""

        for (i in 0 until binding.layoutList.childCount) {
            val receipterView = binding.layoutList.getChildAt(i)
            val autoCompleteTextView =
                receipterView.findViewById<AutoCompleteTextView>(R.id.auto_complete_text_view)
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
                receipter.receiptName = receiptsList[spinnerTeam.selectedItemPosition - 1]
            } else {
                result = false
                errorMessage = "Please select a menu!"
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
        val autoCompleteTextView =
            receipterView.findViewById<AutoCompleteTextView>(R.id.auto_complete_text_view)
        val spinnerTeam = receipterView.findViewById<Spinner>(R.id.spinner_team)
        val imageClose = receipterView.findViewById<ImageView>(R.id.image_remove)

        val autoCompleteAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            receiptsList
        )
        autoCompleteTextView.setAdapter(autoCompleteAdapter)
        autoCompleteTextView.setOnItemClickListener { parent, _, position, _ ->
            spinnerTeam.setSelection(position + 1)
        }

        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            receiptsList
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTeam.adapter = spinnerAdapter
        spinnerTeam.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
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
        checkIfValidAndRead()
    }

    private fun performCheckout() {
        progressDialog.show()

        val orderItems = mutableListOf<OrderItemRequest>()

        for (i in 0 until binding.layoutList.childCount) {
            val receipterView = binding.layoutList.getChildAt(i)
            val autoCompleteTextView =
                receipterView.findViewById<AutoCompleteTextView>(R.id.auto_complete_text_view)
            val editReceipterQuantity =
                receipterView.findViewById<EditText>(R.id.edit_receipter_quantity)

            val itemName = autoCompleteTextView.text.toString()
            val quantityString = editReceipterQuantity.text.toString()

            // Mengonversi teks kuantitas ke dalam tipe data Int dengan aman
            val quantity: Int
            try {
                quantity = quantityString.toInt()
            } catch (e: NumberFormatException) {
                progressDialog.dismiss()
                // Handle jika input tidak dapat diubah menjadi Int
                Toast.makeText(requireContext(), "Invalid quantity for $itemName", Toast.LENGTH_SHORT).show()
                return  // Keluar dari loop atau tindakan lain sesuai dengan kebutuhan Anda
            }

            // Pastikan kuantitas tidak negatif
            if (quantity <= 0) {
                progressDialog.dismiss()
                Toast.makeText(requireContext(), "Quantity must be greater than zero for $itemName", Toast.LENGTH_SHORT).show()
                return  // Keluar dari loop atau tindakan lain sesuai dengan kebutuhan Anda
            }

            orderItems.add(OrderItemRequest(itemName, quantity))
        }

        val checkoutRequest = CheckoutRequest(orderItems)

        val apiService = ApiConfig.getContentApiService().checkout(checkoutRequest)

        apiService.enqueue(object : Callback<CheckoutResponse> {
            override fun onResponse(
                call: Call<CheckoutResponse>,
                response: Response<CheckoutResponse>
            ) {
                progressDialog.dismiss()

                if (response.isSuccessful) {
                    val checkoutResponse = response.body()
                    checkoutResponse?.let {
                        // Lakukan navigasi ke fragment receipt dengan membawa transactionId
                        val transactionId = it.idTransaksi ?: ""
                        navigateToReceiptFragment(transactionId)
                    } ?: run {
                        Toast.makeText(requireContext(), "Response body is null", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Request failed with code: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CheckoutResponse>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(requireContext(), "Checkout error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun navigateToReceiptFragment(transactionId: String) {
        // Buat bundle untuk mengirim data ke fragment tujuan
        val bundle = Bundle().apply {
            putString("transactionId", transactionId)
        }

        // Buat instance fragment tujuan
        val receiptFragment = ReceiptFragment()
        receiptFragment.arguments = bundle

        // Lakukan transaksi fragment secara manual
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment_activity_main, receiptFragment) // Gantikan dengan container view yang sesuai
        transaction.addToBackStack(null) // (Opsional) Tambahkan ke back stack agar bisa kembali ke fragment sebelumnya
        transaction.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
