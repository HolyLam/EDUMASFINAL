package com.example.tubes

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class main_menu : AppCompatActivity() {
    private lateinit var logoProfile: ImageView
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

//    private val onNavigationItemSelectedListener =
//        BottomNavigationView.OnNavigationItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.bottom_home -> {
//                    replaceFragment(HomeFragment())
//                    return@OnNavigationItemSelectedListener true
//                }
//                R.id.bottom_history -> {
//                    replaceFragment(HistoryFragment())
//                    return@OnNavigationItemSelectedListener true
//                }
//                R.id.bottom_emergency -> {
//                    replaceFragment(EmergencyFragment())
//                    return@OnNavigationItemSelectedListener true
//                }
//                R.id.bottom_profil -> {
//                    replaceFragment(ProfileFragment())
//                    return@OnNavigationItemSelectedListener true
//                }
//            }
//            false
//        }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        val cardPengaduan: CardView = findViewById(R.id.laporanPengaduan)
        cardPengaduan.setOnClickListener {
            val pindah = Intent(this, main_pengaduan::class.java)
            startActivity(pindah)
        }
        val cardData: CardView = findViewById(R.id.dataKeluarga)
        cardData.setOnClickListener {
            val pindah = Intent(this, form_data_keluarga::class.java)
            startActivity(pindah)
        }

//        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigation)
//        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
//
//        // Set the default fragment when the activity is created
//        replaceFragment(HomeFragment())

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        logoProfile = findViewById(R.id.barUserlogoMenu)

        val tvNamaMain: TextView = findViewById(R.id.tvNamaMain)
        val tvEmailMain: TextView = findViewById(R.id.tvEmailMain)
        val tvAlamatMain: TextView = findViewById(R.id.tvAlamatMain)

        val user = auth.currentUser
        if (user != null) {
            val userId = user.uid
            val userRef = database.reference.child("users").child(userId)

            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val namaDariFirebase = snapshot.child("name").getValue(String::class.java)
                    val emailDariFirebase = snapshot.child("email").getValue(String::class.java)
                    val alamatDariFirebase = snapshot.child("alamat").getValue(String::class.java)

                    // Set teks di TextView sesuai dengan data yang didapat dari Firebase
                    tvNamaMain.text = namaDariFirebase ?: ""
                    tvEmailMain.text = emailDariFirebase ?: ""
                    tvAlamatMain.text = alamatDariFirebase ?: ""
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle kegagalan untuk mendapatkan data
                    Toast.makeText(
                        this@main_menu,
                        "Failed to retrieve data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }

    }
//    private fun replaceFragment(fragment: Fragment) {
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.fragmentContainer, fragment)
//            .commit()
//    }
}
