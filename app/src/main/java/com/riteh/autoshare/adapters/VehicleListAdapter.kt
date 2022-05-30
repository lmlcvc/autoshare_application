package com.riteh.autoshare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.riteh.autoshare.R
import com.riteh.autoshare.data.dataholders.VehicleListItem
import kotlinx.android.synthetic.main.activity_vehicle_list.view.tv_title
import kotlinx.android.synthetic.main.user_vehicle_items_layout.view.*

class VehicleListAdapter(private var vehicles: List<VehicleListItem>, val context: Context) :
    RecyclerView.Adapter<VehicleListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.user_vehicle_items_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        // return vehicles.size
        return 7
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // dummy values
        holder.name.text = "vehicle name"
        holder.year.text = "200 kn" // TODO: specifying currencies

        /*Glide.with(holder.icon)
            .load(products[position].image)
            .into(holder.icon)*/
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.tv_title
        val year: TextView = itemView.tv_price
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