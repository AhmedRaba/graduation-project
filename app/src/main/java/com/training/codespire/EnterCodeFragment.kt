package com.training.codespire

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.training.codespire.data.datastore.SharedPreferencesUtil
import com.training.codespire.databinding.FragmentEnterCodeBinding

class EnterCodeFragment : Fragment() {

    private lateinit var binding:FragmentEnterCodeBinding
    private lateinit var sharedPreferencesUtil: SharedPreferencesUtil


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentEnterCodeBinding.inflate(inflater,container,false)
        sharedPreferencesUtil= SharedPreferencesUtil(requireContext())

        val verificationCode=sharedPreferencesUtil.emailCode

        binding.btnSubmitCode.setOnClickListener {
            if (binding.etValidateCode.text.isNullOrEmpty()){
                binding.etValidateCode.error="Please enter the code"
            }
            else if (binding.etValidateCode.text.toString().trim() != verificationCode){
                binding.etValidateCode.error="Please check the code"
            }else{
                findNavController().navigate(R.id.action_enterCodeFragment_to_changePasswordFragment)
            }

        }





        return binding.root
    }


}