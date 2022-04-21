package com.riteh.autoshare.ui.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.riteh.autoshare.R
import com.riteh.autoshare.adapters.CardListAdapter
import kotlinx.android.synthetic.main.activity_user_card_info.*
import android.app.AlertDialog

class UserCardInfoActivity : AppCompatActivity() {
    lateinit var adapter: CardListAdapter

    /**
     * Main activity to display all user cards
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_card_info)

        bt_add_card.setOnClickListener {
            val intent = Intent(this, UserCardActivity::class.java)
            startActivity(intent)
        }
        setUpRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        setUpRecyclerView()
    }

    /**
     * Display all cards data and reuses the view with new items if the user scrolle on screen
     */
    private fun setUpRecyclerView() {
        val prefs = getSharedPreferences("USER_CARD_PREFERENCES", Context.MODE_PRIVATE)
        val gson = Gson()
        val json: String? = prefs.getString("userCard", "")
        val userCard: UserCard = gson.fromJson(json, UserCard::class.java)

        rv_cards.layoutManager = LinearLayoutManager(this)
        adapter = if (userCard.cardsList.isNullOrEmpty()) {
            CardListAdapter(mutableListOf())
        } else  {
            userCard.cardsList.let { CardListAdapter(it) }
        }
        rv_cards.adapter = adapter

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(rv_cards)
    }

    /**
     * Dynamically create alert if the user want to delete card
     */
    private fun callDialog(position: Int) {
        val prefs = getSharedPreferences("USER_CARD_PREFERENCES", Context.MODE_PRIVATE)
        val prefsEditor = prefs.edit()
        val gson = Gson()
        var json = prefs.getString("userCard", "")
        val userCard: UserCard = gson.fromJson(json, UserCard::class.java)

        val formatter = CardNumbersFormat()
        val formattedNumber = formatter.formatCardNumber(userCard.cardsList[position].cardNumber)

        val builder = AlertDialog.Builder(this)
        val sb = StringBuilder()

        builder.setTitle(R.string.delete_card)
        sb.append(getString(R.string.are_you_sure))
            .append(" ")
            .append(formattedNumber)
            .append("?")
        builder.setMessage(sb.toString())

        builder.setPositiveButton(R.string.yes) { dialog, _ ->
            userCard.cardsList.removeAt(position)

            json = gson.toJson(userCard)
            prefsEditor.putString("userCard", json)
            prefsEditor.apply()

            Toast.makeText(this, R.string.card_deleted, Toast.LENGTH_SHORT).show()
            dialog.dismiss()
            setUpRecyclerView()
        }

        builder.setNegativeButton(R.string.no) { _, _ ->
            setUpRecyclerView()
        }

        val dialog = builder.create()

        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getColor(R.color.basic_red))
    }


    /**
     * Enable alert creation
     */
    private var itemTouchHelperCallback: ItemTouchHelper.SimpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            callDialog(viewHolder.layoutPosition)
        }
    }



}
