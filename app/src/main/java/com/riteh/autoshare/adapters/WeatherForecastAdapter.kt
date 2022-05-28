package com.riteh.autoshare.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.riteh.autoshare.R
import com.riteh.autoshare.responses.weather.forecast.Daily
import kotlinx.android.synthetic.main.weather_days_layout.view.*
import java.time.ZonedDateTime
import java.util.*
import kotlin.math.roundToInt

class WeatherForecastAdapter(private var forecast: List<Daily>, val context: Context) :
    RecyclerView.Adapter<WeatherForecastAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.weather_days_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return forecast.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        /*
        holder.day.text = "Mon"
        holder.date.text = "Apr 18"
        holder.max.text = "20°C"
        holder.min.text = "22°C"
         */


        holder.day.text = "Mon"
        holder.date.text = "Apr 18"
        holder.max.text = forecast[position].temp.max.roundToInt().toString() + "°C"
        holder.min.text = forecast[position].temp.min.roundToInt().toString() + "°C"

        val icon: String = forecast[position].weather.get(0).icon
        val iconUrl = "http://openweathermap.org/img/w/$icon.png"
        Glide.with(context)
            .load(iconUrl)
            .into(holder.icon)

    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val day: TextView = itemView.tv_day
        val date: TextView = itemView.tv_date
        val icon: ImageView = itemView.iv_image
        val max: TextView = itemView.tv_max_temp
        val min: TextView = itemView.tv_min_temp

        init { }
    }

    private fun getDate(time_stamp_server: Int): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = (time_stamp_server * 1000).toLong()

        val year = calendar[Calendar.YEAR].toString()
        val month = calendar[Calendar.MONTH].toString()
        val day = calendar[Calendar.DAY_OF_MONTH].toString()


        return day
    }
}