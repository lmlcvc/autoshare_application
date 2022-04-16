package com.riteh.autoshare.util

import com.riteh.autoshare.data.dataholders.FuelPriceHolder
import org.jsoup.Jsoup

class FuelPriceFetcher {
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

    // Function that scrapes fuel prices from fuelPriceSource
    fun getFuelPrices(): List<FuelPriceHolder> {
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
                    data[0].contains("class")) {
                    fuelPriceList.add(FuelPriceHolder(fuelTypes[i + 5], data[1], data[2]))
                } else {
                    fuelPriceList.add(FuelPriceHolder(fuelTypes[i], data[1], data[2]))
                }
            }
        }

        return fuelPriceList
    }
}