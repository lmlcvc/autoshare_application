package com.riteh.autoshare.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.riteh.autoshare.R
import com.riteh.autoshare.data.BrandInfo
import com.riteh.autoshare.ui.home.user.vehicles.VehicleInfoViewModel
import kotlinx.android.synthetic.main.brand_model_layout.view.*

class BrandListAdapter(
    items: List<BrandInfo>,
    val context: Context,
    val sharedViewModel: VehicleInfoViewModel
) :
    RecyclerView.Adapter<BrandListAdapter.ViewHolder>() {

    var brands = items

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.brand_model_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return brands.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = brands[position].Make_Name
    }

    fun setItems(brands: List<BrandInfo>) {
        this.brands = brands
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.tv_title

        init {
            itemView.setOnClickListener { view ->
                sharedViewModel.setBrand(title.text.toString())
                Log.v("shared view model", sharedViewModel.brand.value.toString())

                view.findNavController().navigate(R.id.action_brandFragment_to_modelFragment)
            }
        }
    }
}