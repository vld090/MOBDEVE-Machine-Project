package com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Home : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var itemAdapter: ItemAdapter
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var historyDBHelper: HistoryDBHelper
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

        historyDBHelper = HistoryDBHelper(requireContext())

        dbHelper = DatabaseHelper(requireContext())
        items = dbHelper.getAllItems().toMutableList() // Load all items initially

        itemAdapter = ItemAdapter(items, dbHelper, historyDBHelper)
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

        // Set up Sort Button
        val homeSortBtn = view.findViewById<ImageButton>(R.id.homeSortBtn)
        homeSortBtn.setOnClickListener {
            showSortOptions()
        }
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

    private fun showSortOptions() {
        val popupMenu = PopupMenu(requireContext(), requireView().findViewById(R.id.homeSortBtn))
        popupMenu.menuInflater.inflate(R.menu.sort_items, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.sort_by_name -> sortItems { it.itemName }
                R.id.sort_by_brand -> sortItems { it.brand }
                R.id.sort_by_category -> sortItems { it.category }
                R.id.sort_by_stock -> sortItems { it.stock }
            }
            true
        }
        popupMenu.show()
    }

    private fun sortItems(selector: (CatalogItem) -> Comparable<*>?) {
        items.sortWith(compareBy(selector))
        itemAdapter.notifyDataSetChanged()
    }
}

