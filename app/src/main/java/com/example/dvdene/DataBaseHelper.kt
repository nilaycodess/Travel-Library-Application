package com.example.dvdene

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

const val database_name = "PlacesDatabase"
const val table_name = "places"
const val col_id = "id"
const val col_yeradi="yeradi"
const val col_image = "image"
const val col_aciklama="aciklama"

class DatabaseHelper (var context:Context):SQLiteOpenHelper(context, database_name,null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        //Veri tabani olustugunda birkez calisir
        val createTable = """
            CREATE TABLE $table_name (
                $col_id INTEGER PRIMARY KEY AUTOINCREMENT,
                $col_yeradi TEXT,
                $col_image BLOB,
                $col_aciklama TEXT
            )
        """
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        //Veri tabani yükseltmek için bir kez kullanilir
    }

    fun insertPlace(name: String, image: ByteArray, aciklama: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(col_yeradi, name)
            put(col_image, image)
            put(col_aciklama, aciklama)
        }
        return db.insert(table_name, null, values)
    }
    fun getAllPlaces(): List<Bilgiler> {
        val placesList = mutableListOf<Bilgiler>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $table_name", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(col_id))
                val yeradi = cursor.getString(cursor.getColumnIndexOrThrow(col_yeradi))
                val image = cursor.getBlob(cursor.getColumnIndexOrThrow(col_image))
                val aciklama = cursor.getString(cursor.getColumnIndexOrThrow(col_aciklama))
                placesList.add(Bilgiler(image, aciklama, yeradi).apply { this.id = id })
            } while (cursor.moveToNext())
        }
        cursor.close()
        return placesList
    }
}