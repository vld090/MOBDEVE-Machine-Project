package com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication.databinding.NotificationItemBinding

class NotificationAdapter(private var notifList: List<NotifItem>): RecyclerView.Adapter<NotificationAdapter.NotificationListViewHolder>() {
    inner class NotificationListViewHolder(val binding: NotificationItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationListViewHolder {
        val binding = NotificationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationListViewHolder, position: Int) {
        with(holder){
            with(notifList[position]) {
                binding.tvMessage.text = message
                binding.tvNotiftime.text = timeInterval
            }
        }
    }


    override fun getItemCount(): Int {
        return notifList.size
    }
}