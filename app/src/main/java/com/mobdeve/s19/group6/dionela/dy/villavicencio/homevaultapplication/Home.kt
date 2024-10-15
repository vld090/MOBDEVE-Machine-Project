package com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.ImageButton




class Home : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var itemAdapter: ItemAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView2)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val items = listOf(
            CatalogItem(R.drawable.item1, "Eggs", "Brand: Bounty Fresh", "Category: Poultry", "In Stock: 10"),
            CatalogItem(R.drawable.item2, "Tissue Paper", "Brand: Sanicare", "Category: Sanitary", "In Stock: 3"),
            CatalogItem(R.drawable.item3, "Onions", "Brand:", "Category: Produce", "In Stock: 6"),
            CatalogItem(R.drawable.item4, "Smart TV", "Brand: Samsung", "Category: Appliance", "In Stock: 1"),
            CatalogItem(R.drawable.item5, "PS5", "Brand: Sony", "Category: Electronics", "In Stock: 1"),
            CatalogItem(R.drawable.item6, "Rice", "Brand: Dona Maria", "Category: Grains", "In Stock: 5kg"),
            CatalogItem(R.drawable.item7, "Ice Cream", "Brand: Ben & Jerry's", "Category: Dairy", "In Stock: 1")
        )

        itemAdapter = ItemAdapter(items)
        recyclerView.adapter = itemAdapter

        val newItemBtn = view.findViewById<ImageButton>(R.id.newItemBtn)
        newItemBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.flMainPage, NewItemFragment())
                .addToBackStack(null)  // Optional, if you want to allow users to navigate back
                .commit()
        }
    }


}