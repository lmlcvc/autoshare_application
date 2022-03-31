package com.riteh.autoshare.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


// TODO: deprecations
/**
 * Manages home screen tabs.
 *
 * @param supportFragmentManager: FragmentManager
 * @param fragmentList: List<Fragment> - list of fragments acting as tabs
 */
class ViewPagerAdapter(
    supportFragmentManager: FragmentManager,
    private val fragmentList: List<Fragment>
) :
    FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int {
        return fragmentList.size
    }


    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }


}
