package com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication

import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.*

class UploadReceiptFragment : Fragment() {

    private lateinit var imageView: ImageView
    private lateinit var spinner: Spinner
    private lateinit var addImgBtn: TextView
    private lateinit var receiptNameEditText: EditText
    private lateinit var expiryEditText: EditText
    private lateinit var walletDBHelper: WalletDBHelper
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_upload_receipt, container, false)

        walletDBHelper = WalletDBHelper(requireContext())
        databaseHelper = DatabaseHelper(requireContext())

        imageView = view.findViewById(R.id.ivReceiptImage)
        spinner = view.findViewById(R.id.spinnerItemAssociated)
        addImgBtn = view.findViewById(R.id.btnChooseImage)
        receiptNameEditText = view.findViewById(R.id.etReceiptName)
        expiryEditText = view.findViewById(R.id.etExpiry)

        // Populate Spinner
        populateSpinner()

        // Set up date picker for expiry date
        expiryEditText.setOnClickListener {
            showDatePickerDialog()
        }

        //pass image uri to fragment
        parentFragmentManager.setFragmentResultListener("camera_result", viewLifecycleOwner) { _, bundle ->
            val imageUri = bundle.getString("captured_image_uri")
            if (imageUri != null) {
                imageView.setImageURI(Uri.parse(imageUri))
                imageView.tag = imageUri
            }
        }

        // Handle image selection
        addImgBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.flMainPage, CameraFragment())
                .addToBackStack(null)
                .commit()
        }

        // Submit button logic
        view.findViewById<Button>(R.id.btnSubmitItem).setOnClickListener {
            saveReceipt()
        }

        return view
    }

    private fun populateSpinner() {
        val items = mutableListOf("None") // Default first option
        items.addAll(databaseHelper.getAllItems().map { it.itemName }) // Add item names from the database

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                // Format date to MM/dd/yyyy
                val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
                calendar.set(year, month, dayOfMonth)
                expiryEditText.setText(dateFormat.format(calendar.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    private fun saveReceipt() {
        val receiptName = receiptNameEditText.text.toString()
        val associatedItem = spinner.selectedItem?.toString() ?: "None" // Default to "None" if no selection
        val expiryDate = expiryEditText.text.toString()
        val createdDate = getCurrentDate() // Get current date
        val receiptUri = imageView.tag?.toString() ?: ""

        val newReceipt = WalletItem(receiptUri, receiptName, associatedItem, expiryDate, createdDate)

        if (receiptName.isNotEmpty() && expiryDate.isNotEmpty()) {
            walletDBHelper.insertWalletItem(newReceipt)

            val date = getCurrentDate()
            val newActivity = HistoryItem("New Item: ", receiptName, date)
            val historyDB = HistoryDBHelper(requireContext())

            historyDB.insertHistoryItem(newActivity)
            Toast.makeText(context, "Receipt added successfully!", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack() // Go back to Wallet fragment
        } else {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
    }

    // Get current date in MM/dd/yyyy format
    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        return dateFormat.format(Date())
    }
}
