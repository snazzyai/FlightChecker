package com.kotlinattestation.flightchecker.db

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.util.Log
import com.kotlinattestation.flightchecker.MainActivity
import com.kotlinattestation.flightchecker.models.Airports
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import java.net.URL


open class DatabaseInserter(var ctx: MainActivity): Activity(){
    private val urlAirport = "https://raw.githubusercontent.com/jpatokal/openflights/master/data/airports.dat"
    private val urlAirline = "https://raw.githubusercontent.com/jpatokal/openflights/master/data/airlines.dat"
    private val urlRoutes = "https://raw.githubusercontent.com/jpatokal/openflights/master/data/routes.dat"
    private var TAG = "airports"


    fun storeAirportsData(){
        Log.d(TAG,"now in coroutine")
        CoroutineScope(IO).launch(Dispatchers.Default){
           async {
               getAirportsDataFromApi()
           }.await()
        }
    }




    //get airports data and insert into airports table
    fun getAirportsDataFromApi() {

        Log.d(TAG, "now inserting airports")
        val result = URL(urlAirport).readText()
        val final = result.lines()
        for(i in final){
            if(i.isNotEmpty()){
                val splitted = i.split(",")
                insertAirports(splitted)
            }
        }
        Log.d(TAG, "Finished Inserting")
        getAirlinesDataFromApi()
    }


    //   insertAirports to airports table
    private fun insertAirports(splitted: List<String>){
        val helper = DatabaseHelper(ctx)
        val db = helper.writableDatabase
        val cv = ContentValues()
        cv.put("AIRPORTID", splitted[0])
        cv.put("NAME", splitted[1])
        cv.put("CITY", splitted[2])
        cv.put("COUNTRY", splitted[3])
        cv.put("IATA", splitted[4])
        cv.put("ICAO", splitted[5])
        cv.put("LATITUDE", splitted[6])
        cv.put("LONGITUDE", splitted[7])
        cv.put("ALTITUDE", splitted[8])
        cv.put("TIMEZONE", splitted[9])
        cv.put("DST", splitted[10])
        cv.put("DTZ", splitted[11])
        cv.put("TYPE", splitted[12])
        cv.put("SOURCE", splitted[13])
        db?.insert("AIRPORTS", null, cv)
        db?.close()
    }



    private fun getAirlinesDataFromApi() {
        Log.d(TAG, "now getting from api")
        val result = URL(urlAirline).readText()
        val final = result.lines()
        for(i in final){
            if(i.isNotEmpty()){
                val toSplit = i.split(",")
                insertAirlines(toSplit)
            }
        }
        Log.d(TAG, "finished inserting airlines data!! success")
        getRoutesFromApi()
    }

    private fun insertAirlines(splitted: List<String>){
        val helper = DatabaseHelper(ctx)
        val db = helper.writableDatabase
        val cv = ContentValues()
        cv.put("AIRLINEID", splitted[0])
        cv.put("NAME", splitted[1])
        cv.put("ALIAS", splitted[2])
        cv.put("IATA", splitted[3])
        cv.put("ICAO", splitted[4])
        cv.put("CALLSIGN", splitted[5])
        cv.put("COUNTRY", splitted[6])
        cv.put("ACTIVE", splitted[7])
        db?.insert("AIRLINES", null, cv)
        db?.close()
    }



    private fun getRoutesFromApi() {
       Log.d("routes", "now inserting routes")
        val result = URL(urlRoutes).readText()
        val final = result.lines()
        for(i in final){
            if(i.isNotEmpty()){
                val toSplit = i.split(",")
                insertRoutes(toSplit)
            }
        }
        Log.d(TAG, "finished inserting routes data!! success")
        ctx.triggerRestart(ctx)
    }

    private fun insertRoutes(splitted: List<String>) {
        val helper = DatabaseHelper(ctx)
        val db = helper.writableDatabase
        val cv = ContentValues()
        cv.put("AIRLINE", splitted[0])
        cv.put("AIRLINEID", splitted[1])
        cv.put("SOURCEAIRPORTCODE", splitted[2])
        cv.put("SOURCEAIRPORTID", splitted[3])
        cv.put("DESTINATIONAIRPORTCODE", splitted[4])
        cv.put("DESTINATIONAIRPORTID", splitted[5])
        cv.put("CODESHARE", splitted[6])
        cv.put("STOPS", splitted[7])
        cv.put("EQUIPMENT", splitted[7])
        db?.insert("ROUTES", null, cv)
        db?.close()
    }



    fun getAirportsData(ctx: Context): ArrayList<Airports>{
        val query = "SELECT NAME,COUNTRY FROM AIRPORTS"
        val db = DatabaseHelper(ctx).readableDatabase
        val cursor = db.rawQuery(query, null)
        val airports = ArrayList<Airports>()
        if(cursor.count == 0){
            Log.d("records", "No records found")
        }else{
            while(cursor.moveToNext()){
                var airport = Airports()
                airport.airportName = cursor.getString(cursor.getColumnIndex("NAME"))
                airport.airportCountry = cursor.getString(cursor.getColumnIndex("COUNTRY"))
                airports.add(airport)
            }
        }
        cursor.close()
        db?.close()

        return airports
    }

    }
