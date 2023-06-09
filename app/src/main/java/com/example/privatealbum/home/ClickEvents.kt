package com.example.privatealbum.home

import android.view.View
import com.example.privatealbum.db.SharedViewModel

class ClickEvents {
    fun addAlbum(view: View,model: SharedViewModel){
        AlbumNameDialog.show(
            view.context,
            onOk = { name->
                model.addAlbum(name,model.type)
            }
        )
    }
}