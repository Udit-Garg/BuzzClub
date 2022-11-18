package com.buzz.club.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.buzz.club.AccountSettingsActivity
import com.buzz.club.Model.User
import com.buzz.club.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    private lateinit var profileId:String
    private lateinit var firebaseUser:FirebaseUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_profile, container, false)
        firebaseUser=FirebaseAuth.getInstance().currentUser!!
        val pref=context?.getSharedPreferences("PREFS",Context.MODE_PRIVATE)
        if(pref!=null){
            this.profileId= pref.getString("profileId","none").toString()
        }
        if(profileId==firebaseUser.uid){
            view.edit_account_settings_btn.text="Edit Profile"
        }
        else if(profileId!=firebaseUser.uid){
           checkFollowandFollowingButtonStatus()
        }


        view.edit_account_settings_btn.setOnClickListener{
            startActivity(Intent(context,AccountSettingsActivity::class.java))
        }
        getFollowers()
        getFollowings()
        userInfo()
        return view
    }

    private fun checkFollowandFollowingButtonStatus() {
        val followingRef=firebaseUser?.uid.let { it1 ->
            FirebaseDatabase.getInstance().reference
                .child("Follow").child(it1.toString())
                .child("Followers")
        }
        if(followingRef!=null){
            followingRef.addValueEventListener(
                object :ValueEventListener{
                    override fun onDataChange(p0: DataSnapshot) {
                       if(p0.child(profileId).exists()){
                           view?.edit_account_settings_btn?.text="Following"
                       }
                        else{
                           view?.edit_account_settings_btn?.text="Follow"
                       }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                }
            )
        }

    }
    private fun getFollowers(){
        val followersRef= FirebaseDatabase.getInstance().reference
                .child("Follow").child(profileId)
                .child("Followers")

        followersRef.addValueEventListener(
            object :ValueEventListener{
                override fun onDataChange(p0: DataSnapshot) {
                    if(p0.exists()){
                        view?.total_followers?.text=p0.childrenCount.toString()
                    }

                }

                override fun onCancelled(error: DatabaseError) {

                }
            }
        )
    }
    private fun getFollowings(){
        val followersRef= FirebaseDatabase.getInstance().reference
                .child("Follow").child(profileId)
                .child("Followings")

        followersRef.addValueEventListener(
            object :ValueEventListener{
                override fun onDataChange(p0: DataSnapshot) {
                    if(p0.exists()){
                        view?.total_following?.text=p0.childrenCount.toString()
                    }

                }

                override fun onCancelled(error: DatabaseError) {

                }
            }
        )
    }

    private fun userInfo(){
        val usersRef=FirebaseDatabase.getInstance().getReference().child("Users").child("profileId")
        usersRef.addValueEventListener(
            object :ValueEventListener{
                override fun onDataChange(p0: DataSnapshot) {
//                    if(context!=null){
//                      return
//                    }
                    if(p0.exists()){
                        val user=p0.getValue<User>(User::class.java)
                        Picasso.get().load(user!!.getImage()).placeholder(R.drawable.profile).into(view?.pro_image_profile_frag)
                        view?.profile_fragment_username?.text=user.getUsername()
                        view?.full_name_profile_frag?.text=user.getFullname()
                        view?.bio_profile_frag?.text=user.getBio()
                    }

                }

                override fun onCancelled(error: DatabaseError) {

                }
            }
        )
    }

    override fun onStop() {
        super.onStop()
        val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
        pref?.putString("profileId", firebaseUser.uid)
        pref?.apply()
    }

    override fun onPause() {
        super.onPause()
        val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
        pref?.putString("profileId", firebaseUser.uid)
        pref?.apply()
    }
    override fun onDestroy() {
        super.onDestroy()
        val pref = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)?.edit()
        pref?.putString("profileId", firebaseUser.uid)
        pref?.apply()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}