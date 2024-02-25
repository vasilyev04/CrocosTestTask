package com.vasilyev.crocostesttask.presentation

import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.vasilyev.crocostesttask.R

open class BaseActivity: AppCompatActivity() {

    //enables home button for each activity calling this method
    fun enableActionHomeButton(enabled: Boolean){
        if (enabled){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }else{
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }


    //creates custom action bar for each activity calling this method
    fun createCustomActionBar(title: String){
        supportActionBar?.let {
            it.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            it.setDisplayShowCustomEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_ios_new_24)

            val inflater = LayoutInflater.from(this@BaseActivity)
            val view = inflater.inflate(R.layout.custom_action_bar, null)

            val titleActionBar: TextView = view.findViewById(R.id.title)
            titleActionBar.text = title

            val params = ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER
            )

            it.setCustomView(view, params)
        }
    }
}