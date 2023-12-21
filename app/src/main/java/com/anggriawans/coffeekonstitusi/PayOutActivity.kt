package com.anggriawans.coffeekonstitusi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.anggriawans.coffeekonstitusi.databinding.ActivityPayOutBinding
import com.anggriawans.coffeekonstitusi.model.OrderDetails
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PayOutActivity : AppCompatActivity() {
    lateinit var binding: ActivityPayOutBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var name: String
    private lateinit var address: String
    private lateinit var phone: String
    private lateinit var totalAmount: String
    private lateinit var coffeeItemName: ArrayList<String>
    private lateinit var coffeeItemPrice: ArrayList<String>
    private lateinit var coffeeItemDescription: ArrayList<String>
    private lateinit var coffeeItemImage: ArrayList<String>
    private lateinit var coffeeItemIngredient: ArrayList<String>
    private lateinit var coffeeItemQuantities: ArrayList<Int>
    private lateinit var databaseReference: DatabaseReference
    private lateinit var userId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayOutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // initialize firebase and user details
        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference()
        // set user data
        setUserData()

        // get user details from Firebase
        val intent = intent
        coffeeItemName = intent.getStringArrayListExtra("CoffeeItemName") as ArrayList<String>
        coffeeItemPrice = intent.getStringArrayListExtra("CoffeeItemPrice") as ArrayList<String>
        coffeeItemDescription =
            intent.getStringArrayListExtra("CoffeeItemDescription") as ArrayList<String>
        coffeeItemImage = intent.getStringArrayListExtra("CoffeeItemImage") as ArrayList<String>
        coffeeItemIngredient =
            intent.getStringArrayListExtra("CoffeeItemIngredient") as ArrayList<String>
        coffeeItemQuantities =
            intent.getIntegerArrayListExtra("CoffeeItemQuantities") as ArrayList<Int>

        totalAmount = "Rp." + calculateTotalAmount().toString()
        // binding.totalAmount.isEnabled = false
        binding.totalAmount.setText(totalAmount)

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.placeMyOrder.setOnClickListener {
            // get data from textview
            name = binding.name.text.toString().trim()
            address = binding.address.text.toString().trim()
            phone = binding.phone.text.toString().trim()
            if (name.isBlank() && address.isBlank() && phone.isBlank()) {
                Toast.makeText(this, "Please Enter All the Details😜", Toast.LENGTH_SHORT).show()
            } else {
                placeOrder()
            }

        }
    }

    private fun placeOrder() {
        userId = auth.currentUser?.uid ?: ""
        val time = System.currentTimeMillis()
        val itemPushKey = databaseReference.child("OrderDetails").push().key
        val orderDetails = OrderDetails(
            userId,
            name,
            coffeeItemName,
            coffeeItemPrice,
            coffeeItemImage,
            coffeeItemQuantities,
            address,
            totalAmount,
            phone,
            time,
            itemPushKey,
            false,
            false
        )
        val orderReference = databaseReference.child("OrderDetails").child(itemPushKey!!)
        orderReference.setValue(orderDetails).addOnSuccessListener {
            val bottomSheetDialog = CongratsBottomSheet()
            bottomSheetDialog.show(supportFragmentManager, "Test")
            removeItemFromCart()
            addOrderToHistory(orderDetails)
        }
            .addOnFailureListener {
                Toast.makeText(this, "failed to order😥", Toast.LENGTH_SHORT).show()
            }
    }

    private fun addOrderToHistory(orderDetails: OrderDetails) {
        databaseReference.child("user").child(userId).child("BuyHistory")
            .child(orderDetails.itemPushKey!!)
            .setValue(orderDetails).addOnSuccessListener {

            }
    }

    private fun removeItemFromCart() {
        val cartItemsReference = databaseReference.child("user").child(userId).child("CartItems")
        cartItemsReference.removeValue()
    }

    private fun calculateTotalAmount(): Int {
        var totalAmount = 0
        for (i in 0 until coffeeItemPrice.size) {
            val price = coffeeItemPrice[i]

            // Hapus karakter "Rp." dan ubah ke tipe data Int
            val priceIntValue = price.replace("Rp.", "").replace(".", "").toInt()

            val quantity = coffeeItemQuantities[i]
            totalAmount += priceIntValue * quantity
        }
        return totalAmount
    }

    private fun setUserData() {
        val user = auth.currentUser
        if (user != null) {
            val userId = user.uid
            val userReference = databaseReference.child("user").child(userId)

            userReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()) {
                        val names = snapshot.child("name").getValue(String::class.java) ?: ""
                        val addresses = snapshot.child("address").getValue(String::class.java) ?: ""
                        val phones = snapshot.child("phone").getValue(String::class.java) ?: ""
                        binding.apply {
                            name.setText(names)
                            address.setText(addresses)
                            phone.setText(phones)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
        }
    }
}