package com.riteh.autoshare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import com.riteh.autoshare.R
import com.riteh.autoshare.ui.home.info.FuelPricesActivity


class FuelPricesExpandableListAdapter(
    private val context: FuelPricesActivity,
    private val expandableListItems: MutableMap<String, MutableList<Pair<String, String>>>
) : BaseExpandableListAdapter() {

    // map station names to logo resources
    private val stationNameResourceMatcher = mapOf(
        "AdriaOil" to "ic_adriaoil",
        "Crodux Derivati" to "ic_crodux",
        "Ferotom (Dugo Selo)" to "ic_ferotom",
        "INA" to "ic_ina",
        "Lukoil" to "ic_lukoil",
        "Mitea (Samobor)" to "ic_mitea",
        "Petrol" to "ic_petrol"
    )

    override fun getChild(listPosition: Int, expandedListPosition: Int): Pair<String, String> {
        return expandableListItems.getValue(expandableListItems.keys.elementAt(listPosition))
            .elementAt(expandedListPosition)
    }

    override fun getChildId(listPosition: Int, expandedListPosition: Int): Long {
        return expandedListPosition.toLong()
    }

    /**
     * Initialise child views and place station names and prices.
     * Put respective station logo to view.
     */
    override fun getChildView(
        listPosition: Int, expandedListPosition: Int,
        isLastChild: Boolean, view: View?, parent: ViewGroup
    ): View {
        var convertView = view
        val expandedListStation = getChild(listPosition, expandedListPosition).first
        val expandedListPrice = getChild(listPosition, expandedListPosition).second

        if (convertView == null) {
            val layoutInflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.elv_item, null)
        }

        val expandedListStationView = convertView?.findViewById<View>(R.id.tv_station) as TextView
        val expandedListPriceView = convertView.findViewById<View>(R.id.tv_price) as TextView
        val expandedListStationIcon = convertView.findViewById<View>(R.id.iv_logo) as ImageView

        expandedListStationView.text = expandedListStation
        expandedListPriceView.text = expandedListPrice

        try {
            val drawableResourceId: Int =
                context.resources.getIdentifier(
                    stationNameResourceMatcher.getValue(expandedListStation),
                    "mipmap",
                    context.packageName
                )
            expandedListStationIcon.setImageResource(drawableResourceId)
        } catch (e: NoSuchElementException) {

        }



        return convertView
    }

    /**
     * Initialise group views and their titles.
     */
    override fun getGroupView(
        listPosition: Int, isExpanded: Boolean,
        view: View?, parent: ViewGroup
    ): View {
        var convertView = view
        val listTitle = getGroup(listPosition) as String

        if (convertView == null) {
            val layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.elv_group, null)
        }

        val listFuelType = convertView
            ?.findViewById<View>(R.id.tv_title) as TextView

        listFuelType.text = listTitle

        return convertView
    }

    override fun getChildrenCount(listPosition: Int): Int {
        return expandableListItems.values.elementAt(listPosition).size
    }

    override fun getGroup(listPosition: Int): Any {
        return expandableListItems.keys.elementAt(listPosition)
    }

    override fun getGroupCount(): Int {
        return expandableListItems.keys.size
    }

    override fun getGroupId(listPosition: Int): Long {
        return listPosition.toLong()
    }


    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean {
        return false
    }
}