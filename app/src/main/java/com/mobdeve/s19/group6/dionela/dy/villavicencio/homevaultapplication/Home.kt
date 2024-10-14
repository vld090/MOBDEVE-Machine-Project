package com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var itemAdapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView2)
        recyclerView.layoutManager = LinearLayoutManager(context)

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