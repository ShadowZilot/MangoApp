package com.example.mangoapp.registration.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mangoapp.R
import com.example.mangoapp.base.BaseFragment
import com.example.mangoapp.base.ResultLogic
import com.example.mangoapp.base.navigateWithoutBack
import com.example.mangoapp.databinding.RegistrationFragmentBinding
import com.example.mangoapp.phone_codes.CountriesCode
import com.example.mangoapp.registration.data.UserAuthData
import com.example.mangoapp.registration.domain.RegistrationViewModel
import com.example.mangoapp.registration.domain.UsernameErrorWatch
import com.example.mangoapp.sign_in.cloud.SignInData
import com.example.mangoapp.sign_in.ui.CountryAdapter
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.redmadrobot.inputmask.MaskedTextChangedListener

class RegistrationFragment : BaseFragment<RegistrationFragmentBinding>(
    R.layout.registration_fragment
), ResultLogic<SignInData> {

    override val mBinding: RegistrationFragmentBinding by lazy {
        RegistrationFragmentBinding.bind(view ?: throw Exception())
    }
    private val mViewModel by viewModels<RegistrationViewModel> {
        RegistrationViewModel.Factory()
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
        mBinding.registrationButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                mViewModel.register(
                    phoneNumber ?: "",
                    mBinding.registrationNameField.text.toString(),
                    mBinding.registrationUsernameField.text.toString()
                ).collect { it.map(this@RegistrationFragment) }
            }
        }
    }

    override fun doIfLoading() {
        mBinding.loadingCover.visibility = View.VISIBLE
    }

    override fun doIfSuccess(data: SignInData) {
        mBinding.loadingCover.visibility = View.GONE
        findNavController().navigateWithoutBack(
            R.id.action_registrationFragment_to_profileFragment
        )
    }

    override fun doIfFailure(message: Int) {
        mBinding.loadingCover.visibility = View.GONE
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}