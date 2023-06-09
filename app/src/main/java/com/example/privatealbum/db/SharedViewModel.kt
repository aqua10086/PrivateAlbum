package com.example.privatealbum.db

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.privatealbum.DEFAULT_COVER_URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

//与viewModel的区别是强制传一个参数,拥有application即拥有context
class SharedViewModel(application: Application)
    :AndroidViewModel(application){
    //保存所有相册信息
    var imageAlbumList = MutableLiveData<List<Album>>(emptyList())
    var videoAlbumList = MutableLiveData<List<Album>>(emptyList())

    //仓库对象
    val repository = Repository(application.applicationContext)
    //保存当前添加相册的类型
    var type = ALBUM_TYPE_IMAGE
    //相册删除按钮默认不显示 有内容显示
    var shouldShowDeleteInAlbum = MutableLiveData(false)
    private var  deleteAlbumList = arrayListOf<Album>()

    //添加需要删除的相册
    fun addAlbumToDeleteList(album: Album){
        deleteAlbumList.add(album)
        shouldShowDeleteInAlbum.postValue(true)
    }
    fun deleteAlbumFromDeleteList(album: Album){
        deleteAlbumList.remove(album)
        shouldShowDeleteInAlbum.postValue(deleteAlbumList.size > 0)
    }

    //获取相册
    fun loadAlbumsWithType(albumType:Int){
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.loadAlbumWithType(albumType)
            result.collectLatest {
                if (albumType == ALBUM_TYPE_IMAGE){
                    imageAlbumList.postValue(it)
                }else{
                    videoAlbumList.postValue(it)
                }
            }

        }
    }
    //插入相册
    fun addAlbum(name:String,type: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val album = Album(
                0,
                name,
                getApplication<Application>().DEFAULT_COVER_URL,
                0,
                type = type
            )
            repository.addAlbum(album)
        }
    }
}