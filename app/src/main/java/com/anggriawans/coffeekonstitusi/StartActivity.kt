package com.anggriawans.coffeekonstitusi

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.anggriawans.coffeekonstitusi.adapter.ImagePagerAdapter
import com.anggriawans.coffeekonstitusi.databinding.ActivityStartBinding
import com.google.android.material.tabs.TabLayout

class StartActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var tabDots: TabLayout
    private val binding: ActivityStartBinding by lazy {
        ActivityStartBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        progressBar = findViewById(R.id.progressBarStart)
        tabDots = findViewById(R.id.tabDots)

        // Inisialisasi ViewPager
        val viewPager = binding.viewPager

        // Buat dan set adapter untuk ViewPager
        val adapter = ImagePagerAdapter(this)
        viewPager.adapter = adapter

        // Menghubungkan ViewPager dengan TabLayout untuk indikator
        tabDots.setupWithViewPager(viewPager, true)

        // Set visibilitas awal ProgressBar menjadi tidak terlihat
        progressBar.visibility = View.INVISIBLE

        // Mengatur listener untuk tombol "Next"
        binding.next.setOnClickListener {
            // Setelah tombol "Next" ditekan, atur ProgressBar menjadi terlihat
            progressBar.visibility = View.VISIBLE

            // Lakukan tindakan yang diinginkan, seperti pindah ke layar berikutnya
            // ...

            // Setelah tindakan selesai, atur ProgressBar menjadi tidak terlihat
            Handler(Looper.getMainLooper()).postDelayed({
                progressBar.visibility = View.INVISIBLE
                val intent = Intent(this, ChooseLocationActivity::class.java)
                startActivity(intent)
            }, 3000)  // Sesuaikan dengan penundaan yang diinginkan
        }
    }
}


