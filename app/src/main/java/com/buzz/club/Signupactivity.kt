package com.buzz.club

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_signinactivity.*
import kotlinx.android.synthetic.main.activity_signupactivity.*

class Signupactivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signupactivity)

        signin_link_btn.setOnClickListener {
            startActivity(Intent(this, signinactivity::class.java))



        }


        signup_btn.setOnClickListener{
           CreateAccount()
        }





    }

    private fun CreateAccount() {
        val fullName=fullname_signup.text.toString()
        val userName=username_signup.text.toString()
        val email=email_signup.text.toString()
        val password=password_signup.text.toString()
        when{
            TextUtils.isEmpty(fullName)->Toast.makeText(this,"full name is required",Toast.LENGTH_LONG)
            TextUtils.isEmpty(userName)->Toast.makeText(this,"user name is required",Toast.LENGTH_LONG)
            TextUtils.isEmpty(email)->Toast.makeText(this,"email is required",Toast.LENGTH_LONG)
            TextUtils.isEmpty(password)->Toast.makeText(this,"password  is required",Toast.LENGTH_LONG)

            else->{

                val progressDialog= ProgressDialog(this@Signupactivity)
                progressDialog.setTitle("SignUp")
                progressDialog.setMessage("Please Wait...This may take a while ")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()
                val mAuth:FirebaseAuth = FirebaseAuth.getInstance()
                mAuth.createUserWithEmailAndPassword(email, password).
                        addOnCompleteListener{task->
                    if(task.isSuccessful){
                       saveUserInfo(fullName,userName,email)
                    }
                    else{
                        val message=task.exception!!.toString()
                        Toast.makeText(this,"error:$message",Toast.LENGTH_LONG)
                        mAuth.signOut()
                        progressDialog.dismiss()
                    }
            }


            }

        }

    }

    private fun saveUserInfo(fullName: String, userName: String, email: String) {

    }
}