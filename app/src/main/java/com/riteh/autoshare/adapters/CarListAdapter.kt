package com.riteh.autoshare.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
        holder.carName.text = cars[position].brand + " " + cars[position].model
        holder.carPrice.text = cars[position].rent_cost + " / day"
        holder.carStar.text = cars[position].rating_avg

        if(!cars[position].image.isNullOrEmpty()) {
            Glide.with(holder.carPicture)
                .load(getBitmapFromBase64(cars[position].image))
                .into(holder.carPicture)
        }
    }


    private fun getBitmapFromBase64(encodedImage: String): Bitmap {
        val decodedString: ByteArray = Base64.decode(encodedImage, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
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