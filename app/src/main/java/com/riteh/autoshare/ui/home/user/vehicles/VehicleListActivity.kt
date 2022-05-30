package com.riteh.autoshare.ui.home.user.vehicles

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.riteh.autoshare.R
import com.riteh.autoshare.adapters.VehicleListAdapter
import com.riteh.autoshare.data.dataholders.VehicleListItem
import com.riteh.autoshare.ui.home.MainActivity
import kotlinx.android.synthetic.main.activity_vehicle_list.*

class VehicleListActivity : AppCompatActivity() {
    private var vehiclesList = mutableListOf<VehicleListItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_list)

        setUpRecyclerView()
        setOnClickListeners()
    }


    override fun onResume() {
        super.onResume()
        setUpRecyclerView()
    }


    private fun setUpRecyclerView() {
        /*val prefs = getSharedPreferences("USER_CARD_PREFERENCES", Context.MODE_PRIVATE)
        val gson = Gson()
        val json: String? = prefs.getString("userCard", "{}")
        val userCard: UserCard = gson.fromJson(json, UserCard::class.java)*/

        rv_vehicles.layoutManager = GridLayoutManager(this, 1)
        rv_vehicles.adapter = VehicleListAdapter(vehiclesList, this)

        /*val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(rv_cards)*/
    }

    private fun setOnClickListeners() {
        arrow_back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            // TODO: open user tab instead of 1st tab
        }

        button.setOnClickListener {
            val intent = Intent(this, VehicleAddActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)    }
}