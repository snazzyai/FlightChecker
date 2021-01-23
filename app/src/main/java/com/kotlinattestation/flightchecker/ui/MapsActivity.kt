package com.kotlinattestation.flightchecker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var mMap2: GoogleMap
    private var sourceLatitude = 0.0
    private var sourceLongitude = 0.0
    private var destinationLatitude = 0.0
    private var destinationLongitude = 0.0
    private var sourceAirport = ""
    private var destinationAirport = ""




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sourceLatitude = intent.getStringExtra("sourceLatitude").toDouble()
        sourceLongitude = intent.getStringExtra("sourceLongitude").toDouble()
        destinationLatitude = intent.getStringExtra("destinationLatitude").toDouble()
        destinationLongitude = intent.getStringExtra("destinationLongitude").toDouble()
        sourceAirport = intent.getStringExtra("sourceAirport")
        destinationAirport = intent.getStringExtra("destinationAirport")

        Log.d("map", sourceAirport)
        Log.d("map",destinationAirport)



        setContentView(R.layout.activity_maps_layout)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap
        mMap2 = googleMap

        if((destinationLatitude == 0.0 && destinationLongitude == 0.0) || (sourceLatitude == 0.0 && sourceLongitude == 0.0)){
            Toast.makeText(this, "Invalid latitude and longitude", Toast.LENGTH_LONG).show()
        }else{
            val source = LatLng(sourceLatitude, sourceLongitude)
            mMap2.addMarker(MarkerOptions().position(source).title(sourceAirport))
            mMap2.moveCamera(CameraUpdateFactory.newLatLng(source))
            val destination = LatLng(destinationLatitude, destinationLongitude)
            mMap.addMarker(MarkerOptions().position(destination).title(destinationAirport))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(destination))

        }
    }
}