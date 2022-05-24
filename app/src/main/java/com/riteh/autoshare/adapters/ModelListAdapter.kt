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
import com.riteh.autoshare.data.dataholders.ModelInfo
import com.riteh.autoshare.ui.home.user.vehicles.VehicleInfoViewModel
import kotlinx.android.synthetic.main.brand_model_layout.view.*

class ModelListAdapter(
    items: List<ModelInfo>,
    val context: Context,
    val sharedViewModel: VehicleInfoViewModel
) :
    RecyclerView.Adapter<ModelListAdapter.ViewHolder>() {

    var models = items

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.brand_model_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return models.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = models[position].Model_Name
    }

    fun setItems(models: MutableList<ModelInfo>) {
        this.models = models
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.tv_title

        init {
            itemView.setOnClickListener { view ->
                sharedViewModel.setModel(title.text.toString())
                Log.v("VM value", sharedViewModel.model.value.toString())

                view.findNavController().navigate(R.id.action_modelFragment_to_detailsFragment)
            }
        }
    }
}