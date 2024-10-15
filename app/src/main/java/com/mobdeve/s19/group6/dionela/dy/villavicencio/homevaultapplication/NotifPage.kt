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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notif_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvNotif = view.findViewById(R.id.rvNotifItem)
        rvNotif.layoutManager = LinearLayoutManager(context)

        val notifList = listOf(
            NotifItem("Sample notification 1","30m ago"),
            NotifItem("Sample notification 2", "30m ago"),
            NotifItem( "Sample notification 3", "30m ago"),
            NotifItem( "Sample notification 4", "3d ago"),
            NotifItem("Sample notification 5", "3d ago"),
            NotifItem("Sample notification 6", "5d ago"),
            NotifItem("Sample notification 7", "5d ago")
        )

        notifAdapter = NotificationAdapter(notifList)
        rvNotif.adapter = notifAdapter
    }
}