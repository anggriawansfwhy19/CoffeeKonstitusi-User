package com.anggriawans.coffeekonstitusi.Fragment

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.anggriawans.coffeekonstitusi.MenuBottomSheetFragment
import com.anggriawans.coffeekonstitusi.R
import com.anggriawans.coffeekonstitusi.adapter.MenuAdapter
import com.anggriawans.coffeekonstitusi.databinding.FragmentHomeBinding
import com.anggriawans.coffeekonstitusi.model.MenuItem
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var menuItems: MutableList<MenuItem>
    private val fadeIn: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in) }
    private val fadeOut: Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val surveyBtn: ImageButton = binding.surveyBtn

        binding.viewAllMenu.setOnClickListener {
            val bottomSheetDialog = MenuBottomSheetFragment()
            bottomSheetDialog.show(parentFragmentManager, "Test")
        }

        surveyBtn.setOnClickListener {
            // Menerapkan animasi fade-out pada ImageButton
            surveyBtn.startAnimation(fadeOut)
            fadeOut.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    surveyBtn.visibility = View.GONE
                    // Setelah fadeOut selesai, menampilkan dialog konfirmasi
                    showConfirmationDialog()
                    // Menerapkan animasi fade-in pada ImageButton
                    surveyBtn.startAnimation(fadeIn)
                }

                override fun onAnimationRepeat(animation: Animation?) {}
            })
        }
        // Retrieve and display popular menu Items
        retrieveAndDisplayPopularItem()
        return binding.root
    }

    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Konfirmasi")
        builder.setMessage("Apakah Anda ingin melanjutkan untuk mencari rekomendasi kopi untuk Anda?")

        builder.setPositiveButton("OKE") { _, _ ->
            // Menampilkan ProgressBar selama 3 detik
            val progressBar = ProgressDialog.show(requireContext(), "Loading", "Harap tunggu...", true)
            Handler().postDelayed({
                progressBar.dismiss()
                // Beralih ke SurveyFragment setelah loading selesai
                findNavController().navigate(R.id.action_homeFragment_to_surveyFragment)
            }, 3000)
        }

        builder.setNegativeButton("NO") { _, _ ->
            // Tidak melakukan apa-apa jika tombol NO ditekan
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun retrieveAndDisplayPopularItem() {
        database = FirebaseDatabase.getInstance()
        val coffeeRef: DatabaseReference = database.reference.child("menu")
        menuItems = mutableListOf()

        coffeeRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (coffeeSnapshot in snapshot.children){
                    val menuItem = coffeeSnapshot.getValue(MenuItem::class.java)
                    menuItem?.let { menuItems.add(it) }
                }
                // display a random popular items
                randomPopularItems()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
    private fun randomPopularItems() {
        // create as shuffled list of menu items
        val index = menuItems.indices.toList().shuffled()
        val numItemToShow = 6
        val subsetMenuItem = index.take(numItemToShow).map { menuItems[it] }

        setPopularItemsAdapter(subsetMenuItem)
    }

    private fun setPopularItemsAdapter(subsetMenuItem: List<MenuItem>) {
        val adapter = MenuAdapter(subsetMenuItem, requireContext())
        binding.PopularRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.PopularRecyclerView.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.banner1, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner2, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner3, ScaleTypes.FIT))

        val imageSlider = binding.imageSlider
        imageSlider.setImageList(imageList)
        imageSlider.setImageList(imageList, ScaleTypes.FIT)
        imageSlider.setItemClickListener(object : ItemClickListener {
            override fun doubleClick(position: Int) {
                val itemMessage = "Double Clicked on Image $position"
                Toast.makeText(requireContext(), itemMessage, Toast.LENGTH_SHORT).show()
            }

            override fun onItemSelected(position: Int) {
                val itemPosition = imageList[position]
                val itemMessage = "Selected Image $position"
                Toast.makeText(requireContext(), itemMessage, Toast.LENGTH_SHORT).show()
            }
        })

    }
}