package com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication.databinding.FragmentNewItemBinding
import android.widget.Toast

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
                .addToBackStack(null) // Allows you to go back
                .commit()
        }

        binding.btnSubmitItem.setOnClickListener {
            // Here you might handle the data insertion
            Toast.makeText(context, "Item added!", Toast.LENGTH_SHORT).show()
            // Navigate back or clear the form
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
