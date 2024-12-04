package com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LogHistory : Fragment() {

    private lateinit var rvHistory: RecyclerView
    private lateinit var historyAdapter: HistoryAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_log_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvHistory = view.findViewById(R.id.rvHistoryList)
        rvHistory.layoutManager = LinearLayoutManager(context)

        val historyList = listOf(
            HistoryItem("New Item","Eggs", "October 10, 2024"),
            HistoryItem("New Item", "Tissue Paper", "July 10, 2024"),
            HistoryItem( "New Item", "Onions", "October 10, 2024"),
            HistoryItem( "New Item", "Smart TV", "August 6, 2023"),
            HistoryItem("New Item", "PS5", "August 6, 2023"),
            HistoryItem("New Item", "Rice", "October 10, 2024"),
            HistoryItem("New Item", "Ice Cream", "October 10, 2024")
        )

        historyAdapter = HistoryAdapter(historyList)
        rvHistory.adapter = historyAdapter
    }
}