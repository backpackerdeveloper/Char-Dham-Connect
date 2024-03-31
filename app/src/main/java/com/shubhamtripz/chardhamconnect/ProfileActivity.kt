package com.shubhamtripz.chardhamconnect

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import de.hdodenhof.circleimageview.CircleImageView

class ProfileActivity : AppCompatActivity() {

    private lateinit var imageViewProfile: CircleImageView
    private lateinit var database: DatabaseReference
    private lateinit var buttonLogout: RelativeLayout
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()

        val editprobtn = findViewById<RelativeLayout>(R.id.editprofilebtn)
        editprobtn.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

        val invfrnd = findViewById<RelativeLayout>(R.id.invitefriendbtn)
        invfrnd.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                "Char Dham Connect - One Stop Solution for your CharDham & Uttrakhand Tourism : Download now from playstore: https://play.google.com/store/apps/details?id=com.shubhamtripz.jharkhandbijilibillapp"
            )
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
        }

        //START --- Logout firebase auth button Handle code ---
        buttonLogout = findViewById(R.id.logoutbtn)
        buttonLogout.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, SingupActivity::class.java)
            startActivity(intent)
            finish()
            Toast.makeText(this, "Logged Out Successfully", Toast.LENGTH_SHORT).show()
        }
        //END --- Logout firebase auth button Handle code ---


        // image fetch code from firebase
        imageViewProfile = findViewById(R.id.profile_image)
        database = FirebaseDatabase.getInstance().reference

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        userId?.let { uid ->
            val userRef = database.child("users").child(uid)
            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val profileImageUrl = snapshot.child("profileImageUrl").getValue(String::class.java)
                    profileImageUrl?.let { url ->
                        Glide.with(this@ProfileActivity)
                            .load(url)
                            .placeholder(R.drawable.man) // Optional placeholder image
                            .error(R.drawable.man) // Optional error image
                            .into(imageViewProfile)
                    }
    }

                // image fetch code from firebase
                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                }
            })
        }
      }

}