package com.anggriawans.coffeekonstitusi

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {

    // Deklarasi ProgressBar
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Inisialisasi ProgressBar
        progressBar = findViewById(R.id.progressBar)

        // Mengatur Handler untuk menangani penundaan
        Handler(Looper.getMainLooper()).postDelayed({
            // Intent untuk pindah ke StartActivity
            val intent = Intent(this, StartActivity::class.java)
            startActivity(intent)
            finish() // Menutup SplashScreen setelah StartActivity dimulai
        }, 3000) // Menunda selama 3000 milidetik (3 detik)
    }
}
