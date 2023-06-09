package com.example.privatealbum.advertisement

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.privatealbum.R
import com.example.privatealbum.databinding.FragmentAdvertisementBinding
import com.example.privatealbum.delayNavigate
import com.example.privatealbum.utils.startAnimationWithListener


class AdvertisementFragment : Fragment() {

    lateinit var binding: FragmentAdvertisementBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentAdvertisementBinding.inflate(
            inflater,container,false)

        //状态栏颜色
        //requireActivity().window.statusBarColor = Color.parseColor("#E5FCFC")

        Glide
            .with(this)
            .load(Network.fetchImage())
            .into(binding.advImageView)


        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.timeoutJumpView.onClickListener = {
            it.stopTimer()
        }
    }


    override fun onResume() {
        super.onResume()
        //开屏缩小动画
        binding.advImageView.startAnimationWithListener(R.anim.image_scale_anim)
        binding.timeoutJumpView.startTimer(3000, onAnimatorEnd = {

            findNavController().delayNavigate(
                R.id.action_advertisementFragment_to_unlockFragment,
                lifecycleScope
            )
        })
    }
}