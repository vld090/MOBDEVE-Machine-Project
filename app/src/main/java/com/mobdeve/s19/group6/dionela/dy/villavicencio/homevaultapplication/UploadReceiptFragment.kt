package com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.*

class UploadReceiptFragment : Fragment() {

    private lateinit var walletDatabaseHelper: WalletDatabaseHelper
    private lateinit var imageView: ImageView
    private lateinit var spinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        walletDatabaseHelper = WalletDatabaseHelper(requireContext())

        val view = inflater.inflate(R.layout.fragment_upload_receipt, container, false)
        imageView = view.findViewById(R.id.ivReceiptImage)
        spinner = view.findViewById(R.id.spinnerItemAssociated)

        val btnSubmit = view.findViewById<Button>(R.id.btnSubmitItem)
        btnSubmit.setOnClickListener { insertWalletItem() }

        return view
    }

    private fun insertWalletItem() {
        val receiptName = view?.findViewById<EditText>(R.id.etReceiptName)?.text.toString()
        val associatedItem = spinner.selectedItem.toString()
        val expiryDate = "2024-12-01" // Placeholder
        val currentDate = getCurrentDate() // Generate the current timestamp
        val imageId = R.drawable.receipt1 // Placeholder

        val walletItem = WalletItem(imageId, receiptName, associatedItem, expiryDate, currentDate)
        walletDatabaseHelper.insertWallet(walletItem, currentDate)

        Toast.makeText(requireContext(), "Receipt added successfully!", Toast.LENGTH_SHORT).show()
        parentFragmentManager.popBackStack() // Return to previous fragment
    }

    // Helper function to get the current date
    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val currentDate = Calendar.getInstance().time
        return dateFormat.format(currentDate)
    }
}
