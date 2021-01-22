package com.kotlinattestation.flightchecker.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.kotlinattestation.flightchecker.models.Airports

const val DATABASE_NAME = "FlightDB"




class DatabaseHelper(var context:Context): SQLiteOpenHelper(context, DATABASE_NAME,null,1 ) {

    companion object {
        val id = "_id"
        val name = "NAME"
        val country = "COUNTRY"
    }


    override fun onCreate(db: SQLiteDatabase?) {
//
//            db?.execSQL("DROP TABLE AIRPORTS")
        db?.execSQL("CREATE TABLE AIRPORTS(AIRPORTID VARCHAR(50) PRIMARY KEY,NAME VARCHAR(100), CITY VARCHAR(100), COUNTRY VARCHAR(100), IATA VARCHAR(10), ICAO VARCHAR(10), LATITUDE TEXT, LONGITUDE TEXT, ALTITUDE VARCHAR(100), TIMEZONE VARCHAR(100), DST VARCHAR(100), DTZ VARCHAR(100), TYPE VARCHAR(100), SOURCE VARCHAR(100))")
        db?.execSQL("CREATE TABLE AIRLINES(AIRLINEID VARCHAR(50) PRIMARY KEY,NAME VARCHAR(100),ALIAS VARCHAR(100), IATA VARCHAR(10), ICAO VARCHAR(10),CALLSIGN VARCHAR(100), COUNTRY VARCHAR(100), ACTIVE VARCHAR(5))")
        db?.execSQL("CREATE TABLE ROUTES(AIRLINE VARCHAR(100), AIRLINEID VARCHAR(100), SOURCEAIRPORTCODE VARCHAR(100), SOURCEAIRPORTID INTEGER, DESTINATIONAIRPORTCODE VARCHAR(100), DESTINATIONAIRPORTID VARCHAR(100), CODESHARE VARCHAR(10), STOPS VARCHAR(100), EQUIPMENT VARCHAR(100))")
    }

//    private fun createDemoTable(db: SQLiteDatabase?) {
//
//    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            onCreate(db)
    }

}