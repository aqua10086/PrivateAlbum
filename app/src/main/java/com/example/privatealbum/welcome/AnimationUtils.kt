package com.example.privatealbum.utils

import android.animation.ObjectAnimator
import android.content.Context
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import java.time.OffsetDateTime

//监听动画
fun Animation.setAnimationStatusChangeListener(
    onStart:(Animation?) -> Unit = {this},
    onEnd:(Animation?) -> Unit = {this},
    onRepeat:(Animation?) -> Unit = {this}
){
    this.setAnimationListener(object:Animation.AnimationListener{
        override fun onAnimationStart(animation: Animation?) {
            onStart(animation)
        }

        override fun onAnimationEnd(animation: Animation?) {
            //定时切换到下一个界面
            onEnd(animation)
        }

        override fun onAnimationRepeat(animation: Animation?) {
            onRepeat(animation)
        }
    })
}

//启动动画的同时监听动画
fun View.startAnimationWithListener(
    resId:Int,
    onStart:(Animation?) -> Unit = {this},
    onEnd:(Animation?) -> Unit = {this},
    onRepeat:(Animation?) -> Unit = {this}
){
    //加载动画
    val animation = AnimationUtils.loadAnimation(this.context,resId)
    animation.setAnimationStatusChangeListener(
        onStart = { onStart(it)},
        onEnd = {onEnd(it)},
        onRepeat = {onRepeat(it)}
    )
    //启动动画
    this.startAnimation(animation)
}

//左右摆动的动画效果
fun View.startShakeAnimation(offsetX:Float,time:Long){

    ObjectAnimator.ofFloat(
        this,
        "translationX",
        0f,
        -offsetX,
        2*offsetX,
        0f
    ).apply {
        duration = time
        repeatCount = 3
        repeatMode = ObjectAnimator.RESTART
        //加速
        interpolator = AccelerateInterpolator()
        start()
    }
}