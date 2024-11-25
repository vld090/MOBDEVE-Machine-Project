package com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication.databinding.FragmentNewItemBinding

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
            val stock = binding.editStock.text.toString().trim()

            if (itemName.isEmpty() || stock.isEmpty()) {
                Toast.makeText(context, "Please fill out all required fields.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newItem = CatalogItem(0, itemName, brand, category, stock)
            val dbHelper = DatabaseHelper(requireContext())
            val result = dbHelper.addItem(newItem)

            if (result != -1L) {
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
}
