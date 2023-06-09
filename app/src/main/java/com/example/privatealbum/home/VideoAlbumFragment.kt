package com.example.privatealbum.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.privatealbum.R
import com.example.privatealbum.databinding.FragmentVideoAlbumBinding
import com.example.privatealbum.db.ALBUM_TYPE_VIDEO
import com.example.privatealbum.db.SharedViewModel
import com.example.privatealbum.dp2pxI

class VideoAlbumFragment : Fragment() {
    lateinit var binding:FragmentVideoAlbumBinding
    lateinit var adapter: AlbumAdapter
    val model:SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentVideoAlbumBinding.inflate(inflater)
        model.videoAlbumList.observe(viewLifecycleOwner){
            adapter.setData(it)
            model.loadAlbumsWithType(ALBUM_TYPE_VIDEO)

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = AlbumAdapter()
        binding.recyclerView.layoutManager = GridLayoutManager(requireActivity(),2)
        binding.recyclerView.addItemDecoration(SpacingItemDecoration(requireActivity().dp2pxI(4)))
        binding.recyclerView.adapter = adapter
    }


}