package com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication

import android.app.IntentService
import android.content.Intent
import android.util.Log

class ExpiryCheckService : IntentService("ExpiryCheckService") {

    override fun onHandleIntent(intent: Intent?) {
        Log.d("ExpiryCheckService", "Service started")
        val dbHelper = DatabaseHelper(this)
        val notifHelper = NotifHelper.getInstance(this)
        val expiringItems = dbHelper.getExpiringItems(1) // Check for items expiring in the next 1 day

        for (item in expiringItems) {
            notifHelper.sendExpiryNotification(item.itemName, item.expiryDate)
        }

        Log.d("ExpiryCheckService", "Checked for expiring items and sent notifications if any.")
    }
}