package com.riteh.autoshare.ui.home.info

import android.os.Bundle
import android.widget.ExpandableListView
import androidx.appcompat.app.AppCompatActivity
import com.riteh.autoshare.R
import com.riteh.autoshare.adapters.FuelPricesExpandableListAdapter
import com.riteh.autoshare.ui.home.info.ExpandableListDataPump.data
import kotlinx.android.synthetic.main.activity_fuel_prices.*

class FuelPricesActivity : AppCompatActivity() {
    private lateinit var expandableListView: ExpandableListView
    private lateinit var expandableListAdapter: FuelPricesExpandableListAdapter
    private lateinit var expandableListTitle: List<String>
    private lateinit var expandableListDetail: HashMap<String, List<String>>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fuel_prices)

        setUpExpandableListView()
        setUpListeners()
    }

    private fun setUpExpandableListView() {
        expandableListView = elv_fuels as ExpandableListView
        expandableListDetail = data
        expandableListTitle = ArrayList(expandableListDetail.keys)
        expandableListAdapter =
            FuelPricesExpandableListAdapter(
                this,
                expandableListTitle as ArrayList<String>, expandableListDetail
            )
        expandableListView.setAdapter(expandableListAdapter)
    }

    private fun setUpListeners() {
        iv_back.setOnClickListener {
            this.finish()
        }
    }
}