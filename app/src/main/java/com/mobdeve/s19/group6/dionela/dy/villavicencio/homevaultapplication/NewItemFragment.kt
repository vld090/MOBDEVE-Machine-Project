package com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication.databinding.FragmentNewItemBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NewItemFragment : Fragment() {

    private var _binding: FragmentNewItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCategoryDropdown()

        parentFragmentManager.setFragmentResultListener("camera_result", viewLifecycleOwner) { _, bundle ->
            val imageUri = bundle.getString("captured_image_uri")
            if (imageUri != null) {
                binding.imageButton.setImageURI(Uri.parse(imageUri))
                binding.imageButton.tag = imageUri
            }
        }

        binding.textView.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.flMainPage, CameraFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.btnSubmitItem.setOnClickListener {
            val itemName = binding.editItemName.text.toString().trim()
            val brand = binding.editBrand.text.toString().trim()
            val category = binding.spinnerCategory.selectedItem.toString()
            val _stock = binding.editStock.text.toString().trim()
            val stock = _stock.toInt()
            val photoUri = binding.imageButton.tag?.toString() ?: "default_value"

            if (itemName.isEmpty() || stock == 0) {
                Toast.makeText(context, "Please fill out all required fields.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newItem = CatalogItem(photoUri, itemName, brand, category, stock)
            val dbHelper = DatabaseHelper(requireContext())
            val result = dbHelper.addItem(newItem)

            if (result != -1L) {
                val date = getCurrentDate()
                val newActivity = HistoryItem("New Item: ", itemName, date)
                val historyDB = HistoryDBHelper(requireContext())

                historyDB.insertHistoryItem(newActivity)
                Toast.makeText(context, "Item added successfully!", Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()
            } else {
                Toast.makeText(context, "Failed to add item.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupCategoryDropdown() {
        val categories = arrayOf("Appliance", "Electronics", "Furniture", "Groceries", "Others")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, categories)
        binding.spinnerCategory.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        return dateFormat.format(Date())
    }
}
