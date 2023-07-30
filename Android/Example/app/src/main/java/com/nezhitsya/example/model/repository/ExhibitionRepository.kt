package com.nezhitsya.example.model.repository

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.nezhitsya.example.model.MyExhibition

class ExhibitionRepository(private val context: Context) {
    private val database: SQLiteDatabase

    init {
        val helper = object : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
            override fun onCreate(db: SQLiteDatabase) {
                db.execSQL(CREATE_TABLE_QUERY)
            }

            override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
                db.execSQL(DROP_TABLE_QUERY)
                onCreate(db)
            }
        }

        database = helper.writableDatabase
    }

    companion object {
        private const val DATABASE_NAME = "ExhibitionDatabase.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "exhibition"
        private const val CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS exhibition (id INTEGER PRIMARY KEY, exhibitionTl TEXT NOT NULL, exhibitionImg TEXT, exhibitionDate TEXT, exhibitionPlace TEXT, exhibitionMap TEXT, exhibitionPrice TEXT, exhibitionView TEXT)"
        private const val DROP_TABLE_QUERY = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

    fun getAllMyExhibitions(): List<MyExhibition> {
        val exhibitionList = mutableListOf<MyExhibition>()
        val cursor = database.rawQuery("SELECT * FROM $TABLE_NAME", null)
        cursor?.use {
            while (it.moveToNext()) {
                val id = it.getLong(it.getColumnIndexOrThrow("id"))
                val exhibitionTl = it.getString(it.getColumnIndexOrThrow("exhibitionTl")) ?: ""
                val exhibitionImg = it.getString(it.getColumnIndexOrThrow("exhibitionImg")) ?: ""
                val exhibitionDate = it.getString(it.getColumnIndexOrThrow("exhibitionDate")) ?: ""
                val exhibitionPlace = it.getString(it.getColumnIndexOrThrow("exhibitionPlace")) ?: ""
                val exhibitionMap = it.getString(it.getColumnIndexOrThrow("exhibitionMap")) ?: ""
                val exhibitionPrice = it.getString(it.getColumnIndexOrThrow("exhibitionPrice")) ?: ""
                val exhibitionView = it.getString(it.getColumnIndexOrThrow("exhibitionView")) ?: ""
                val user = MyExhibition(id, exhibitionTl, exhibitionImg, exhibitionDate, exhibitionPlace, exhibitionMap, exhibitionPrice, exhibitionView)
                exhibitionList.add(user)
            }
        }
        return exhibitionList
    }

    fun insertExhibition(exhibition: MyExhibition) {
        val values = ContentValues().apply {
            put("exhibitionTl", exhibition.exhibitionTl)
            put("exhibitionImg", exhibition.exhibitionImg)
            put("exhibitionDate", exhibition.exhibitionDate)
            put("exhibitionPlace", exhibition.exhibitionPlace)
            put("exhibitionMap", exhibition.exhibitionMap)
            put("exhibitionPrice", exhibition.exhibitionPrice)
            put("exhibitionView", exhibition.exhibitionView)
        }
        database.insert(TABLE_NAME, null, values)
    }

    fun deleteUser(exhibition: MyExhibition) {
        val selection = "id = ?"
        val selectionArgs = arrayOf(exhibition.id.toString())
        database.delete(TABLE_NAME, selection, selectionArgs)
    }

    fun closeDatabase() {
        database.close()
    }

}