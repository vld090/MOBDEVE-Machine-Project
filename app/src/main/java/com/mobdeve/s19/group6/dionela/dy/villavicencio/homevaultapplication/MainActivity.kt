package com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication

import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var itemAdapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        window.statusBarColor = Color.parseColor("#4d5d80")

        recyclerView = findViewById(R.id.recyclerView2)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val items = listOf(
            Item(R.drawable.item1, "Eggs", "Brand: Bounty Fresh", "Category: Poultry", "In Stock: 10"),
            Item(R.drawable.item2, "Tissue Paper", "Brand: Sanicare", "Category: Sanitary", "In Stock: 3"),
            Item(R.drawable.item3, "Onions", "Brand:", "Category: Produce", "In Stock: 6"),
            Item(R.drawable.item4, "Smart TV", "Brand: Samsung", "Category: Appliance", "In Stock: 1"),
            Item(R.drawable.item5, "PS5", "Brand: Sony", "Category: Electronics", "In Stock: 1"),
            Item(R.drawable.item6, "Rice", "Brand: Dona Maria", "Category: Grains", "In Stock: 5kg"),
            Item(R.drawable.item7, "Ice Cream", "Brand: Ben & Jerry's", "Category: Dairy", "In Stock: 1")
        )

        itemAdapter = ItemAdapter(items)
        recyclerView.adapter = itemAdapter
    }
}
