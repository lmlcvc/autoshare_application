package com.riteh.autoshare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.riteh.autoshare.R


class FuelPricesExpandableListAdapter(
    private val context: Context, private val expandableListTitle: List<String>,
    private val expandableListDetail: HashMap<String, List<String>>
) : BaseExpandableListAdapter() {

    override fun getChild(listPosition: Int, expandedListPosition: Int): Any {
        return expandableListDetail[expandableListTitle[listPosition]]
            ?.get(expandedListPosition) ?: Int
    }

    override fun getChildId(listPosition: Int, expandedListPosition: Int): Long {
        return expandedListPosition.toLong()
    }

    /**
     * Initialise child views and place station names and prices.
     */
    override fun getChildView(
        listPosition: Int, expandedListPosition: Int,
        isLastChild: Boolean, view: View?, parent: ViewGroup
    ): View {
        var convertView = view

        val expandedListText = getChild(listPosition, expandedListPosition) as String

        if (convertView == null) {
            val layoutInflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.elv_item, null)
        }

        val expandedListStationName = convertView?.findViewById<View>(R.id.tv_station) as TextView
        val expandedListPrice = convertView?.findViewById<View>(R.id.tv_price) as TextView

        expandedListStationName.text = expandedListText

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
        return expandableListDetail[expandableListTitle[listPosition]]
            ?.size!!
    }

    override fun getGroup(listPosition: Int): Any {
        return expandableListTitle[listPosition]
    }

    override fun getGroupCount(): Int {
        return expandableListTitle.size
    }

    override fun getGroupId(listPosition: Int): Long {
        return listPosition.toLong()
    }


    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean {
        return true
    }
}