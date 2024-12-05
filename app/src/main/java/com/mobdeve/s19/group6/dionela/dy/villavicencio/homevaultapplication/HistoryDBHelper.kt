package com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.mobdeve.s19.group6.dionela.dy.villavicencio.homevaultapplication.DatabaseHelper.Companion

class HistoryDBHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "history.db"
        private const val DATABASE_VERSION = 2

        const val TABLE_LOG_HISTORY = "history"
        const val COLUMN_ID = "id"
        const val COLUMN_ACTION = "action"
        const val COLUMN_ITEM = "item"
        const val COLUMN_DATE = "date_created"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = """
            CREATE TABLE $TABLE_LOG_HISTORY (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_ACTION TEXT,
                $COLUMN_ITEM TEXT,
                $COLUMN_DATE TEXT
            )
        """
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_LOG_HISTORY")
        onCreate(db)
    }

    fun insertHistoryItem(history: HistoryItem): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_ACTION, history.action)
            put(COLUMN_ITEM, history.item)
            put(COLUMN_DATE, history.date)
        }
        val result = db.insert(TABLE_LOG_HISTORY, null, values)
        db.close()
        return result
    }

    fun getAllHistoryItems(): List<HistoryItem> {
        val historyItems = mutableListOf<HistoryItem>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_LOG_HISTORY", null)
        if (cursor.moveToFirst()) {
            do {
                val historyItem = HistoryItem(
                    action = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ACTION)),
                    item = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ITEM)),
                    date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
                )
                historyItems.add(historyItem)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return historyItems
    }

    fun deleteAllHistoryItems(): Int {
        val db = this.writableDatabase
        val rowsDeleted = db.delete(TABLE_LOG_HISTORY, null, null)
        db.close()
        return rowsDeleted
    }



}
