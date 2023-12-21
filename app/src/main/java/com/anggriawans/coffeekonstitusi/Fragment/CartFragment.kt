package com.anggriawans.coffeekonstitusi.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.anggriawans.coffeekonstitusi.PayOutActivity
import com.anggriawans.coffeekonstitusi.adapter.CartAdapter
import com.anggriawans.coffeekonstitusi.databinding.FragmentCartBinding
import com.anggriawans.coffeekonstitusi.model.CartItems
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var coffeeNames: MutableList<String>
    private lateinit var coffeePrices: MutableList<String>
    private lateinit var coffeeDescriptions: MutableList<String>
    private lateinit var coffeeImagesUri: MutableList<String>
    private lateinit var coffeeIngredients: MutableList<String>
    private lateinit var quantity: MutableList<Int>
    private lateinit var cartAdapter: CartAdapter
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()
        retrieveCartItems()


        binding.processedButton.setOnClickListener {
            // get order items details before processing to checkout
            getOrderItemsDetail()
        }

        return binding.root
    }

    private fun getOrderItemsDetail() {
        val orderIdReference: DatabaseReference =
            database.reference.child("user").child(userId).child("CartItems")

        val coffeeName = mutableListOf<String>()
        val coffeePrice = mutableListOf<String>()
        val coffeeDescription = mutableListOf<String>()
        val coffeeImage = mutableListOf<String>()
        val coffeeIngredient = mutableListOf<String>()
        // get items Quantities
        val coffeeQuantities = cartAdapter.getUpdateItemsQuantities()

        orderIdReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (coffeeSnapshot in snapshot.children) {

                    val orderItems = coffeeSnapshot.getValue(CartItems::class.java)
                    //add items details in to list
                    orderItems?.coffeeName?.let { coffeeName.add(it) }
                    orderItems?.coffeePrice?.let { coffeePrice.add(it) }
                    orderItems?.coffeeDescription?.let { coffeeDescription.add(it) }
                    orderItems?.coffeeImage?.let { coffeeImage.add(it) }
                    orderItems?.coffeeIngredient?.let { coffeeIngredient.add(it) }
                }
                orderNow(
                    coffeeName,
                    coffeePrice,
                    coffeeDescription,
                    coffeeImage,
                    coffeeIngredient,
                    coffeeQuantities
                )
            }

            private fun orderNow(
                coffeeName: MutableList<String>,
                coffeePrice: MutableList<String>,
                coffeeDescription: MutableList<String>,
                coffeeImage: MutableList<String>,
                coffeeIngredient: MutableList<String>,
                coffeeQuantities: MutableList<Int>
            ) {
                if (isAdded && context != null) {
                    val intent = Intent(requireContext(), PayOutActivity::class.java)
                    intent.putExtra("CoffeeItemName", coffeeName as ArrayList<String>)
                    intent.putExtra("CoffeeItemPrice", coffeePrice as ArrayList<String>)
                    intent.putExtra("CoffeeItemDescription", coffeeDescription as ArrayList<String>)
                    intent.putExtra("CoffeeItemImage", coffeeImage as ArrayList<String>)
                    intent.putExtra("CoffeeItemIngredient", coffeeIngredient as ArrayList<String>)
                    intent.putExtra("CoffeeItemQuantities", coffeeQuantities as ArrayList<Int>)
                    startActivity(intent)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    requireContext(),
                    "Order making Failed. Please try again😉",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    private fun retrieveCartItems() {
        // database reference to the Firebase
        database = FirebaseDatabase.getInstance()
        userId = auth.currentUser?.uid ?: ""
        val coffeeReference: DatabaseReference =
            database.reference.child("user").child(userId).child("CartItems")

        // list to store cart items
        coffeeNames = mutableListOf()
        coffeePrices = mutableListOf()
        coffeeDescriptions = mutableListOf()
        coffeeImagesUri = mutableListOf()
        coffeeIngredients = mutableListOf()
        quantity = mutableListOf()

        // fetch data from the database
        coffeeReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (coffeeSnapshot in snapshot.children) {
                    //get the cartItems object from the child node
                    val cartItems = coffeeSnapshot.getValue(CartItems::class.java)

                    // add cart items details to the list
                    cartItems?.coffeeName?.let { coffeeNames.add(it) }
                    cartItems?.coffeePrice?.let { coffeePrices.add(it) }
                    cartItems?.coffeeDescription?.let { coffeeDescriptions.add(it) }
                    cartItems?.coffeeImage?.let { coffeeImagesUri.add(it) }
                    cartItems?.coffeeQuantity?.let { quantity.add(it) }
                    cartItems?.coffeeIngredient?.let { coffeeIngredients.add(it) }
                }

                setAdapter()
            }

            private fun setAdapter() {
                cartAdapter = CartAdapter(
                    requireContext(),
                    coffeeNames,
                    coffeePrices,
                    coffeeDescriptions,
                    coffeeImagesUri,
                    quantity,
                    coffeeIngredients
                )
                binding.cartRecyclerView.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.cartRecyclerView.adapter = cartAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "data not fetch", Toast.LENGTH_SHORT).show()
            }

        })
    }

}