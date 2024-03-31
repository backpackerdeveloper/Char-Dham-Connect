package com.shubhamtripz.chardhamconnect

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import de.hdodenhof.circleimageview.CircleImageView

class MainActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var home_profile: CircleImageView
    private val navListener =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.Home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    return@OnNavigationItemSelectedListener true
                }

                R.id.Explore -> {
                    startActivity(Intent(this, ExploreActivity::class.java))
                    return@OnNavigationItemSelectedListener true
                }

                R.id.Booking -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    return@OnNavigationItemSelectedListener true
                }

                else -> return@OnNavigationItemSelectedListener false
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // bottom nav code
        val bottom_navigation = findViewById<BottomNavigationView>(R.id.bottomnav)
        bottom_navigation.setOnNavigationItemSelectedListener(navListener)
        // bottom nav code end

        // button click
        var home_profile = findViewById<CircleImageView>(R.id.home_profile)
        home_profile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        //Webview setup ---
        findViewById<View>(R.id.know_more).setOnClickListener {
            openWebView("https://uttarakhandtourism.gov.in/destination/kedarnath")
        }

        // START -- Cards gmvn click listeners ---
        findViewById<View>(R.id.tilwara_card).setOnClickListener {
            openWebView("https://gmvnonline.com/room-tariff.php?trhID=42&adults=&child=&checkindate=&checkoutdate=")
        }

        findViewById<View>(R.id.gmvn_badrinath_card).setOnClickListener {
            openWebView("https://gmvnonline.com/room-tariff.php?trhID=49&adults=&child=&checkindate=&checkoutdate=")
        }

        findViewById<View>(R.id.haridwar_card).setOnClickListener {
            openWebView("https://gmvnonline.com/room-tariff.php?trhID=85&adults=&child=&checkindate=&checkoutdate=")
        }

        findViewById<View>(R.id.haridwar_card).setOnClickListener {
            openWebView("https://gmvnonline.com/room-tariff.php?trhID=85&adults=&child=&checkindate=&checkoutdate=")
        }

        findViewById<View>(R.id.pauri_card).setOnClickListener {
            openWebView("https://gmvnonline.com/room-tariff.php?trhID=23&adults=&child=&checkindate=&checkoutdate=")
        }
        // END ---- Cards gmvn click listeners ----


        // START ---- CharDham booking click listeners ---
        findViewById<View>(R.id.kedarnath_registraion).setOnClickListener {
            openWebView("https://registrationandtouristcare.uk.gov.in/")
        }

        findViewById<View>(R.id.badrinath_registraion).setOnClickListener {
            openWebView("https://registrationandtouristcare.uk.gov.in/")
        }

        findViewById<View>(R.id.gangotri_registraion).setOnClickListener {
            openWebView("https://registrationandtouristcare.uk.gov.in/")
        }

        findViewById<View>(R.id.yamunotri_registraion).setOnClickListener {
            openWebView("https://registrationandtouristcare.uk.gov.in/")
        }
        findViewById<View>(R.id.gmvn_more_btn).setOnClickListener {
            openWebView("https://gmvnonline.com/trh-filter.php")
        }
        // END ---- CharDham booking click listeners ----


        // START --- Profile icon Image load code ---
        home_profile = findViewById(R.id.home_profile)
        database = FirebaseDatabase.getInstance().reference

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        userId?.let { uid ->
            val userRef = database.child("users").child(uid)
            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val profileImageUrl =
                        snapshot.child("profileImageUrl").getValue(String::class.java)
                    profileImageUrl?.let { url ->
                        Glide.with(this@MainActivity)
                            .load(url)
                            .placeholder(R.drawable.man) // Optional placeholder image
                            .error(R.drawable.man) // Optional error image
                            .into(home_profile)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                }
            })
        }
        // END --- Profile icon Image load code ---
    }

    // START --- Webview method to open webview activity with url ---
    private fun openWebView(url: String) {
        val intent = Intent(this, WebActivity::class.java)
        intent.putExtra("URL", url)
        startActivity(intent)
    }
    // END --- Webview method to open webview activity with url ---
}
