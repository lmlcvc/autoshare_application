package com.riteh.autoshare.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.riteh.autoshare.R
import com.riteh.autoshare.data.dataholders.Vehicle
import com.riteh.autoshare.ui.home.add.AddViewModel
import kotlinx.android.synthetic.main.user_vehicle_items_layout.view.*

class VehiclesForRentListAdapter(
    private var vehicles: List<Vehicle>,
    val context: Context,
    val sharedViewModel: AddViewModel
) :
    RecyclerView.Adapter<VehiclesForRentListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.vehicle_rent_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        // return vehicles.size
        return 7
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // holder.name.text = vehicles[position].brand + " " + vehicles[position].model

        holder.name.text = "tvoja stara"
        /*Glide.with(holder.icon)
            .load(products[position].image)
            .into(holder.icon)*/
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.tv_title
        val image: ImageView = itemView.iv_image

        init {
            itemView.setOnClickListener { view ->
                // sharedViewModel.setBrand(title.text.toString())

                view.findNavController().navigate(R.id.action_vehicleSelectFragment_to_priceFragment)
            }
        }
    }
}