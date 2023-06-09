package com.example.privatealbum.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Album::class,ThumbImage::class],
    version = 1,
    exportSchema = false //是否导出
    )
abstract class AlbumDatabase:RoomDatabase() {
    //获取一系列数据库访问的接口实现类
    abstract fun albumDao():AlbumDao

    //访问静态属性，提供单例对象
    //有就返回，没有就加锁再返回
    companion object{
        private var INSTANCE:AlbumDatabase ? = null
        fun getInstance(context: Context):AlbumDatabase{
            if (INSTANCE != null){
                return INSTANCE!!
            }
            synchronized(this){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(
                        context,
                        AlbumDatabase::class.java,
                        "album_db"
                    ).build()
                }
                return INSTANCE!!
            }
        }
    }
}