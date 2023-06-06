package com.example.privatealbum.password

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.privatealbum.R
import com.example.privatealbum.databinding.FragmentUnlockBinding
import com.example.privatealbum.getResourceColor

class UnlockFragment : Fragment() {
    lateinit var binding: FragmentUnlockBinding

    //原始密码
    private var orgPassword:String? = null
    private var tempPassword = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentUnlockBinding.inflate(inflater,container,false)
        requireActivity()
            .window
            .statusBarColor = getResourceColor(requireActivity(),R.color.match_pink2)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //提取密码
        getPassword(requireActivity(),lifecycleScope){
            orgPassword = it
            if (orgPassword == null){
                binding.alertTextView.text = "请设置密码图案"
            }else{
                binding.alertTextView.text = "轻挥之解锁密码图案"
            }
        }

        var passwordStatus = PasswordStatus.NORMAL
        binding.onlockView.onPasswordFinishListener = { password ->
            if (orgPassword == null){
                //没有密码 设置密码或
                if(tempPassword.isEmpty()){
                    //第一次设置密码图案
                    tempPassword = password
                    binding.alertTextView.text = "请确认密码图案"
                }else{
                    //确认密码图案
                    if (tempPassword == password){
                        //密码设置成功
                        savePassword(requireActivity(),password,lifecycleScope)
                        binding.alertTextView.text = "设置密码成功"
                        passwordStatus = PasswordStatus.NORMAL
                        //跳转到主页

                    }else{
                        //密码确认失败
                        binding.alertTextView.text = "两次密码不一致，请重新绘制"
                        tempPassword = ""
                        passwordStatus = PasswordStatus.ERROR
                    }
                }
            }else{
                if (orgPassword == password){
                    //密码正确
                    passwordStatus = PasswordStatus.NORMAL
                    binding.alertTextView.text = "密码解锁成功"
                    //跳转
                }else{
                    //密码不正确
                    passwordStatus = PasswordStatus.ERROR
                    binding.alertTextView.text = "密码解锁失败，请重新输入"
                }
            }

            passwordStatus
        }
    }
}