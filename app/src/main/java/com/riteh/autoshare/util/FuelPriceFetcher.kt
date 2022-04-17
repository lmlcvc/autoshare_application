package com.riteh.autoshare.util

import com.riteh.autoshare.data.dataholders.FuelPriceHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

interface FuelPriceFetcher {


    // Function that scrapes fuel prices from fuelPriceSource
    suspend fun getFuelPrices(
        fuelPriceSource: String,
        fuelIds: List<String>,
        fuelTypes: List<String>
    ): List<FuelPriceHolder> = withContext(Dispatchers.IO) {
        val doc = Jsoup.connect(fuelPriceSource).get()
        val fuelPriceList: MutableList<FuelPriceHolder> = mutableListOf()

        for (i in fuelIds.indices) {
            val table = doc.getElementById(fuelIds[i])
            val rows = table?.select("tr")

            if (rows.isNullOrEmpty()) continue

            for (j in 1 until rows.size) {
                val data = rows[j].select("td").eachText()

                if (data[0].contains("+") ||
                    data[0].contains("plus") ||
                    data[0].contains("max") ||
                    data[0].contains("class")
                ) {
                    fuelPriceList.add(FuelPriceHolder(fuelTypes[i + 5], data[1], data[2]))
                } else {
                    fuelPriceList.add(FuelPriceHolder(fuelTypes[i], data[1], data[2]))
                }
            }
        }

        return@withContext fuelPriceList
    }
}