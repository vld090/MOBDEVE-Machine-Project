package com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

class NotifHelper private constructor(private val context: Context) {

    companion object {
        const val CHANNEL_ID = "notif_channel"
        @Volatile
        private var INSTANCE: NotifHelper? = null

        fun getInstance(context: Context): NotifHelper {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: NotifHelper(context.applicationContext).also { INSTANCE = it }
            }
        }
    }

    private val dbHelper = DatabaseHelper(context)

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notifications"
            val descriptionText = "Channel for app notifications, specifically for item with low stock and warranties close to expiry."
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun sendLowStockNotification(item: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Notification permission not granted", Toast.LENGTH_SHORT).show()
            return
        }

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentText("Item is low in stock!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            notify(System.currentTimeMillis().toInt(), builder.build())
        }

        val notifItem = NotifItem("Item '$item' is low in stock!", "Just now")
        dbHelper.addNotification(notifItem)
    }

    fun sendExpiryNotification(itemName: String, expiryDate: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Notification permission not granted", Toast.LENGTH_SHORT).show()
            return
        }

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Expiry Alert")
            .setContentText("Item '$itemName' will expire on $expiryDate.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            notify(System.currentTimeMillis().toInt(), builder.build())
        }

        val notifItem = NotifItem("Item '$itemName' will expire on $expiryDate.", "Just now")
        dbHelper.addNotification(notifItem)
    }

    fun getNotifications(): List<NotifItem> {
        return dbHelper.getAllNotifications()
    }
}