package com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "items.db"
        private const val DATABASE_VERSION = 4

        const val TABLE_NAME = "items"
        const val COLUMN_ID = "id"
        const val COLUMN_ITEM_NAME = "item_name"
        const val COLUMN_BRAND = "brand"
        const val COLUMN_CATEGORY = "category"
        const val COLUMN_STOCK = "stock"
        const val COLUMN_IMAGE = "image_path"
        const val COLUMN_EXPIRY_DATE = "expiry_date"

        const val NOTIF_TABLE_NAME = "notifications"
        const val NOTIF_COLUMN_ID = "id"
        const val NOTIF_COLUMN_MESSAGE = "message"
        const val NOTIF_COLUMN_TIME_INTERVAL = "time_interval"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createItemsTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_ITEM_NAME TEXT,
                $COLUMN_BRAND TEXT,
                $COLUMN_CATEGORY TEXT,
                $COLUMN_STOCK INTEGER,
                $COLUMN_IMAGE TEXT,
                $COLUMN_EXPIRY_DATE TEXT
            )
        """
        db.execSQL(createItemsTableQuery)

        val createNotifTableQuery = """
            CREATE TABLE $NOTIF_TABLE_NAME (
                $NOTIF_COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $NOTIF_COLUMN_MESSAGE TEXT,
                $NOTIF_COLUMN_TIME_INTERVAL TEXT
            )
        """
        db.execSQL(createNotifTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 3) {
            db.execSQL("ALTER TABLE $TABLE_NAME ADD COLUMN $COLUMN_EXPIRY_DATE TEXT")
        }
        if (oldVersion < 4) {
            db.execSQL("CREATE TABLE $NOTIF_TABLE_NAME ($NOTIF_COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $NOTIF_COLUMN_MESSAGE TEXT, $NOTIF_COLUMN_TIME_INTERVAL TEXT)")
        }
    }

    fun addItem(item: CatalogItem): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_ITEM_NAME, item.itemName)
            put(COLUMN_BRAND, item.brand)
            put(COLUMN_CATEGORY, item.category)
            put(COLUMN_STOCK, item.stock)
            put(COLUMN_IMAGE, item.imageResId)
            put(COLUMN_EXPIRY_DATE, item.expiryDate)
        }
        val result = db.insert(TABLE_NAME, null, values)
        db.close()
        return result
    }

    fun getAllItems(): List<CatalogItem> {
        val items = mutableListOf<CatalogItem>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        cursor?.use {
            if (it.moveToFirst()) {
                do {
                    val itemName = it.getString(it.getColumnIndexOrThrow(COLUMN_ITEM_NAME))
                    val brand = it.getString(it.getColumnIndexOrThrow(COLUMN_BRAND))
                    val category = it.getString(it.getColumnIndexOrThrow(COLUMN_CATEGORY))
                    val stock = it.getInt(it.getColumnIndexOrThrow(COLUMN_STOCK))
                    val photo = it.getString(it.getColumnIndexOrThrow(COLUMN_IMAGE))
                    val expiryDate = it.getString(it.getColumnIndexOrThrow(COLUMN_EXPIRY_DATE))
                    items.add(CatalogItem(photo, itemName, brand, category, stock, expiryDate))
                } while (it.moveToNext())
            }
        }
        db.close()
        return items
    }

    fun deleteItem(itemName: String): Int {
        val db = this.writableDatabase
        val result = db.delete(TABLE_NAME, "$COLUMN_ITEM_NAME = ?", arrayOf(itemName))
        db.close()
        return result
    }

    fun updateStock(itemName: String, newStock: Int): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_STOCK, newStock)
        }
        val rowsUpdated = db.update(TABLE_NAME, values, "$COLUMN_ITEM_NAME = ?", arrayOf(itemName))
        db.close()
        return rowsUpdated
    }

    fun searchItemsByName(query: String): List<CatalogItem> {
        val items = mutableListOf<CatalogItem>()
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            null, // Select all columns
            "$COLUMN_ITEM_NAME LIKE ?", // WHERE clause
            arrayOf("%$query%"), // Argument for the LIKE clause
            null,
            null,
            null
        )
        cursor?.use {
            if (it.moveToFirst()) {
                do {
                    val itemName = it.getString(it.getColumnIndexOrThrow(COLUMN_ITEM_NAME))
                    val brand = it.getString(it.getColumnIndexOrThrow(COLUMN_BRAND))
                    val category = it.getString(it.getColumnIndexOrThrow(COLUMN_CATEGORY))
                    val stock = it.getInt(it.getColumnIndexOrThrow(COLUMN_STOCK))
                    val photo = it.getString(it.getColumnIndexOrThrow(COLUMN_IMAGE))
                    val expiryDate = it.getString(it.getColumnIndexOrThrow(COLUMN_EXPIRY_DATE))
                    items.add(CatalogItem(photo, itemName, brand, category, stock, expiryDate))
                } while (it.moveToNext())
            }
        }
        db.close()
        return items
    }

    fun getExpiringItems(daysBeforeExpiry: Int): List<CatalogItem> {
        val items = mutableListOf<CatalogItem>()
        val db = this.readableDatabase
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        Log.d("DatabaseHelper", "Current Date: $currentDate")
        val query = "SELECT * FROM $TABLE_NAME WHERE date($COLUMN_EXPIRY_DATE) <= date(?, '+$daysBeforeExpiry days')"
        val cursor = db.rawQuery(query, arrayOf(currentDate))
        Log.d("DatabaseHelper", "Query: $query")
        cursor?.use {
            if (it.moveToFirst()) {
                do {
                    val itemName = it.getString(it.getColumnIndexOrThrow(COLUMN_ITEM_NAME))
                    val brand = it.getString(it.getColumnIndexOrThrow(COLUMN_BRAND))
                    val category = it.getString(it.getColumnIndexOrThrow(COLUMN_CATEGORY))
                    val stock = it.getInt(it.getColumnIndexOrThrow(COLUMN_STOCK))
                    val photo = it.getString(it.getColumnIndexOrThrow(COLUMN_IMAGE))
                    val expiryDate = it.getString(it.getColumnIndexOrThrow(COLUMN_EXPIRY_DATE))
                    Log.d("DatabaseHelper", "Found expiring item: $itemName, Expiry Date: $expiryDate")
                    items.add(CatalogItem(photo, itemName, brand, category, stock, expiryDate))
                } while (it.moveToNext())
            } else {
                Log.d("DatabaseHelper", "No expiring items found.")
            }
        }
        db.close()
        return items
    }

    fun addNotification(notifItem: NotifItem): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(NOTIF_COLUMN_MESSAGE, notifItem.message)
            put(NOTIF_COLUMN_TIME_INTERVAL, notifItem.timeInterval)
        }
        val result = db.insert(NOTIF_TABLE_NAME, null, values)
        db.close()
        return result
    }

    fun getAllNotifications(): List<NotifItem> {
        val notifications = mutableListOf<NotifItem>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $NOTIF_TABLE_NAME", null)
        cursor?.use {
            if (it.moveToFirst()) {
                do {
                    val message = it.getString(it.getColumnIndexOrThrow(NOTIF_COLUMN_MESSAGE))
                    val timeInterval = it.getString(it.getColumnIndexOrThrow(NOTIF_COLUMN_TIME_INTERVAL))
                    notifications.add(NotifItem(message, timeInterval))
                } while (it.moveToNext())
            }
        }
        db.close()
        return notifications
    }
}