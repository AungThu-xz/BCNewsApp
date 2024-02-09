package com.aungthu.bcnewsapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.aungthu.bcnewsapp.utils.Constants.TOTAL_NEWS_TAB
import com.aungthu.bcnewsapp.view.home.BusinessFragment
import com.aungthu.bcnewsapp.view.home.DailyNewsFragment


class FragmentAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount(): Int = TOTAL_NEWS_TAB

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                DailyNewsFragment()
            }

            1 -> {
                BusinessFragment()
            }

            else -> DailyNewsFragment()

        }
    }
}