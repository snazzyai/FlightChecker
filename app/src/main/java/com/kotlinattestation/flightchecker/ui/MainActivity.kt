package com.kotlinattestation.flightchecker

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import data.RequestApi
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.lang.Exception


class MainActivity : AppCompatActivity() {
    var url = "https://raw.githubusercontent.com/jpatokal/openflights/master/data/airports.dat"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getAllAirports()
        getAllAirlines()
        getAllRoutes()
    }

    private fun getAllAirlines() {
        TODO("Not yet implemented")
    }

    private fun getAllRoutes() {
        TODO("Not yet implemented")
    }


    fun getAllAirports(){
        var helper = DatabaseHelper(applicationContext)
        var db = helper.readableDatabase
        try{
            var rq = db.rawQuery("SELECT * FROM AIRPORTS",null)
            Log.d("response", rq.toString())
            if(rq.moveToNext()){
                Toast.makeText(applicationContext,rq.getString(1), Toast.LENGTH_SHORT).show()
            }
            else{

                Toast.makeText(applicationContext,"no data, will now fetch from api", Toast.LENGTH_SHORT).show()
                getAndStoreAirport()

            }
        }
        catch(e: Exception){
            Toast.makeText(applicationContext,"not found in db", Toast.LENGTH_SHORT).show()
        }

    }

//coroutine for api call async
    fun getAndStoreAirport(){
        try{
            CoroutineScope(IO).launch {
                val result = async {
                    storeAirportsData()
                }.await()
            }
        }
        catch (e: Exception) {
            Log.e("Request", "Error ", e);
        }
    }

//   insertAirports to airports table
    fun insertAirports(splitted: List<String>){
        var helper = DatabaseHelper(applicationContext)
        var db = helper.writableDatabase
        var cv = ContentValues()
        cv.put("AIRPORTID", splitted[0].toInt())
        cv.put("NAME", splitted[1].toString())
        cv.put("CITY", splitted[2].toString())
        cv.put("COUNTRY", splitted[3].toString())
        cv.put("IATA", splitted[4].toString())
        cv.put("ICAO", splitted[5].toString())
        cv.put("LATITUDE", splitted[6].toString())
        cv.put("LONGITUDE", splitted[7].toString())
        cv.put("ALTITUDE", splitted[8].toString())
        cv.put("TIMEZONE", splitted[9].toString())
        cv.put("DST", splitted[10].toString())
        cv.put("DTZ", splitted[11].toString())
        cv.put("TYPE", splitted[12].toString())
        cv.put("SOURCE", splitted[13].toString())
        db?.insert("AIRPORTS",null, cv)
        db?.close()
//    Toast.makeText(applicationContext,"finished inserting data!", Toast.LENGTH_SHORT).show()

    }

    //get airports data and insert into airports table
    suspend fun storeAirportsData(){
        var request = RequestApi(url)
        var result = request.makeRequest()
        if(result !== null){
            var final = result.toString().lines()
            Log.d("dump", final[0])

            for(i in final){
                var splitted = i.split(",")
                insertAirports(splitted)
            }
            getAllAirports()
        }
        else{
            throw CancellationException("Api not gotten!")
        }
    }


    fun searchAirlines(){


    }



}