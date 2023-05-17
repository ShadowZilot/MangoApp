package com.example.mangoapp.verification.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mangoapp.R
import com.example.mangoapp.base.BaseFragment
import com.example.mangoapp.base.ResultLogic
import com.example.mangoapp.base.navigateWithoutBack
import com.example.mangoapp.databinding.VerificationCodeFragmentBinding
import com.example.mangoapp.sign_in.cloud.IsUserExist
import com.example.mangoapp.sign_in.cloud.SignInData
import com.example.mangoapp.verification.domain.VerificationViewModel

class VerificationFragment : BaseFragment<VerificationCodeFragmentBinding>(
    R.layout.verification_code_fragment
), ResultLogic<SignInData> {
    override val mBinding: VerificationCodeFragmentBinding by lazy {
        VerificationCodeFragmentBinding.bind(view ?: throw Exception())
    }

    private val mViewModel by viewModels<VerificationViewModel> {
        VerificationViewModel.Factory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mBinding.verificationButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                mViewModel.checkAuthCode(
                    requireArguments().getString("phone") ?: "",
                    mBinding.verificationCodeInput.text.toString()
                ).collect { it.map(this@VerificationFragment) }
            }
        }
    }

    override fun doIfLoading() {
        mBinding.loadingCover.visibility = View.VISIBLE
    }

    override fun doIfSuccess(data: SignInData) {
        mBinding.loadingCover.visibility = View.GONE
        if (data.map(IsUserExist())) {
            Toast.makeText(requireContext(), "Обнял", Toast.LENGTH_SHORT).show()
        } else {
            findNavController().navigateWithoutBack(
                R.id.action_verificationFragment_to_registrationFragment,
                Bundle().apply {
                    putString("phone", requireArguments().getString("phone"))
                }
            )
        }
    }

    override fun doIfFailure(message: Int) {
        mBinding.loadingCover.visibility = View.GONE
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}