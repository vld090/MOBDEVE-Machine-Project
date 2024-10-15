package com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Button
import androidx.fragment.app.Fragment

class UploadReceiptFragment : Fragment() {

    private lateinit var imageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_upload_receipt, container, false)
        imageView = view.findViewById(R.id.ivReceiptImage)
        view.findViewById<Button>(R.id.btnChooseImage).setOnClickListener {
            openImageSelector()
        }
        return view
    }

    private fun openImageSelector() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

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

