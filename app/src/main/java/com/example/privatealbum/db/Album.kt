package com.example.privatealbum.db


import androidx.room.Entity
import androidx.room.PrimaryKey

const val ALBUM_TYPE_IMAGE = 0
const val ALBUM_TYPE_VIDEO = 1
//默认表名就是类名
//@Entity(tableName = "album_table")
@Entity
data class Album (

    @PrimaryKey(autoGenerate = true)//主键自增长取消，因为是string类型
    val id:Int,
    var albumName:String,
    //@ColumnInfo(name ="cover_url") 表名和属性名的转换
    var coverUrl:String,
    var number:Int,
    val type:Int = ALBUM_TYPE_IMAGE
)

//缩略图表
@Entity
data class ThumbImage(
    @PrimaryKey(autoGenerate = false)
    val imageName:String,
    //外键
    val albumId: Int
)