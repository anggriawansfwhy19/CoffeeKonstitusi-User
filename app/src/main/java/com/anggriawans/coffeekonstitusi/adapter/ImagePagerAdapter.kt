package com.anggriawans.coffeekonstitusi.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.anggriawans.coffeekonstitusi.R

class ImagePagerAdapter(private val context: Context) : PagerAdapter() {
    private val coffeeImages = arrayOf(R.drawable.menu1, R.drawable.menu2, R.drawable.menu3, R.drawable.menu4, R.drawable.menu5)

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(context)
        imageView.setImageResource(coffeeImages[position])
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        container.addView(imageView)
        return imageView
    }

    override fun getCount(): Int = coffeeImages.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view === `object`

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}

