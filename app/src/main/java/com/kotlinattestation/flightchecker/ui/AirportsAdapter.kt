package com.kotlinattestation.flightchecker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlinattestation.flightchecker.models.Airports
import kotlinx.android.synthetic.main.layout_airports_list.view.*


class AirportsAdapter(private val airportsData: List<Airports>): RecyclerView.Adapter<AirportsAdapter.AirportsViewHolder>() {

    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AirportsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_airports_list, parent, false)

        return AirportsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AirportsViewHolder, position: Int) {
        val currentItem = airportsData[position]
        holder.textViewName.setText(currentItem.airportName?.replace("\"",""))
        holder.textViewCountry.setText(currentItem.airportCountry?.replace("\"",""))
    }

    override fun getItemCount() = airportsData.size

    class AirportsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val textViewName: TextView = itemView.airportName
        val textViewCountry: TextView = itemView.airportCountry

    }


}