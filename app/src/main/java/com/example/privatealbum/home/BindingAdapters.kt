package com.example.privatealbum.home

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("shouldShow")
fun ImageView.shouldShow(show:Boolean){
    visibility = if(show){
        isEnabled = true
        View.VISIBLE
    }else{
        isEnabled = false
        View.INVISIBLE
    }
}