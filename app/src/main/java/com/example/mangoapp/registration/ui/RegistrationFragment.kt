package com.example.mangoapp.registration.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.mangoapp.R
import com.example.mangoapp.base.BaseFragment
import com.example.mangoapp.databinding.RegistrationFragmentBinding
import com.example.mangoapp.phone_codes.CountriesCode
import com.example.mangoapp.registration.domain.UsernameErrorWatch
import com.example.mangoapp.sign_in.ui.CountryAdapter
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.redmadrobot.inputmask.MaskedTextChangedListener

class RegistrationFragment : BaseFragment<RegistrationFragmentBinding>(
    R.layout.registration_fragment
) {

    override val mBinding: RegistrationFragmentBinding by lazy {
        RegistrationFragmentBinding.bind(view ?: throw Exception())
    }
    private val spinnerArrayAdapter by lazy {
        CountryAdapter(
            requireContext(),
            CountriesCode.Base().codes(),
            mBinding.countryCodes
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val phoneNumber = requireArguments().getString("phone")
        val listener: MaskedTextChangedListener = MaskedTextChangedListener.installOn(
            mBinding.registrationPhoneField,
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
        val phone = PhoneNumberUtil.getInstance().parse(phoneNumber, "+")
        val countryCode = PhoneNumberUtil.getInstance().getRegionCodesForCountryCode(
            phone.countryCode
        )[0]
        mBinding.countryCodes.isEnabled = false
        mBinding.countryCodes.isClickable = false
        mBinding.countryCodes.adapter = spinnerArrayAdapter
        mBinding.registrationPhoneField.hint = listener.placeholder()
        mBinding.countryCodes.setSelection(
            spinnerArrayAdapter.searchPosition(countryCode)
        )
        mBinding.registrationPhoneField.setText(phone.nationalNumber.toString())
        UsernameErrorWatch.Base(
            mBinding.registrationUsernameField,
            mBinding.errorUsernameFiled
        )
    }
}