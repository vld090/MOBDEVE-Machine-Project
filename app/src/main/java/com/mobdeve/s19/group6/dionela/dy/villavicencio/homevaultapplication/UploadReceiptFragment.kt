package com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment

class UploadReceiptFragment : Fragment() {

    private lateinit var imageView: ImageView
    private lateinit var spinner: Spinner
    private lateinit var addImgBtn: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_upload_receipt, container, false)
        imageView = view.findViewById(R.id.ivReceiptImage)
        spinner = view.findViewById(R.id.spinnerItemAssociated)
        addImgBtn = view.findViewById(R.id.btnChooseImage)

        // Manually adding the items to the spinner
        val itemNames = listOf("Eggs", "Tissue Paper", "Onions", "Smart TV", "PS5", "Rice", "Ice Cream")

        // Set up the spinner with item names
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, itemNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        parentFragmentManager.setFragmentResultListener("camera_result", viewLifecycleOwner) { _, bundle ->
            val imageUri = bundle.getString("captured_image_uri")
            if (imageUri != null) {
                imageView.setImageURI(Uri.parse(imageUri))
                imageView.tag = imageUri
            }
        }

        addImgBtn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.flMainPage, CameraFragment())
                .addToBackStack(null)
                .commit()
        }


        return view
    }

}
