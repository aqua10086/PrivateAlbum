package com.example.privatealbum.password

import android.graphics.RectF

//封装
data class DotModel (
    var number:Int = 0,
    var radius:Float = 0f,
    var state:DotState = DotState.NORMAL,
    var cx:Float = 0f,
    var cy:Float = 0f
){
    fun getRect():RectF{
        return RectF(cx - radius,cy - radius,cx + radius,cy + radius)
    }
}

//记录每个点的状态
enum class DotState{
    NORMAL,SELECTED
}

enum class PasswordStatus{
    NORMAL,ERROR
}