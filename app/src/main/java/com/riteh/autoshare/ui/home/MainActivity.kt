package com.riteh.autoshare.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.riteh.autoshare.R
import com.riteh.autoshare.adapters.ViewPagerAdapter
import com.riteh.autoshare.data.UserPreferences
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        setUpTabs()

        /*check if we have user preferences*/
        val userPreferences = UserPreferences(this)
        userPreferences.authToken.asLiveData().observe(this, Observer {
//            startActivity(Intent(this,MainActivity::class.java))
        })
    }

    /**
     * Set up tabs with ViewPagerAdapter.
     */
    private fun setUpTabs() {
        val fragmentList = getFragmentList()

        val adapter = ViewPagerAdapter(supportFragmentManager, fragmentList)
        view_pager.adapter = adapter

        tabs.setupWithViewPager(view_pager)
        setUpTabIcons()
    }

    /**
     * Makes a list of Fragment objects for ViewPagerAdapter.
     */
    private fun getFragmentList(): List<Fragment> {
        val searchFragment = SearchFragment.newInstance()
        val infoFragment = InfoFragment.newInstance()
        val addFragment = AddFragment.newInstance()
        val messagesFragment = MessagesFragment.newInstance()
        val userFragment = UserFragment.newInstance()

        return listOf(searchFragment, infoFragment, addFragment, messagesFragment, userFragment)
    }

    private fun setUpTabIcons() {
        tabs.getTabAt(0)!!.setIcon(R.drawable.ic_baseline_directions_car_24)
        tabs.getTabAt(1)!!.setIcon(R.drawable.ic_baseline_info_24)
        tabs.getTabAt(2)!!.setIcon(R.drawable.ic_baseline_add_circle_outline_24)
        tabs.getTabAt(3)!!.setIcon(R.drawable.ic_baseline_message_24)
        tabs.getTabAt(4)!!.setIcon(R.drawable.ic_baseline_person_24)
    }

    /**
     * Makes sure you can't go further back than home screen activity.
     */
    override fun onBackPressed() {
        return
    }
}