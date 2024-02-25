package com.vasilyev.crocostesttask.presentation.myorders

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayoutMediator
import com.vasilyev.crocostesttask.R
import com.vasilyev.crocostesttask.databinding.ActivityMainBinding
import com.vasilyev.crocostesttask.presentation.BaseActivity
import com.vasilyev.crocostesttask.presentation.myorders.adapter.viewpager.MyOrdersPagerAdapter
import com.vasilyev.crocostesttask.presentation.myorders.past.PastOrdersFragment
import com.vasilyev.crocostesttask.presentation.myorders.upcoming.UpcomingOrdersFragment
import java.lang.RuntimeException

class MyOrdersActivity : BaseActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding ?: throw RuntimeException("ActivityMainBinding is null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActivitySettings()
        attachViewPagerAdapter()
    }

    private fun attachViewPagerAdapter(){
        val orderFragments = listOf(
            UpcomingOrdersFragment.newUpcomingOrdersInstance(),
            PastOrdersFragment.newPastOrdersFragmentInstance()
        )

        binding.viewPagerOrders.adapter =
                MyOrdersPagerAdapter(
                    supportFragmentManager,
                    lifecycle,
                    orderFragments)

        TabLayoutMediator(binding.tabLayoutOrders, binding.viewPagerOrders) { tab, position ->
            tab.text = when (position) {
                UPCOMING_ORDERS_FRAGMENT_POSITION -> getString(R.string.upcoming_orders)
                PAST_ORDERS_FRAGMENT_POSITION -> getString(R.string.past_orders)
                else -> null
            }
        }.attach()
    }

    private fun setActivitySettings(){
        val actionBarTitle = ContextCompat.getString(this, R.string.title_my_orders)
        createCustomActionBar(actionBarTitle)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object{
        private const val UPCOMING_ORDERS_FRAGMENT_POSITION = 0
        private const val PAST_ORDERS_FRAGMENT_POSITION = 1
    }
}