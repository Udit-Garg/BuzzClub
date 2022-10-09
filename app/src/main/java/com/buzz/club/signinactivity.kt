package com.buzz.club

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_signinactivity.*

class signinactivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signinactivity)
        signup_link_btn.setOnClickListener {
            startActivity(Intent(this, Signupactivity::class.java))
        }
    }
}