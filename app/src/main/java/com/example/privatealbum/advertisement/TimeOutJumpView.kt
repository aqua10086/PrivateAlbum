package com.example.privatealbum.advertisement

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.Gravity.apply
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.animation.addListener
import androidx.core.view.GravityCompat.apply
import com.example.privatealbum.*
import java.time.Duration

class TimeOutJumpView: View {
    private val mStrokeWidth = context.dp2pxF(5)
    private val bgPaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.LTGRAY
        strokeWidth = mStrokeWidth
    }
    //前景画笔
    private val arcPaint = Paint().apply {
        style = Paint.Style.STROKE
        color = getResourceColor(context,R.color.match_pink1)
        strokeWidth = mStrokeWidth

    }
    //绘制文本的画笔
    private val textPaint = Paint().apply{
        color = Color.BLACK
        textSize = context.dp2pxF(15)
    }
    //圆的半径
    private var mRadius = 0f
    //绘制的文本
    private val mText = "跳转"
    //文本的起始点
    private var mTextX = 0f
    private var mTextY = 0f
    //圆弧弧度
    private var sweepAngle = 90f
    //动画对象
    private lateinit var circleProgressAnimator:ValueAnimator
    //点击回调事件
    var onClickListener:((TimeOutJumpView)->Unit)? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context,
        attrs,
        defStyleAttr)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mRadius = (width-2*mStrokeWidth)/2
        val rect = getTextSize(mText,textPaint)
        mTextX = (width - rect.width())/2f
        mTextY = getTextBaseline(textPaint)
    }

    override fun onDraw(canvas: Canvas?) {
        //灰色背景
        super.onDraw(canvas)
        canvas?.let {
            it.drawCircle(
                width/2f,height/2f, mRadius.toFloat(),bgPaint
            )
            it.drawText(mText,mTextX,mTextY,textPaint)
            it.drawArc(0f,0f,width-mStrokeWidth,height-mStrokeWidth,270f,sweepAngle,false,arcPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(event?.action == MotionEvent.ACTION_DOWN){
            //回调事件
            onClickListener?.let {
                it(this)
            }
        }
        return true
    }

    /**测量字符串的尺寸（宽高）**/
    fun getTextSize(text:String,paint: Paint):Rect{
        val rect = Rect()
        paint.getTextBounds(text,0,text.length,rect)
        return rect
    }

    //字体坐标
    fun getTextBaseline(paint: Paint):Float{
        val fontMetrics = paint.fontMetrics
        val bottom = fontMetrics.bottom
        val top = fontMetrics.top
        return height/2f+(bottom+Math.abs(top))/2f-bottom
    }

    fun startTimer(aDuration: Long,onAnimatorEnd:()->Unit={}){
        circleProgressAnimator = ValueAnimator.ofFloat(0f,360f).apply {
            duration = aDuration
            interpolator = LinearInterpolator()
            addUpdateListener {
                sweepAngle = it.animatedValue as Float
                invalidate()
            }
            addListener(onEnd = {
                onAnimatorEnd()
            })
            start()
        }
    }

    fun stopTimer(){
        circleProgressAnimator.cancel()
    }
}