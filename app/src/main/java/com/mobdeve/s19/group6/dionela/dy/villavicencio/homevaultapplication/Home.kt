package com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageButton
import android.widget.SearchView

class Home : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var itemAdapter: ItemAdapter
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var items: MutableList<CatalogItem> // Maintain a mutable list of items

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView2)
        recyclerView.layoutManager = LinearLayoutManager(context)

        dbHelper = DatabaseHelper(requireContext())
        items = dbHelper.getAllItems().toMutableList() // Load all items initially

        itemAdapter = ItemAdapter(items, dbHelper)
        recyclerView.adapter = itemAdapter

        val newItemBtn = view.findViewById<ImageButton>(R.id.newItemBtn)
        newItemBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.flMainPage, NewItemFragment())
                .addToBackStack(null)
                .commit()
        }

        // Set up SearchView
        val searchView = view.findViewById<SearchView>(R.id.svSearchItem)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterItems(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterItems(newText)
                return true
            }
        })
    }

    private fun filterItems(query: String?) {
        val filteredItems = if (query.isNullOrBlank()) {
            dbHelper.getAllItems() // Show all items when query is empty
        } else {
            dbHelper.searchItemsByName(query) // Fetch matching items
        }

        items.clear()
        items.addAll(filteredItems)
        itemAdapter.notifyDataSetChanged() // Notify adapter about changes
    }
}
