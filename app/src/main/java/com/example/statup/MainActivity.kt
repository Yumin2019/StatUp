package com.example.statup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.statup.fragment.AchievementFragment
import com.example.statup.fragment.GraphFragment
import com.example.statup.fragment.HomeFragment
import com.example.statup.module.RecyclerViewAdapter
import com.example.statup.module.StatItem
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity()
{
    lateinit var bottom_menu: BottomNavigationView


    private fun initBottomMenu()
    {
        // bottom_menu listener
        bottom_menu.setOnNavigationItemSelectedListener { item: MenuItem ->

            when (item.itemId)
            {
                R.id.item_home ->
                {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, HomeFragment()).commit()
                    true
                }

                R.id.item_graph ->
                {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, GraphFragment()).commit()
                    true
                }

                R.id.item_achievement ->
                {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, AchievementFragment()).commit()
                    true
                }

                else -> true
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_menu = findViewById(R.id.bottom_menu)

        // home activity at first
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomeFragment())
            .commit()

        initBottomMenu()


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)



    }

    /*  private var firstTime: Long = 0
    private var secondTime: Long = 0
    override fun onBackPressed()
    {
        secondTime = System.currentTimeMillis()

        if (secondTime - firstTime < 2000)
        {
            super.onBackPressed()
            finishAffinity()
        }
        else
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        firstTime = secondTime
    }*/
}