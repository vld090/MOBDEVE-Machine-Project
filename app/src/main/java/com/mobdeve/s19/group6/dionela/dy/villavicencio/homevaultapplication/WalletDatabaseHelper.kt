package com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class WalletDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "wallets.db"
        private const val DATABASE_VERSION = 1

        // Table and Columns
        const val TABLE_NAME = "wallets"
        const val COL_ID = "id"
        const val COL_NAME = "name"
        const val COL_ASSOC_ITEM_NAME = "assoc_item_name"
        const val COL_EXPIRY_DATE = "expiry_date"
        const val COL_CREATED_DATE = "created_date"
        const val COL_IMAGE_ID = "image_id"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_NAME TEXT,
                $COL_ASSOC_ITEM_NAME TEXT,
                $COL_EXPIRY_DATE TEXT,
                $COL_CREATED_DATE TEXT,
                $COL_IMAGE_ID INTEGER
            )
        """.trimIndent()
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Insert wallet item
    fun insertWallet(wallet: WalletItem, currentTime: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_NAME, wallet.name)
            put(COL_ASSOC_ITEM_NAME, wallet.assocItemName)
            put(COL_EXPIRY_DATE, wallet.expiryDate)
            put(COL_CREATED_DATE, currentTime) // Use current time
            put(COL_IMAGE_ID, wallet.imageId)
        }
        val result = db.insert(TABLE_NAME, null, values)
        db.close()
        return result
    }

    // Retrieve all wallet items
    fun getAllWallets(): List<WalletItem> {
        val walletList = mutableListOf<WalletItem>()
        val db = readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        if (cursor.moveToFirst()) {
            do {
                val wallet = WalletItem(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_IMAGE_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_ASSOC_ITEM_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_EXPIRY_DATE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_CREATED_DATE))
                )
                walletList.add(wallet)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return walletList
    }

    // Update wallet item
    fun updateWallet(wallet: WalletItem, id: Int): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_NAME, wallet.name)
            put(COL_ASSOC_ITEM_NAME, wallet.assocItemName)
            put(COL_EXPIRY_DATE, wallet.expiryDate)
            put(COL_IMAGE_ID, wallet.imageId)
        }
        val result = db.update(TABLE_NAME, values, "$COL_ID=?", arrayOf(id.toString()))
        db.close()
        return result
    }

    // Delete wallet item
    fun deleteWallet(id: Int): Int {
        val db = writableDatabase
        val result = db.delete(TABLE_NAME, "$COL_ID=?", arrayOf(id.toString()))
        db.close()
        return result
    }
}
