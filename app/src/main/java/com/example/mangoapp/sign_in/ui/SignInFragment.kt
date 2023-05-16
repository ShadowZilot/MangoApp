package com.example.mangoapp.sign_in.ui

import com.example.mangoapp.R
import com.example.mangoapp.base.BaseFragment
import com.example.mangoapp.databinding.SignInFragmentBinding

class SignInFragment : BaseFragment<SignInFragmentBinding>(R.layout.sign_in_fragment) {
    override val mBinding: SignInFragmentBinding by lazy {
        SignInFragmentBinding.bind(view ?: throw Exception())
    }
}