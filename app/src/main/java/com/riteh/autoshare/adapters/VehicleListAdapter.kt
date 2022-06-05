package com.riteh.autoshare.adapters

import android.annotation.SuppressLint
import android.content.Context
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
import kotlinx.android.synthetic.main.activity_vehicles_list.view.tv_title
import kotlinx.android.synthetic.main.user_vehicle_items_layout.view.*

class VehicleListAdapter(private var vehicles: List<Vehicle>, val context: Context) :
    RecyclerView.Adapter<VehicleListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_vehicle_items_layout, parent, false)
        return ViewHolder(v)
    }


    override fun getItemCount(): Int {
        return vehicles.size
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = vehicles[position].brand + " " + vehicles[position].model

        if(!vehicles[position].image.isNullOrEmpty()) {
            Glide.with(holder.image)
                .load(getBitmapFromBase64(vehicles[position].image))
                .into(holder.image)
        }
    }


    private fun getBitmapFromBase64(encodedImage: String): Bitmap {
        val decodedString: ByteArray = Base64.decode(encodedImage, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.tv_title
        val image: ImageView = itemView.iv_image

        /*init {
            itemView.setOnClickListener {
                val intent = Intent(context, ProductDetailActivity::class.java)
                intent.putExtra("EXTRA_PRODUCT", products[layoutPosition] as Serializable)
                context.startActivity(intent)
            }
        }*/
    }
}