package com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NotifPage : Fragment() {

    private lateinit var rvNotif: RecyclerView
    private lateinit var notifAdapter: NotificationAdapter
    private lateinit var notifHelper: NotifHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notif_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvNotif = view.findViewById(R.id.rvNotifItem)
        rvNotif.layoutManager = LinearLayoutManager(context)

        notifHelper = NotifHelper.getInstance(requireContext())
        val notifList = notifHelper.getNotifications().reversed() // Reverse the list to show latest notifications at the top

        notifAdapter = NotificationAdapter(notifList)
        rvNotif.adapter = notifAdapter
    }
}