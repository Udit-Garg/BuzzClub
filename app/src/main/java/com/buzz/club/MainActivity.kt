package com.buzz.club

import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.buzz.club.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView


    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    textView.setText("Home")
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_search -> {
                    textView.setText("search")

                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_add_post -> {
                    textView.setText("addpost")

                    item.isChecked = false
//                    startActivity(Intent(this@MainActivity, AddPostActivity::class.java))
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_notifications -> {
                    textView.setText("notification")

                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_profile -> {
                    textView.setText("profile")

                    return@OnNavigationItemSelectedListener true
                }
            }

            false
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        textView = findViewById(R.id.message)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

    }
}