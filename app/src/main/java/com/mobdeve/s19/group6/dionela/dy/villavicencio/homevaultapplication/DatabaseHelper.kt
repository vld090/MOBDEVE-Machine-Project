package com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "items.db"
        private const val DATABASE_VERSION = 2

        const val TABLE_NAME = "items"
        const val COLUMN_ID = "id"
        const val COLUMN_ITEM_NAME = "item_name"
        const val COLUMN_BRAND = "brand"
        const val COLUMN_CATEGORY = "category"
        const val COLUMN_STOCK = "stock"
        const val COLUMN_IMAGE = "image_path"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_ITEM_NAME TEXT,
                $COLUMN_BRAND TEXT,
                $COLUMN_CATEGORY TEXT,
                $COLUMN_STOCK TEXT,
                $COLUMN_IMAGE TEXT
            )
        """
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addItem(item: CatalogItem): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ITEM_NAME, item.itemName)
        values.put(COLUMN_BRAND, item.brand)
        values.put(COLUMN_CATEGORY, item.category)
        values.put(COLUMN_STOCK, item.stock)
        values.put(COLUMN_IMAGE, item.imageResId)
        val result = db.insert(TABLE_NAME, null, values)
        db.close()
        return result
    }

    fun getAllItems(): List<CatalogItem> {
        val items = mutableListOf<CatalogItem>()
        val db = this.readableDatabase
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                val itemName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ITEM_NAME))
                val brand = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BRAND))
                val category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY))
                val stock = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STOCK))
                val photo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE))
                items.add(CatalogItem(photo, itemName, brand, category, stock)) // Replace 0 with a proper placeholder for imageResId
            } while (cursor.moveToNext())
        }
        cursor.close()
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
            put(COLUMN_STOCK, newStock.toString())
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

        if (cursor.moveToFirst()) {
            do {
                val itemName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ITEM_NAME))
                val brand = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BRAND))
                val category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY))
                val stock = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STOCK))
                val photo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE))
                items.add(CatalogItem(photo, itemName, brand, category, stock)) // Placeholder for imageResId
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return items
    }

}
