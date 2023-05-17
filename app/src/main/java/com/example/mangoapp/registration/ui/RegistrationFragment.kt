package com.example.mangoapp.registration.ui

import com.example.mangoapp.R
import com.example.mangoapp.base.BaseFragment
import com.example.mangoapp.databinding.RegistrationFragmentBinding

class RegistrationFragment : BaseFragment<RegistrationFragmentBinding>(
    R.layout.registration_fragment) {

    override val mBinding: RegistrationFragmentBinding by lazy {
        RegistrationFragmentBinding.bind(view ?: throw Exception())
    }
}