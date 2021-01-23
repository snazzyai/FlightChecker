package com.kotlinattestation.flightchecker.ui
//package com.kotlinattestation.flightchecker


import com.kotlinattestation.flightchecker.R
import com.kotlinattestation.flightchecker.models.Routes
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlinattestation.flightchecker.MapsActivity
import kotlinx.android.synthetic.main.layout_flights_list.view.*


class RoutesAdapter(var context: Context, private val routesData: List<Routes>): RecyclerView.Adapter<RoutesAdapter.RoutesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutesViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_flights_list, parent, false)
        return RoutesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RoutesViewHolder, position: Int) {
            val currentItem = routesData[position]

            holder.textViewAirlineName.setText(currentItem.airlineName?.replace("\"",""))
            holder.textViewSourceAirport.setText(currentItem.sourceAirport?.replace("\"",""))
            holder.textViewDestinationAirport.setText(currentItem.destinationAirport?.replace("\"",""))
            holder.textViewSourceCountry.setText(currentItem.sourceCountry?.replace("\"",""))
            holder.textViewDestinationCountry.setText(currentItem.destinationCountry?.replace("\"",""))
            holder.textViewDestinationLatitude.setText("Lat:" + currentItem.destinationLatitude?.replace("\"",""))
            holder.textViewDestinationLongitude.setText("Long: "+ currentItem.destinationLongitude?.replace("\"",""))
            holder.textViewSourceLatitude.setText("Lat: "+ currentItem.sourceLatitude?.replace("\"",""))
            holder.textViewSourceLongitude.setText("Long: "+ currentItem.sourceLongitude?.replace("\"",""))

            holder.linearLayoutCardLayout.setOnClickListener {
                val intent = Intent(context, MapsActivity::class.java)
                intent.putExtra("sourceLatitude", currentItem.sourceLatitude.toString())
                intent.putExtra("sourceLongitude", currentItem.sourceLongitude.toString())
                intent.putExtra("destinationLatitude", currentItem.destinationLatitude.toString())
                intent.putExtra("destinationLongitude", currentItem.destinationLongitude.toString())
                intent.putExtra("sourceAirport", currentItem.sourceAirport)
                intent.putExtra("destinationAirport", currentItem.destinationAirport)
                context.startActivity(intent)
        }


    }


    override fun getItemCount() = routesData.size

    class RoutesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val textViewAirlineName: TextView = itemView.airlineName
        val textViewSourceAirport: TextView = itemView.sourceAirport
        val textViewDestinationAirport: TextView = itemView.destinationAirport
        val textViewSourceCountry: TextView = itemView.sourceCountry
        val textViewDestinationCountry: TextView = itemView.destinationCountry
        val textViewSourceLatitude:TextView = itemView.sourceLatitude
        val textViewSourceLongitude:TextView = itemView.sourceLongitude
        val textViewDestinationLatitude:TextView = itemView.destinationLatitude
        val textViewDestinationLongitude:TextView = itemView.destinationLongitude
        val linearLayoutCardLayout: LinearLayout = itemView.cardLayout

    }


}