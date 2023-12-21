package com.anggriawans.coffeekonstitusi

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.anggriawans.coffeekonstitusi.databinding.ActivityChooseLocationBinding

class ChooseLocationActivity : AppCompatActivity() {
    private val binding: ActivityChooseLocationBinding by lazy {
        ActivityChooseLocationBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val locationList = arrayOf(
            "Jakarta, Indonesia",
            "Jakarta, Cilandak, Indonesia",
            "Jakarta, Kemang, Indonesia",
            "Bandung, Indonesia",
            "Bekasi, Indonesia",
            "Bekasi, Kabupaten Bekasi, Indonesia",
            "Bekasi, Cikarang, Cikarang Utara, Indonesia",
            "Tangerang, Indonesia",
            "Tangerang, BSD City, Indonesia",
            "Jogjakarta, Indonesia",
            "Jogjakarta, Sleman, Indonesia",
            "Surabaya, Indonesia",
            "Surabaya, Sidoarjo, Indonesia",
            "Semarang, Indonesia",
            "Semarang, Ungaran, Indonesia",
            "Medan, Indonesia",
            "Medan, Deli Serdang, Indonesia",
            "Palembang, Indonesia",
            "Palembang, Ilir Barat, Indonesia",
            "Makassar, Indonesia",
            "Makassar, Mariso, Indonesia",
            "Denpasar, Indonesia",
            "Denpasar, Gianyar, Indonesia",
            "Balikpapan, Indonesia",
            "Balikpapan, Samarinda, Indonesia",
            "Padang, Indonesia",
            "Padang, Pariaman, Indonesia",
            "Malang, Indonesia",
            "Malang, Batu, Indonesia",
            "Manado, Indonesia",
            "Manado, Minahasa, Indonesia",
            "Bandar Lampung, Indonesia",
            "Bandar Lampung, Metro, Indonesia",
            "Pontianak, Indonesia",
            "Pontianak, Mempawah, Indonesia",
            "Cirebon, Indonesia",
            "Cirebon, Majalengka, Indonesia",
            "Banjarmasin, Indonesia",
            "Banjarmasin, Banjar, Indonesia",
            "Kupang, Indonesia",
            "Kupang, Timor Tengah Selatan, Indonesia",
            "Batam, Indonesia",
            "Batam, Bintan, Indonesia",
            "Pekanbaru, Indonesia",
            "Pekanbaru, Siak, Indonesia",
            "Palu, Indonesia",
            "Palu, Donggala, Indonesia",
            "Ambon, Indonesia",
            "Ambon, Maluku Tengah, Indonesia",
            "Samarinda, Indonesia",
            "Samarinda, Kutai Kartanegara, Indonesia",
            "Tanjung Pinang, Indonesia",
            "Tanjung Pinang, Bintan, Indonesia",
            "Tanjung Balai, Indonesia",
            "Tanjung Balai, Asahan, Indonesia",
            "Ternate, Indonesia",
            "Ternate, Tidore Kepulauan, Indonesia",
            "Sorong, Indonesia",
            "Sorong, Raja Ampat, Indonesia",
            "Bau-Bau, Indonesia",
            "Bau-Bau, Buton, Indonesia",
            "Mamuju, Indonesia",
            "Mamuju, Mamasa, Indonesia",
            "Tarakan, Indonesia",
            "Tarakan, Nunukan, Indonesia",
            "Bitung, Indonesia",
            "Bitung, Minahasa, Indonesia",
            "Padang Sidempuan, Indonesia",
            "Padang Sidempuan, Tapanuli Selatan, Indonesia",
            "Langsa, Indonesia",
            "Langsa, Aceh Timur, Indonesia",
            "Tebing Tinggi, Indonesia",
            "Tebing Tinggi, Serdang Bedagai, Indonesia",
            "Madiun, Indonesia",
            "Madiun, Magetan, Indonesia",
            "Bontang, Indonesia",
            "Bontang, Kutai Kartanegara, Indonesia",
            "Metro, Indonesia",
            "Metro, Lampung Tengah, Indonesia",
            "Lahat, Indonesia",
            "Lahat, Muara Enim, Indonesia",
            "Solok, Indonesia",
            "Solok, Solok Selatan, Indonesia",
            "Lubuklinggau, Indonesia",
            "Lubuklinggau, Musi Rawas, Indonesia",
            "Parepare, Indonesia",
            "Parepare, Barru, Indonesia",
            "Palopo, Indonesia",
            "Palopo, Luwu, Indonesia",
            "Bima, Indonesia",
            "Bima, Dompu, Indonesia",
            "Bukittinggi, Indonesia",
            "Bukittinggi, Agam, Indonesia",
            "Sungai Penuh, Indonesia",
            "Sungai Penuh, Kerinci, Indonesia",
            "Palangka Raya, Indonesia",
            "Palangka Raya, Pulang Pisau, Indonesia",
            "Sukabumi, Indonesia",
            "Sukabumi, Cianjur, Indonesia",
            "Pematangsiantar, Indonesia",
            "Pematangsiantar, Simalungun, Indonesia",
            "Subulussalam, Indonesia",
            "Subulussalam, Aceh Singkil, Indonesia",
            ".",
            "Lokasi Lainnya"
        )

        val adapter = ArrayAdapter(this, R.layout.custom_dropdown_item, locationList)
        val autoCompleteTextView: AutoCompleteTextView = binding.listOfLocation
        autoCompleteTextView.setAdapter(adapter)

        val progressBar = binding.progressBarLocation
        progressBar.visibility = View.INVISIBLE

        binding.nextBtn.setOnClickListener {
            // Mendapatkan lokasi yang dipilih dari autoCompleteTextView
            val selectedLocation = autoCompleteTextView.text.toString()

            // Cek apakah lokasi dipilih dari daftar yang tersedia
            if (locationList.contains(selectedLocation)) {
                // Menampilkan ProgressBar

                Handler(Looper.getMainLooper()).postDelayed({

                    progressBar.visibility = View.VISIBLE

                    // Intent untuk pindah ke MainActivity
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish() // Menutup ChooseLocationActivity setelah MainActivity dimulai
                }, 5000)
            } else {
                // Menampilkan pesan bahwa lokasi tidak valid
                Toast.makeText(this, "Pilih Lokasi anda terlebih dahulu😉.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
