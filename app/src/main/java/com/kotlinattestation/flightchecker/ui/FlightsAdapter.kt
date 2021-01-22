//package com.kotlinattestation.flightchecker.ui
package com.kotlinattestation.flightchecker


import com.kotlinattestation.flightchecker.R
import com.kotlinattestation.flightchecker.models.Routes
import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlinattestation.flightchecker.db.DatabaseHelper
import com.kotlinattestation.flightchecker.models.Airports
import kotlinx.android.synthetic.main.layout_flights_list.view.*


class RoutesAdapter(private val routesData: List<Routes>): RecyclerView.Adapter<RoutesAdapter.RoutesViewHolder>() {

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
    }

    override fun getItemCount() = routesData.size

    class RoutesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val textViewAirlineName: TextView = itemView.airlineName
        val textViewSourceAirport: TextView = itemView.sourceAirport
        val textViewDestinationAirport: TextView = itemView.destinationAirport
        val textViewSourceCountry: TextView = itemView.sourceCountry
        val textViewDestinationCountry: TextView = itemView.destinationCountry

    }


}