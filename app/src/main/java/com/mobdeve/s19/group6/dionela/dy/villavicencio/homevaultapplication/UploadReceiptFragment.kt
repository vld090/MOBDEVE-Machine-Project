package com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Button
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment

class UploadReceiptFragment : Fragment() {

    private lateinit var imageView: ImageView
    private lateinit var spinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_upload_receipt, container, false)
        imageView = view.findViewById(R.id.ivReceiptImage)
        spinner = view.findViewById(R.id.spinnerItemAssociated)

        // Manually adding the items to the spinner
        val itemNames = listOf("Eggs", "Tissue Paper", "Onions", "Smart TV", "PS5", "Rice", "Ice Cream")

        // Set up the spinner with item names
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, itemNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val textViewButton = view.findViewById<TextView>(R.id.btnChooseImage)
        textViewButton.setOnClickListener {
            // Perform button action, e.g., open image selector
            parentFragmentManager.beginTransaction()
                .replace(R.id.flMainPage, CameraFragment())
                .addToBackStack(null) // Allows you to go back
                .commit()
        }


        return view
    }

//    private fun openImageSelector() {
//        val intent = Intent(Intent.ACTION_PICK)
//        intent.type = "image/*"
//        startActivityForResult(intent, IMAGE_PICK_CODE)
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                imageView.setImageURI(uri)
            }
        }
    }

    companion object {
        private const val IMAGE_PICK_CODE = 1001
    }
}
