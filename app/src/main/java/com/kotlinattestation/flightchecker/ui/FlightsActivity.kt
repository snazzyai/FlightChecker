package com.kotlinattestation.flightchecker.ui

import android.app.Activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlinattestation.flightchecker.R
import com.kotlinattestation.flightchecker.db.DatabaseSearcher
import com.kotlinattestation.flightchecker.models.Routes
import kotlinx.android.synthetic.main.flights_layout.*



class FlightsActivity : Activity(){
    var flightsDetails = ArrayList<Routes>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.flights_layout)

        var data = intent.getStringExtra("airport").toString()
        runFlightsGetter(data)

    }

    fun runFlightsGetter(data: String){
        val id = DatabaseSearcher(this).searchAirportId(data)

        if(id.isNotEmpty()){
            Log.d("idofflight",id)
            flightsDetails = DatabaseSearcher(this).searchRoutesForAirport(id)
            recyclerViewFlights.adapter = RoutesAdapter(this, flightsDetails)
            recyclerViewFlights.layoutManager = LinearLayoutManager(this)
            recyclerViewFlights.setHasFixedSize(true)
        }
        else{
            Toast.makeText(this, "Flight data empty..", Toast.LENGTH_LONG).show()
        }

    }

}