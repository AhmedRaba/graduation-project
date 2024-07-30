package com.training.codespire.ui.frags.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.training.codespire.R
import com.training.codespire.data.viewmodel.AuthViewmodel
import com.training.codespire.databinding.FragmentPaymentBinding
import com.training.codespire.network.payment.PaymentRequest
import com.training.codespire.network.payment.PaymentResponse

private const val TAG = "PaymentFragment"

class PaymentFragment : Fragment() {

    private lateinit var binding: FragmentPaymentBinding
    private lateinit var authViewModel: AuthViewmodel
    private val args: PaymentFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentBinding.inflate(inflater, container, false)
        authViewModel = ViewModelProvider(this)[AuthViewmodel::class.java]

        binding.btnPayNow.setOnClickListener {
            processPayment()
        }

        authViewModel.paymentResponseLiveData.observe(viewLifecycleOwner) { paymentResponse ->
            paymentResponse?.let {
                handlePaymentSuccess(it)

            }
        }

        authViewModel.errorLiveData.observe(viewLifecycleOwner) { errorMessage ->
            handlePaymentError(errorMessage)
        }

        return binding.root
    }

    private fun validatePaymentDetails(
        cardNumber: String,
        expiryDate: String,
        cvv: String
    ): Boolean {
        val cardNumberPattern = Regex("^[0-9]{16}$")
        val expiryDatePattern = Regex("^(0[1-9]|1[0-2])/[0-9]{2}$")
        val cvvPattern = Regex("^[0-9]{3}$")

        return cardNumberPattern.matches(cardNumber) && expiryDatePattern.matches(expiryDate) && cvvPattern.matches(
            cvv
        )
    }

    private fun processPayment() {
        val cardNumber = binding.etCardNumber.text.toString().trim()
        val expiryDate = binding.etExpiryDate.text.toString().trim()
        val cvv = binding.etCvv.text.toString().trim()

        if (cardNumber.isEmpty() || expiryDate.isEmpty() || cvv.isEmpty()) {
            showToast("Please fill all fields")
            return
        }

        if (!validatePaymentDetails(cardNumber, expiryDate, cvv)) {
            showToast("Invalid payment details")
            return
        }

        val productId = args.productId
        val paymentRequest = PaymentRequest(cardNumber, expiryDate, cvv)
        authViewModel.makePayment(
            productId,
            paymentRequest.card_number,
            paymentRequest.expiry_date,
            paymentRequest.cvv
        )
    }

    private fun handlePaymentSuccess(paymentResponse: PaymentResponse) {
        showToast(paymentResponse.message)
        findNavController().navigateUp()
    }

    private fun handlePaymentError(errorMessage: String) {
        binding.tvErrorMessage.text = errorMessage
        binding.tvErrorMessage.visibility = View.VISIBLE
        showToast(errorMessage)
    }

    private fun showToast(message: String) {

        val inflater = layoutInflater
        val layout = inflater.inflate(R.layout.toast_layout, null)
        val textView = layout.findViewById<TextView>(R.id.toast_text)
        textView.text = message


        val toast = Toast(requireContext())
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.show()

    }
}
