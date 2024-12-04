package com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView


class ViewReceiptFragment : Fragment() {
    private lateinit var receipt : ImageView
    private lateinit var name : TextView
    private lateinit var item : TextView
    private lateinit var expDate : TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_receipt, container, false)

        receipt = view.findViewById(R.id.ivReceiptPicture)
        name = view.findViewById(R.id.tvReceiptName)
        item = view.findViewById(R.id.tvAssoc_item)
        expDate = view.findViewById(R.id.tvExpDate)

        parentFragmentManager.setFragmentResultListener("camera_result", viewLifecycleOwner) { _, bundle ->
            val imageUri = bundle.getString("captured_image_uri")
            if (imageUri != null) {
                receipt.setImageURI(Uri.parse(imageUri))
                receipt.tag = imageUri
            }
        }

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val receiptName = arguments?.getString("receiptName")
        val associatedItem = arguments?.getString("associatedItem")
        val expiryDate = arguments?.getString("expiryDate")

        name.text = receiptName
        item.text = associatedItem
        expDate.text = expiryDate

        val receiptImageUri = arguments?.getString("receiptImageResUri")
        receiptImageUri?.let {
            receipt.setImageURI(Uri.parse(it))
        }

    }
}