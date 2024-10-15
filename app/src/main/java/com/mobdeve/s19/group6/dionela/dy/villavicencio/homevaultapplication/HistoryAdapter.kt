package com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication.databinding.HistoryItemBinding

class HistoryAdapter(private var historyList: List<HistoryItem>): RecyclerView.Adapter<HistoryAdapter.HistoryListViewHolder>() {
    inner class HistoryListViewHolder(val binding: HistoryItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryListViewHolder {
        val binding = HistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryListViewHolder, position: Int) {
        with(holder){
            with(historyList[position]) {
                binding.tvHistoryName.text = action + ": " + item
                binding.tvDate.text = date
            }
        }
    }


    override fun getItemCount(): Int {
        return historyList.size
    }
}