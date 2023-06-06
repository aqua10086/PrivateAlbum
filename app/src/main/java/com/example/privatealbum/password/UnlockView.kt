package com.example.privatealbum.password

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.privatealbum.dp2pxF
import com.example.privatealbum.postDelay

class UnlockView:View {
    //画笔
    private val dotPaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.argb(150,255,255,255)
    }
    private val linePaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = context.dp2pxF(5)
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
        color = Color.argb(220,255,255,255)
    }
    private val dotRadius = context.dp2pxF(10)
    private val mMargin = context.dp2pxF(5) //与边缘的距离
    private var space = 0f //两点之间的距离
    private var cx = 0f
    private var cy = 0f
    private val dotlist = arrayListOf<DotModel>()//存储每个点的模型数据
    private val dotModel = DotModel() //模型数据
    private var dotRectF = RectF()//每个点的矩形区域
    private var isFirstDot = true //记录上一个点
    private var linePath = Path() //连接线
    private var moveblePath:Path? = null  //某个点到触摸区域 路径
    private var currentDot:DotModel? = null
    private val passwordBuilder = StringBuilder() //记录密码
    var onPasswordFinishListener:((String) -> PasswordStatus)?=null//密码回调

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context,
        attrs,
        defStyleAttr)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        space = (width -2*mMargin -6*dotRadius)/2
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.let {
            //绘制九个点
            for (i in 0..2){
                for (j in 0..2){
                    cx = mMargin+dotRadius+j*(dotRadius*2+space)
                    cy = mMargin+dotRadius+i*(dotRadius*2+space)
                    it.drawCircle(cx,cy,dotRadius,dotPaint)
                    //创建这个点的model
                    dotModel.number = 1 + i*3 +j
                    dotModel.cx = cx
                    dotModel.cy = cy
                    dotModel.radius = dotRadius
                    dotlist.add(dotModel.copy())
                }
            }


            //绘制路径
            it.drawPath(linePath,linePaint)
            //绘制移动路线
            if (moveblePath != null){
                it.drawPath(moveblePath!!,linePaint)

            }
        }

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_DOWN -> {
                val model = findDot(event.x,event.y)
                dotlist.forEach {
                    Log.v("hjb","$it")
                }
                Log.v("hjb","$model")
                if (model != null){
                    //确定线的起点
                    linePath.moveTo(model.cx,model.cy)
                    model.state = DotState.SELECTED
                    currentDot = model
                    isFirstDot = false
                    passwordBuilder.append(model.number)
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val model = findDot(event.x,event.y)
                if (model != null){
                    if (isFirstDot){
                        linePath.moveTo(model.cx,model.cy)
                        model.state = DotState.SELECTED
                        currentDot = model
                        isFirstDot = false
                        passwordBuilder.append(model.number)

                    }else{
                        if (model.state == DotState.NORMAL){
                            linePath.lineTo(model.cx,model.cy)//从上一个点到这个点的中心连成一条线
                            linePath.moveTo(model.cx,model.cy)//当前点作为下个点的起点
                            model.state = DotState.SELECTED
                            currentDot = model
                            moveblePath = null
                            passwordBuilder.append(model.number)

                            invalidate()
                        }
                    }
                }
                else{
                    if (!isFirstDot){
                        moveblePath = Path()
                        moveblePath?.moveTo(currentDot!!.cx,currentDot!!.cy)
                        //从上一个点到当前琢磨点绘制一条线
                        moveblePath?.lineTo(event.x,event.y)
                        invalidate()
                    }

                }
            }
            MotionEvent.ACTION_UP -> {
                if (onPasswordFinishListener != null){
                    val result = onPasswordFinishListener!!(passwordBuilder.toString())
                    if (result == PasswordStatus.NORMAL){
                        postDelay(500){
                            clear()
                        }
                    }else{
                        linePaint.color = Color.parseColor("#D83E32")
                        moveblePath = null
                        invalidate()
                        postDelay(1000){
                            linePaint.color = Color.argb(220,255,255,255)
                            clear()
                        }
                    }
                }
            }
        }
        return true
    }

    private fun clear(){
        linePath = Path()
        moveblePath = Path()
        passwordBuilder.clear()
        invalidate()

        dotlist.forEach {
            it.state = DotState.NORMAL
        }

        isFirstDot = true
        currentDot = null
    }

    //查找xy是否在某个点里
    private fun findDot(x:Float,y:Float):DotModel?{
        dotlist.forEach {  model ->
            if (model.getRect().contains(x,y)){
                return model
            }
        }
        return null
    }
}