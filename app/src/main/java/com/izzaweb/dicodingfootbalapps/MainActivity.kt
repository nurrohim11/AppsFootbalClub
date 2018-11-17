package com.izzaweb.dicodingfootbalapps

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import com.izzaweb.dicodingfootbalapps.R.id.*
import com.izzaweb.dicodingfootbalapps.favorit.FavoriteFragment
import com.izzaweb.dicodingfootbalapps.nextmatch.NextMatchFragment
import com.izzaweb.dicodingfootbalapps.prevmatch.PrevMatchFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId){
                next -> {
                    LoadFragmentNext(savedInstanceState)
                }
                prev -> {
                    LoadFragmentPrev(savedInstanceState)
                }
                favorit -> {
                    LoadFragmentFavorit(savedInstanceState)
                }
            }
            true
        }
        bottom_navigation.selectedItemId = next
    }
    private fun LoadFragmentNext(savedInstanceState: Bundle?){
        if (savedInstanceState == null){
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, NextMatchFragment(), NextMatchFragment::class.java.simpleName)
                    .commit()
        }
    }
    private fun LoadFragmentFavorit(savedInstanceState: Bundle?){
        if (savedInstanceState == null){
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, FavoriteFragment(), FavoriteFragment::class.java.simpleName)
                    .commit()
        }
    }

    private fun LoadFragmentPrev(savedInstanceState: Bundle?){
        if (savedInstanceState == null){
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, PrevMatchFragment(), PrevMatchFragment::class.java.simpleName)
                    .commit()
        }
    }
}
