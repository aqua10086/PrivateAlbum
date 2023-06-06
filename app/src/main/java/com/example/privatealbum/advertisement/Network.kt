package com.example.privatealbum.advertisement

import kotlin.random.Random

object Network {
    private val imageList = listOf(
        //保存图片地址
        "https://img1.baidu.com/it/u=1390383800,428797916&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=889",
        "https://img1.baidu.com/it/u=1452848575,2409229605&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=889",
        "https://img0.baidu.com/it/u=2756578123,1422922300&fm=253&fmt=auto&app=120&f=JPEG?w=800&h=1422",
        "https://img2.baidu.com/it/u=2302904975,1715415548&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=707"

)
    /*
        //如果有线程则在()中同步创建线程
        val sharedInstance:Network by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            Network()
        }

     */

    /**暴露给外部的方法，获取随机数**/
    fun fetchImage():String{
        val index = Random.nextInt(imageList.size)
        return imageList[index]
    }
}