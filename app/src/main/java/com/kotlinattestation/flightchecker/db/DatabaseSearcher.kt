package com.kotlinattestation.flightchecker.db

import android.app.Activity
import android.content.Context
import android.database.SQLException
import android.util.Log
import com.kotlinattestation.flightchecker.models.Airports
import com.kotlinattestation.flightchecker.models.Routes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class DatabaseSearcher(private val ctx: Context): Activity() {
    private var TAG = "cursor"
    var flights = ArrayList<Routes>()


//
//    fun searchFlights(airportName: String){
//        CoroutineScope(Dispatchers.IO).launch(Dispatchers.Default){
//            async {
//                searchAirportId(airportName)
//            }.await()
//        }
//    }

    fun searchAirportId(airportName: String): String{
        val input = "\"" + "$airportName" + "\""
        var result = ""
        val db = DatabaseHelper(ctx).writableDatabase
        val selectQuery = "SELECT * FROM AIRPORTS WHERE NAME = ?"
        try{
            db.rawQuery(selectQuery, arrayOf(input)).use {
                if (it.moveToFirst()) {
                    result = it.getString(0)
                }else{
                    Log.d(TAG, "Does not exist")
                }
            }
        }catch (e: SQLException){
            Log.d("errordb", e.toString())
        }

        return result
    }



    fun getAirlineDetails(airlineId: String): String? {
        var result: String? = ""
        val db = DatabaseHelper(ctx).writableDatabase
        val selectQuery = "SELECT * FROM AIRLINES WHERE AIRLINEID = ?"
        try{
            db.rawQuery(selectQuery, arrayOf(airlineId)).use {
                if (it.moveToFirst()) {
                    result = it.getString(1)

                }else{
                    Log.d(TAG, "Does not exist")
                }
            }
        }catch (e: SQLException){
            Log.d("errordb", e.toString())
        }
        return result
    }


    fun getAirportDetails(airportId: String): String?{
        var result: String? = ""
        val db = DatabaseHelper(ctx).writableDatabase
        val selectQuery = "SELECT * FROM AIRPORTS WHERE AIRPORTID = ?"
        try{
            db.rawQuery(selectQuery, arrayOf(airportId)).use {
                if (it.moveToFirst()) {
                    result = it.getString(1)
                }else{
                    Log.d(TAG, "Does not exist")
                }
            }
        }catch (e: SQLException){
            Log.d("errordb", e.toString())
        }

        return result
    }

    fun getCountry(airportId: String): String? {
        var result: String? = ""
        val db = DatabaseHelper(ctx).writableDatabase
        val selectQuery = "SELECT * FROM AIRPORTS WHERE AIRPORTID = ?"
        try{
            db.rawQuery(selectQuery, arrayOf(airportId)).use {
                if (it.moveToFirst()) {
                    result = it.getString(3)
                }else{
                    Log.d(TAG, "Does not exist")
                }
            }
        }catch (e: SQLException){
            Log.d("errordb", e.toString())
        }

        return result
    }


    fun searchRoutesForAirport(destinationId: String): ArrayList<Routes>{
        val db = DatabaseHelper(ctx).writableDatabase
        val selectQuery = "SELECT * FROM ROUTES WHERE DESTINATIONAIRPORTID = ?"
        try{
            db.rawQuery(selectQuery, arrayOf(destinationId)).use {
                if (it.moveToNext()) {
                    while(it.moveToNext()){
                        val flightData = Routes()
                        val airlineId = it.getString(it.getColumnIndex("AIRLINEID"))
                        flightData.airlineName = getAirlineDetails(airlineId)


                        val sourceAirportId = it.getString(it.getColumnIndex("SOURCEAIRPORTID"))
                        flightData.sourceAirport = getAirportDetails(sourceAirportId)
                        flightData.sourceCountry = getCountry(sourceAirportId)


                        val destinationAirportId = it.getString(it.getColumnIndex("DESTINATIONAIRPORTID"))
                        flightData.destinationAirport = getAirportDetails(destinationAirportId)

                        flightData.destinationCountry = getCountry(destinationAirportId)

                        flights.add(flightData)
                    }

                }else{
                    Log.d(TAG, "Does not exist routes")
                }
            }
        }catch (e: SQLException){
            Log.d("errordb", e.toString())
        }
        return flights
    }





}