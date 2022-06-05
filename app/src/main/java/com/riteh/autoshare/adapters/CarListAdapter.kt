package com.riteh.autoshare.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.riteh.autoshare.R
import com.riteh.autoshare.data.dataholders.Vehicle
import com.riteh.autoshare.ui.home.search.VehicleDetailsRentingActivity
import com.riteh.autoshare.ui.home.search.VehicleRentViewModel
import kotlinx.android.synthetic.main.car_layout.view.*


class CarListAdapter(private var cars: List<Vehicle>, val context: Context, val viewModelVehicle: VehicleRentViewModel) :
    RecyclerView.Adapter<CarListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.car_layout, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return cars.size
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.carName.text = cars[position].brand
        holder.carPrice.text = cars[position].rent_cost + " / day"
        holder.carStar.text = cars[position].rating_avg

        //image
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val carPrice: TextView = itemView.car_price
        val carName: TextView = itemView.car_name
        val carStar: TextView = itemView.car_star
        val carPicture: ImageView = itemView.picture_car

        init {
            itemView.setOnClickListener {
                viewModelVehicle.setVehicle(cars[layoutPosition])
                val intent = Intent(context, VehicleDetailsRentingActivity::class.java)
                context.startActivity(intent)
            }
        }
    }
}