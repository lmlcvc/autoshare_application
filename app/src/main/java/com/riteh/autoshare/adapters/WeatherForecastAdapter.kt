package com.riteh.autoshare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.riteh.autoshare.R
import com.riteh.autoshare.responses.weather.forecast.WeatherForecastItem
import kotlinx.android.synthetic.main.weather_days_layout.view.*

class WeatherForecastAdapter(private var forecast: List<WeatherForecastItem>, val context: Context) :
    RecyclerView.Adapter<WeatherForecastAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.weather_days_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return forecast.size
        // return 7
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // dummy values
        holder.day.text = "Mon"
        holder.date.text = "Apr 18"
        holder.max.text = "20°C"
        holder.min.text = "22°C"

        /*Glide.with(holder.icon)
            .load(products[position].image)
            .into(holder.icon)*/
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val day: TextView = itemView.tv_day
        val date: TextView = itemView.tv_date
        val icon: ImageView = itemView.iv_image
        val max: TextView = itemView.tv_max_temp
        val min: TextView = itemView.tv_min_temp

        /*init {
            itemView.setOnClickListener {
                val intent = Intent(context, ProductDetailActivity::class.java)
                intent.putExtra("EXTRA_PRODUCT", products[layoutPosition] as Serializable)
                context.startActivity(intent)
            }
        }*/
    }
}