package com.anggriawans.coffeekonstitusi.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.anggriawans.coffeekonstitusi.adapter.MenuAdapter
import com.anggriawans.coffeekonstitusi.databinding.FragmentSearchBinding
import com.anggriawans.coffeekonstitusi.model.MenuItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: MenuAdapter
    private lateinit var database: FirebaseDatabase
    private val originalMenuItems = mutableListOf<MenuItem>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        retrieveMenuItem()

        setUpSearchView()

        return binding.root
    }

    private fun retrieveMenuItem() {
        database = FirebaseDatabase.getInstance()
        val coffeeReference: DatabaseReference = database.reference.child("menu")
        coffeeReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (coffeeSnapshot in snapshot.children) {

                    val menuItem = coffeeSnapshot.getValue(MenuItem::class.java)
                    menuItem?.let {
                        originalMenuItems.add(it)
                    }
                }
                showAllMenu()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun showAllMenu() {
        val filteredMenuItem = ArrayList(originalMenuItems)
        setAdapter(filteredMenuItem)
    }

    private fun setAdapter(filteredMenuItem: List<MenuItem>) {
        adapter = MenuAdapter(filteredMenuItem, requireContext())
        binding.menuSearchRV.layoutManager = LinearLayoutManager(requireContext())
        binding.menuSearchRV.adapter = adapter
    }

    private fun setUpSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                filterMenuItems(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filterMenuItems(newText)
                return true
            }
        })
    }

    private fun filterMenuItems(query: String) {
        val filteredMenuItem = originalMenuItems.filter {
            it.coffeeName?.contains(query, ignoreCase = true) == true
        }
        setAdapter(filteredMenuItem)
    }

    companion object {
    }

}
