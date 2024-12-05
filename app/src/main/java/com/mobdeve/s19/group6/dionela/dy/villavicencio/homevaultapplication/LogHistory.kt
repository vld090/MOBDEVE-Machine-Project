package com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LogHistory : Fragment() {

    private lateinit var rvHistory: RecyclerView
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var historyItem: MutableList<HistoryItem>
    private lateinit var historyDBHelper: HistoryDBHelper
    private lateinit var tvDeleteBtn: TextView

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

        historyDBHelper = HistoryDBHelper(requireContext())

        historyItem = historyDBHelper.getAllHistoryItems().toMutableList()

        historyAdapter = HistoryAdapter(historyItem)
        rvHistory.adapter = historyAdapter

        tvDeleteBtn = view.findViewById(R.id.tvDeleteAll)
        tvDeleteBtn.setOnClickListener{
            val rowsDeleted = historyDBHelper.deleteAllHistoryItems()
            if (rowsDeleted > 0) {
                Toast.makeText(context, "All history items deleted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "No items to delete", Toast.LENGTH_SHORT).show()
            }
        }
    }
}