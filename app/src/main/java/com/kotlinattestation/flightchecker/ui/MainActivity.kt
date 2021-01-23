package com.kotlinattestation.flightchecker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlinattestation.flightchecker.db.DatabaseInserter
import com.kotlinattestation.flightchecker.db.DatabaseSearcher
import com.kotlinattestation.flightchecker.models.Airports
import com.kotlinattestation.flightchecker.ui.FlightsActivity


class MainActivity : Activity() {
    private val TAG = "Test"
    private var arrayFilterList  = ArrayList<Airports>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        runStartup()
    }


    fun runStartup(){
       var airportsList = DatabaseInserter(this).getAirportsData(this)
        if(airportsList.isEmpty()){
            btnSearch.setEnabled(false)
            loadingText.setText("First time Database insertion..Please Wait..App will close when finished and you can restart the app")
            DatabaseInserter(this).storeAirportsData()
        }
        else{
            recyclerView.adapter = AirportsAdapter(airportsList)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.setHasFixedSize(true)
        }

    }





    fun searchAirlines(view: View?){
        var data = etAirport.text.toString().trim()

        Log.d("text", data.count().toString())
        if(data.isNotEmpty()){
            if(DatabaseSearcher(this).searchAirportId(data).isNotEmpty()){
                val intent = Intent(this, FlightsActivity::class.java)
                intent.putExtra("airport", data)
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "Airport not found, please include 'Airport'. You can use the below Airports as reference.", Toast.LENGTH_LONG).show()
            }

        }
        else{
            Toast.makeText(this, "Nothing has been entered", Toast.LENGTH_LONG).show()
        }

    }


    fun triggerRestart(context: Activity) {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        if (context is Activity) {
            (context as Activity).finish()
        }
        Runtime.getRuntime().exit(0)
    }



}