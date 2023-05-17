package com.example.mangoapp.sign_in.ui

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.example.mangoapp.R
import com.example.mangoapp.base.BaseFragment
import com.example.mangoapp.databinding.SignInFragmentBinding
import com.example.mangoapp.phone_codes.CountriesCode
import com.example.mangoapp.phone_codes.CountryCodeToString

class SignInFragment : BaseFragment<SignInFragmentBinding>(R.layout.sign_in_fragment) {
    override val mBinding: SignInFragmentBinding by lazy {
        SignInFragmentBinding.bind(view ?: throw Exception())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spinnerArrayAdapter = CountryAdapter(
            requireContext(),
            CountriesCode.Base().codes(),
            mBinding.countryCodes
        )
        mBinding.countryCodes.adapter = spinnerArrayAdapter
    }
}