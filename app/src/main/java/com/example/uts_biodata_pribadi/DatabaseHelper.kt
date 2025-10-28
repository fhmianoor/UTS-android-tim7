package com.example.uts_biodata_pribadi

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "biodata.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE biodata (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nama TEXT,
                umur TEXT,
                alamat TEXT,
                pekerjaan TEXT
            )
            """
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS biodata")
        onCreate(db)
    }

    fun insertBiodata(biodata: Biodata) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("nama", biodata.nama)
            put("umur", biodata.umur)
            put("alamat", biodata.alamat)
            put("pekerjaan", biodata.pekerjaan)
        }
        db.insert("biodata", null, values)
        db.close()
    }

    fun updateBiodata(biodata: Biodata) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("nama", biodata.nama)
            put("umur", biodata.umur)
            put("alamat", biodata.alamat)
            put("pekerjaan", biodata.pekerjaan)
        }
        db.update("biodata", values, "id = ?", arrayOf(biodata.id.toString()))
        db.close()
    }

    fun deleteBiodata(id: Int) {
        val db = writableDatabase
        db.delete("biodata", "id = ?", arrayOf(id.toString()))
        db.close()
    }

    fun getAllBiodata(): List<Biodata> {
        val list = mutableListOf<Biodata>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM biodata", null)
        if (cursor.moveToFirst()) {
            do {
                list.add(
                    Biodata(
                        id = cursor.getInt(0),
                        nama = cursor.getString(1),
                        umur = cursor.getString(2),
                        alamat = cursor.getString(3),
                        pekerjaan = cursor.getString(4)
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return list
    }
}
