package com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication.DatabaseHelper.Companion

class WalletDBHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "wallet.db"
        private const val DATABASE_VERSION = 2

        const val TABLE_WALLET = "wallet"
        const val COLUMN_ID = "id"
        const val COLUMN_IMAGE = "image"
        const val COLUMN_NAME = "name"
        const val COLUMN_ASSOC_ITEM = "assoc_item"
        const val COLUMN_EXPIRY_DATE = "expiry_date"
        const val COLUMN_CREATED_DATE = "created_date"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = """
            CREATE TABLE $TABLE_WALLET (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_IMAGE TEXT,
                $COLUMN_NAME TEXT,
                $COLUMN_ASSOC_ITEM TEXT,
                $COLUMN_EXPIRY_DATE TEXT,
                $COLUMN_CREATED_DATE TEXT
            )
        """
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_WALLET")
        onCreate(db)
    }

    fun insertWalletItem(receipt: WalletItem): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_IMAGE, receipt.imageId)
            put(COLUMN_NAME, receipt.name)
            put(COLUMN_ASSOC_ITEM, receipt.assocItemName)
            put(COLUMN_EXPIRY_DATE, receipt.expiryDate)
            put(COLUMN_CREATED_DATE, receipt.createdDate)
        }
        val result = db.insert(TABLE_WALLET, null, values)
        db.close()
        return result
    }

    fun getAllWalletItems(): List<WalletItem> {
        val walletItems = mutableListOf<WalletItem>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_WALLET", null)
        if (cursor.moveToFirst()) {
            do {
                val walletItem = WalletItem(
                    imageId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE)), // Default placeholder
                    name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                    assocItemName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ASSOC_ITEM)),
                    expiryDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPIRY_DATE)),
                    createdDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CREATED_DATE))
                )
                walletItems.add(walletItem)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return walletItems
    }

    fun deleteWalletItem(name: String): Int {
        val db = this.writableDatabase
        val rowsDeleted = db.delete(TABLE_WALLET, "$COLUMN_NAME = ?", arrayOf(name))
        db.close()
        return rowsDeleted
    }



}
