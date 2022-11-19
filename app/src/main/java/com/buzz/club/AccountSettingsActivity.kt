package com.buzz.club

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.buzz.club.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_account_settings.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

class AccountSettingsActivity : AppCompatActivity() {
    private lateinit var firebaseUser: FirebaseUser
    private var checker=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_settings)
        firebaseUser=FirebaseAuth.getInstance().currentUser!!
        save_infor_profile_btn.setOnClickListener{
                 if(checker=="clicked"){

                 }
                else{
                    updateUserInfoOnly()
                 }
        }

      userInfo()

    }

    private fun updateUserInfoOnly() {
        when {
            TextUtils.isEmpty((full_name_profile_frag.text.toString()=="").toString()) -> {
                Toast.makeText(this,"Please write full name first", Toast.LENGTH_LONG).show()
            }
            user_name_profile_frag.text.toString()=="" -> {
                Toast.makeText(this,"Please write user name first", Toast.LENGTH_LONG).show()
            }
            bio_profile_frag.text.toString()=="" -> {
                Toast.makeText(this,"Please write your bio first", Toast.LENGTH_LONG).show()
            }
            else -> {
                val usersRef= FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser.uid)
                val userMap=HashMap<String,Any>()
                userMap["fullname"]=full_name_profile_frag.text.toString().toLowerCase()
                userMap["username"]= user_name_profile_frag.text.toString().toLowerCase()
                userMap["bio"]=bio_profile_frag.text.toString().toLowerCase()

                usersRef.child(firebaseUser.uid).updateChildren(userMap)
                Toast.makeText(this,"account information has been saved successfully",Toast.LENGTH_LONG).show()
                val intent=Intent(this@AccountSettingsActivity,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }


    }

    private fun userInfo(){
        val usersRef= FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.uid)
        usersRef.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {

                    if(p0.exists()){
                        val user=p0.getValue<User>(User::class.java)
                        Picasso.get().load(user!!.getImage()).placeholder(R.drawable.profile).into(profile_image_view_profile_frag)
                        user_name_profile_frag.setText(user.getUsername())
                        full_name_profile_frag.setText(user.getFullname())
                        bio_profile_frag.setText(user.getBio())
                    }

                }

                override fun onCancelled(error: DatabaseError) {

                }
            }
        )
    }

}
