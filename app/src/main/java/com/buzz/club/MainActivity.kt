package com.buzz.club

import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.buzz.club.Fragments.HomeFragment
import com.buzz.club.Fragments.NotificationFragment
import com.buzz.club.Fragments.ProfileFragment
import com.buzz.club.Fragments.SearchFragment
import com.buzz.club.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {



    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                   moveToFragment(HomeFragment())
                    return@OnNavigationItemSelectedListener true

                }
                R.id.nav_search -> {
                    moveToFragment(SearchFragment())
                    return@OnNavigationItemSelectedListener true



                }
                R.id.nav_add_post -> {



                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_notifications -> {
                    moveToFragment(NotificationFragment())
                    return@OnNavigationItemSelectedListener true



                }
                R.id.nav_profile -> {
                    moveToFragment(ProfileFragment())
                    return@OnNavigationItemSelectedListener true



                }
            }


            false
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        moveToFragment(HomeFragment())

    }
    private fun moveToFragment(fragment:Fragment){
        val fragmentTrans=supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.fragment_container,fragment)
        fragmentTrans.commit()
    }
}