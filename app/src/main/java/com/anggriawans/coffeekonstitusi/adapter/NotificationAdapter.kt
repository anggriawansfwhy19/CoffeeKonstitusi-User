package com.anggriawans.coffeekonstitusi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anggriawans.coffeekonstitusi.databinding.NotificationItemBinding

class NotificationAdapter(
    private var notification: ArrayList<String>,
    private var notificationImage: ArrayList<Int>
) : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding =
            NotificationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val currentNotification = notification[position]
        val currentNotificationImage = notificationImage[position]

        holder.bind(currentNotification, currentNotificationImage)
    }

    override fun getItemCount(): Int {
        return notification.size
    }

    inner class NotificationViewHolder(private val binding: NotificationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(notificationText: String, notificationImageResource: Int) {
            binding.apply {
                // Set data ke tampilan di sini
                notificationTV.text = notificationText
                notificationImage.setImageResource(notificationImageResource)
                // Implementasikan aksi jika diperlukan
            }
        }
    }
}
