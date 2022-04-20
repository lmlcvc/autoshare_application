package com.riteh.autoshare.ui.home.info

import android.os.Bundle
import android.util.Log
import android.widget.ExpandableListView
import androidx.appcompat.app.AppCompatActivity
import com.riteh.autoshare.R
import com.riteh.autoshare.adapters.FuelPricesExpandableListAdapter
import com.riteh.autoshare.data.dataholders.FuelPriceHolder
import com.riteh.autoshare.util.FuelPriceFetcher
import kotlinx.android.synthetic.main.activity_fuel_prices.*
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class FuelPricesActivity : AppCompatActivity(), FuelPriceFetcher {
    private lateinit var expandableListView: ExpandableListView
    private lateinit var expandableListAdapter: FuelPricesExpandableListAdapter

    private lateinit var responseItems: List<FuelPriceHolder>
    private var expandableListItems = mutableMapOf<String, MutableList<Pair<String, String>>>()


    // Webpage from which the fuel prices are scraped
    private val fuelPriceSource = "https://cijenegoriva.info/CijeneGoriva.aspx"

    // Ids of html tables containing fuel price info
    private val fuelIds = listOf(
        "MainContent_gwEuro95",
        "MainContent_gwEurodizel",
        "MainContent_gwEuro100",
        "MainContent_gwPlaviDizel",
        "MainContent_gwAutoplin"
    )

    // Types of fuel to be stored
    private val fuelTypes = listOf(
        "Eurosuper 95",
        "Eurodizel",
        "Eurosuper 100",
        "Plavi dizel",
        "LPG",
        "Eurosuper 95+",
        "Eurodizel+"
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fuel_prices)

        setUpExpandableListView(this)
        setUpListeners()
    }

    private fun setUpListeners() {
        iv_back.setOnClickListener {
            this.finish()
        }
    }

    private fun setUpExpandableListView(fuelPricesActivity: FuelPricesActivity) = runBlocking {
        expandableListView = elv_fuels as ExpandableListView


        async {
            responseItems = getFuelPrices(
                fuelPriceSource, fuelIds, fuelTypes
            )

            responseItems.forEach {
                // expandableListItems[it.type]?.plusAssign(((Pair(it.location, it.price))))
                expandableListItems.getOrPut(it.type) { mutableListOf() }.add(Pair(it.location, it.price))
            }

            fuelPricesActivity.expandableListAdapter =
                FuelPricesExpandableListAdapter(
                    fuelPricesActivity, expandableListItems
                )
            fuelPricesActivity.expandableListView.setAdapter(fuelPricesActivity.expandableListAdapter)


        }
    }

}