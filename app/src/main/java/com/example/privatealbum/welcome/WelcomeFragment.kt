package com.example.privatealbum.welcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.privatealbum.R
import com.example.privatealbum.databinding.FragmentWelcomeBinding
import com.example.privatealbum.utils.setAnimationStatusChangeListener
import com.example.privatealbum.utils.startAnimationWithListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class WelcomeFragment : Fragment() {
    lateinit var binding: FragmentWelcomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentWelcomeBinding.inflate(
            inflater,container,false
        )
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        binding.titleTextView.startAnimationWithListener(
            R.anim.text_up_scale_anim,
            onEnd = { navigateToNextScreen() }
        )
        /*将动画的启动和监听同时封装

        val animation = AnimationUtils.loadAnimation(requireActivity(), R.anim.text_up_scale_anim)
        binding.titleTextView.startAnimation(animation)
                /* 将这个方法封装，可以写出一个更简单的形式
                //继承接口
                animation.setAnimationListener(object:Animation.AnimationListener{

                    override fun onAnimationStart(animation: Animation?) {
                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        //定时切换到下一个界面
                        navigateToNextScreen()
                    }

                    override fun onAnimationRepeat(animation: Animation?) {
                    }
                })

            }

                 */
                animation.setAnimationStatusChangeListener(
            onEnd = {navigateToNextScreen()}
        )
    }

    */
    }

    fun navigateToNextScreen() {
        //3s后切换
        lifecycleScope.launch(Dispatchers.IO) {
            delay(1500)
            withContext(Dispatchers.Main) {
                findNavController().navigate(R.id.action_welcomeFragment_to_advertisementFragment)
            }
        }
    }
}