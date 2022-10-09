package com.buzz.club

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_signinactivity.*
import kotlinx.android.synthetic.main.activity_signupactivity.*

class Signupactivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signupactivity)

        signin_link_btn.setOnClickListener {
            startActivity(Intent(this, signinactivity::class.java))
        }













    }
}