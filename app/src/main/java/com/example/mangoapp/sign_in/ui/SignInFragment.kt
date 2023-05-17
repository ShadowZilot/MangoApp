package com.example.mangoapp.sign_in.ui

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
import com.example.mangoapp.databinding.SignInFragmentBinding
import com.example.mangoapp.phone_codes.CountriesCode
import com.example.mangoapp.sign_in.domain.SignInViewModel
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.redmadrobot.inputmask.MaskedTextChangedListener.Companion.installOn
import java.util.*


class SignInFragment : BaseFragment<SignInFragmentBinding>(R.layout.sign_in_fragment),
    ResultLogic<Boolean> {

    override val mBinding: SignInFragmentBinding by lazy {
        SignInFragmentBinding.bind(view ?: throw Exception())
    }
    private val mViewModel by viewModels<SignInViewModel> {
        SignInViewModel.Factory()
    }
    private val spinnerArrayAdapter by lazy {
        CountryAdapter(
            requireContext(),
            CountriesCode.Base().codes(),
            mBinding.countryCodes
        )
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
        mBinding.countryCodes.adapter = spinnerArrayAdapter
        mBinding.signInPhoneField.hint = listener.placeholder()
        mBinding.countryCodes.setSelection(
            spinnerArrayAdapter.searchPosition(Locale.getDefault().country)
        )
        mBinding.signInButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                mViewModel.sendAuthCode(
                    mBinding.signInPhoneField.text.toString(),
                    spinnerArrayAdapter.selectedPhoneCode()
                ).collect { it.map(this@SignInFragment) }
            }
        }
    }

    override fun doIfLoading() {
        mBinding.loadingCover.visibility = View.VISIBLE
    }

    override fun doIfSuccess(data: Boolean) {
        mBinding.loadingCover.visibility = View.GONE
        findNavController().navigateWithoutBack(
            R.id.action_signInFragment_to_verificationFragment,
            Bundle().apply {
                putString(
                    "phone",
                    "+${spinnerArrayAdapter.selectedPhoneCode()}${
                        mBinding.signInPhoneField.text.toString()
                            .replace("(", "")
                            .replace(")", "")
                            .replace(" ", "")
                            .replace("-", "")
                    }"
                )
            }
        )
    }

    override fun doIfFailure(message: Int) {
        mBinding.loadingCover.visibility = View.GONE
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}