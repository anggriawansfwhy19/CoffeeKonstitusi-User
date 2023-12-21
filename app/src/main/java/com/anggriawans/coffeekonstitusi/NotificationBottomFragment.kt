package com.anggriawans.coffeekonstitusi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.anggriawans.coffeekonstitusi.adapter.NotificationAdapter
import com.anggriawans.coffeekonstitusi.databinding.FragmentNotificationBottomBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NotificationBottomFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentNotificationBottomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNotificationBottomBinding.inflate(layoutInflater,container,false)
        val notifications = listOf("Your order has been Canceled Successfully","Order has been taken by the driver","Congrats Your Order Placed")
        val notificationImages = listOf(R.drawable.sademoji,R.drawable.truck,R.drawable.congrats)

        val adapter = NotificationAdapter(
            ArrayList(notifications),
            ArrayList(notificationImages)
        )
        binding.notificationRV.layoutManager = LinearLayoutManager(requireContext())
        binding.notificationRV.adapter = adapter
        return binding.root
    }

    companion object {

    }
}