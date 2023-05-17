package com.example.mangoapp.sign_in.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.mangoapp.R
import com.example.mangoapp.base.BaseFragment
import com.example.mangoapp.databinding.SignInFragmentBinding
import com.example.mangoapp.phone_codes.CountriesCode
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.redmadrobot.inputmask.MaskedTextChangedListener.Companion.installOn
import java.util.Locale


class SignInFragment : BaseFragment<SignInFragmentBinding>(R.layout.sign_in_fragment) {

    override val mBinding: SignInFragmentBinding by lazy {
        SignInFragmentBinding.bind(view ?: throw Exception())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listener: MaskedTextChangedListener = installOn(
            mBinding.signInPhoneField,
            "([000]) [000]-[00]-[00]",
            object : MaskedTextChangedListener.ValueListener {
                override fun onTextChanged(
                    maskFilled: Boolean,
                    extractedValue: String,
                    formattedValue: String,
                    tailPlaceholder: String
                ) {
                    Log.d("TAG", extractedValue)
                    Log.d("TAG", maskFilled.toString())
                }
            }
        )
        val spinnerArrayAdapter = CountryAdapter(
            requireContext(),
            CountriesCode.Base().codes(),
            mBinding.countryCodes
        )
        mBinding.countryCodes.adapter = spinnerArrayAdapter
        mBinding.signInPhoneField.hint = listener.placeholder()
        mBinding.countryCodes.setSelection(
            spinnerArrayAdapter.searchPosition(Locale.getDefault().country)
        )
    }
}