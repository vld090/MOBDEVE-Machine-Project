package com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "items.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_NAME = "items"
        const val COLUMN_ID = "id"
        const val COLUMN_ITEM_NAME = "item_name"
        const val COLUMN_BRAND = "brand"
        const val COLUMN_CATEGORY = "category"
        const val COLUMN_STOCK = "stock"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_ITEM_NAME TEXT,
                $COLUMN_BRAND TEXT,
                $COLUMN_CATEGORY TEXT,
                $COLUMN_STOCK TEXT
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
                val stock = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STOCK))
                items.add(CatalogItem(0, itemName, brand, category, stock)) // Replace 0 with a proper placeholder for imageResId
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

}
