package com.riteh.autoshare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.riteh.autoshare.R
import kotlinx.android.synthetic.main.brand_model_layout.view.*

class BrandListAdapter(
    private var items: List<String>,
    val context: Context
) :
    RecyclerView.Adapter<BrandListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.brand_model_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        // return vehicles.size
        return 10
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // dummy values
        holder.title.text = "brand name"
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.tv_title

        init {
            itemView.setOnClickListener { view ->
                view.findNavController().navigate(R.id.action_brandFragment_to_modelFragment)
            }
        }
    }
}