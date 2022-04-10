package com.riteh.autoshare.ui.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.riteh.autoshare.R
import com.riteh.autoshare.adapters.CardListAdapter
import kotlinx.android.synthetic.main.activity_user_card_info.*

class UserCardInfoActivity : AppCompatActivity() {
    lateinit var adapter: CardListAdapter


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
    }
}