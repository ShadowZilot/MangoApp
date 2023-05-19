package com.example.mangoapp.profile.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.mangoapp.R
import com.example.mangoapp.base.BaseFragment
import com.example.mangoapp.base.ResultLogic
import com.example.mangoapp.databinding.ProfileFragmentBinding
import com.example.mangoapp.profile.data.ProfileData
import com.example.mangoapp.profile.domain.ProfileViewModel
import com.squareup.picasso.Picasso


class ProfileFragment : BaseFragment<ProfileFragmentBinding>(R.layout.profile_fragment),
    ResultLogic<ProfileData>, ProfileData.Mapper<Unit> {
    override val mBinding: ProfileFragmentBinding by lazy {
        ProfileFragmentBinding.bind(view ?: throw Exception())
    }
    private val mViewModel by viewModels<ProfileViewModel> {
        ProfileViewModel.Factory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            mViewModel.profileData().collect { it.map(this@ProfileFragment) }
        }
    }

    override fun doIfLoading() {
        mBinding.loadingCover.visibility = View.VISIBLE
    }

    override fun doIfSuccess(data: ProfileData) {
        mBinding.loadingCover.visibility = View.GONE
        data.map(this)
    }

    override fun doIfFailure(message: Int) {
        mBinding.loadingCover.visibility = View.GONE
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun map(
        id: Long,
        name: String,
        username: String,
        birthdate: String,
        phone: String,
        avatar: String
    ) {
        Picasso.get().load(
            avatar
        ).into(mBinding.userPhoto)
        mBinding.nameLabel.text = name
        mBinding.usernameLabel.text = username
        mBinding.birthdateLabel.text = birthdate
        mBinding.phoneLabel.text = phone
    }
}
