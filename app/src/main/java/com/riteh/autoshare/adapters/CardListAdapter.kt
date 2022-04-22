package com.riteh.autoshare.adapters

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.riteh.autoshare.R
import com.riteh.autoshare.ui.user.CardNumbersFormat
import com.riteh.autoshare.ui.user.UserCard
import com.riteh.autoshare.ui.user.UserCardInfoActivity
import kotlinx.android.synthetic.main.credit_card_layout.view.*
import kotlinx.coroutines.NonDisposableHandle.parent
import kotlinx.coroutines.withContext

class CardListAdapter(private var cards: MutableList<UserCard>): RecyclerView.Adapter<CardListAdapter.ViewHolder>() {

    /**
     * Involves inflating a layout from XML and returning the holder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.credit_card_layout, parent, false)

        return ViewHolder(view)
    }

    /**
     * Length of list
     */
    override fun getItemCount(): Int {

        return cards.size
    }

    /**
     * Involves populating data into the item through holder
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val formatter = CardNumbersFormat()

        val cardNumber = formatter.formatCardNumber(cards[position].cardNumber)
        holder.cardTitle.text = cardNumber

        val cardMonthYear: String =  cards[position].cardDue
        holder.cardDescription.text = cardMonthYear

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardTitle: TextView = itemView.tv_card_name
        val cardDescription: TextView = itemView.tv_card_number

        init {}
    }

}