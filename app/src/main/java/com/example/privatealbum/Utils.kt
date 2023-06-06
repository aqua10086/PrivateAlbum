package com.example.privatealbum

import android.content.Context
import android.graphics.RectF
import android.os.Build
import androidx.navigation.NavController
import kotlinx.coroutines.*
import java.time.Duration

fun Context.dp2pxI(dp:Int):Int {
    return (resources.displayMetrics.density*dp).toInt()

}

fun Context.dp2pxF(dp: Int):Float{
    return resources.displayMetrics.density*dp
}

fun SdkMoreThanM():Boolean{
    return Build.VERSION.SDK_INT > Build.VERSION_CODES.M
}

fun getResourceColor(context: Context,resId:Int):Int{
    return if (SdkMoreThanM()){
        context.resources.getColor(R.color.match_pink1,null)
    }else{
        context.resources.getColor(R.color.match_pink1)
    }
}

fun NavController.delayNavigate(id:Int,scope:CoroutineScope){
    scope.launch(Dispatchers.IO) {
        delay(500)
        withContext(Dispatchers.Main){
            navigate(id)
        }
    }
}

//实现Rect copy
fun RectF.copy(srcRectF: RectF){
    left = srcRectF.left
    top = srcRectF.top
    right = srcRectF.right
    bottom = srcRectF.bottom
}

//延迟操作密码
fun postDelay(duration: Long,callBack:()->Unit = {}){
    CoroutineScope(Dispatchers.IO).launch{
        delay(duration)
        withContext(Dispatchers.Main){
            callBack()
        }
    }
}