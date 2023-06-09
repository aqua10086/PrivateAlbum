package com.example.privatealbum.db

import androidx.room.*
import java.util.concurrent.Flow

@Dao
interface AlbumDao {
    //创建相册 数据库操作都耗时suspend
    //@Insert(onConflict = OnConflictStrategy.)解决冲突
    @Insert
    fun insertAlbum(album: Album)
    //删除一个相册
    @Delete
    fun deleteAlbum(album: Album)
    //删除多个相册 可变数组
    @Delete
    fun deleteAlbums(vararg album: Album)
    //更新相册
    @Update
    fun updateAlbum(album: Album)
    //查询所有相册信息 查询信息不要用suspend修饰 :冒号表示拼接一个参数
    @Query("select * from Album where type = :type")
    fun getAllAlbumsWithType(type:Int):kotlinx.coroutines.flow.Flow<List<Album>>

    /**------------------------相片--------------------------**/
    //插入一张图
    @Insert
    fun insertImage(thumbImage: ThumbImage)
    //插入多张图
    @Insert
    fun insertImages(vararg thumbImage: ThumbImage)
    //删除一张图
    @Delete
    fun deleteImage(thumbImage: ThumbImage)
    //删除多张图
    @Delete
    fun deleteImages(vararg thumbImage: ThumbImage)


}