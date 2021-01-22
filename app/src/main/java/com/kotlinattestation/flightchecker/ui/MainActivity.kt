package com.kotlinattestation.flightchecker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.kotlinattestation.flightchecker.db.DatabaseHelper
import kotlinx.android.synthetic.main.activity_main.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlinattestation.flightchecker.db.DatabaseInserter
import com.kotlinattestation.flightchecker.db.DatabaseSearcher
import com.kotlinattestation.flightchecker.ui.FlightsActivity

import java.lang.Exception




class MainActivity : Activity() {
    private val TAG = "Test"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        getAllAirports()
        runStartup()

    }


    fun runStartup(){
        val airportsList = DatabaseInserter(this).getAirportsData(this)

        if(airportsList.isEmpty()){
            btnSearch.setEnabled(false)
            loadingText.setText("First time Database insertion..Please Wait..")
            DatabaseInserter(this).storeAirportsData()
        }
        else{
            recyclerView.adapter = AirportsAdapter(airportsList)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.setHasFixedSize(true)
        }

    }


    fun searchAirlines(view: View?){
        if(etAirport.text.toString().isNotEmpty()){
            val intent = Intent(this, FlightsActivity::class.java)
            val text = etAirport.text
            intent.putExtra("airport", text.toString())
            startActivity(intent)
        }
        else{
            Toast.makeText(this, "Nothing has been entered", Toast.LENGTH_LONG).show()
        }

    }



}