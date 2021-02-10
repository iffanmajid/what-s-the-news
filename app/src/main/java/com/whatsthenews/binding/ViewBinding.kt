package com.whatsthenews.binding

import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.skydoves.whatif.whatIfNotNullOrEmpty
import com.whatsthenews.R
import java.text.SimpleDateFormat
import java.util.*

object ViewBinding {

    @JvmStatic
    @BindingAdapter("toast")
    fun bindToast(view: View, text: String?) {
        text.whatIfNotNullOrEmpty {
            Toast.makeText(view.context, it, Toast.LENGTH_SHORT).show()
        }
    }

    @JvmStatic
    @BindingAdapter("loadImage")
    fun bindLoadImage(view: AppCompatImageView, url: String) {
        Glide.with(view.context)
            .load(url)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("formatTime")
    fun bindFormatTime(view: TextView, epoch: Long) {
        val date = SimpleDateFormat("h:mm a MMM d").format(Date(epoch*1000))
        view.text = date
    }
}