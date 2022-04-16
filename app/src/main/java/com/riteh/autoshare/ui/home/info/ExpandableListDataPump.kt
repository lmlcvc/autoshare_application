package com.riteh.autoshare.ui.home.info

// TODO: temporary class to fill the expandable list view; delete later and replace with data class
object ExpandableListDataPump {
    val data: HashMap<String, List<String>>
        get() {
            val expandableListDetail = HashMap<String, List<String>>()
            val cricket: MutableList<String> = ArrayList()
            cricket.add("Mitea")
            cricket.add("Tifon")
            cricket.add("Petrol")
            cricket.add("Lukoil")

            val football: MutableList<String> = ArrayList()
            football.add("Mitea")
            football.add("Tifon")
            football.add("Petrol")
            football.add("Lukoil")

            val basketball: MutableList<String> = ArrayList()
            basketball.add("Mitea")
            basketball.add("Tifon")
            basketball.add("Petrol")
            basketball.add("Lukoil")

            expandableListDetail["Eurosuper95"] = cricket
            expandableListDetail["Eurosuper 100"] = football
            expandableListDetail["Eurodizel"] = basketball

            return expandableListDetail
        }
}