package com.example.mangoapp.profile.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mangoapp.GLOBAL_CLIENT
import com.example.mangoapp.R
import com.example.mangoapp.base.ResultData
import com.example.mangoapp.profile.data.ProfileCloud
import com.example.mangoapp.profile.data.ProfileData
import com.example.mangoapp.registration.data.UserAuthData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProfileViewModel(
    private val mProfileCloud: ProfileCloud
) : ViewModel() {

    suspend fun profileData() : Flow<ResultData<ProfileData>> {
        return flow {
            emit(object : ResultData<ProfileData>(mIsLoading = true) {})
            emit(
                try {
                    val data = mProfileCloud.profileData()
                    object : ResultData<ProfileData>(mData = data) {}
                } catch (e: Exception) {
                    object : ResultData<ProfileData>(mMessage = R.string.something_went_wrong) {}
                }
            )
        }.flowOn(Dispatchers.IO)
    }

    class Factory : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProfileViewModel(
                ProfileCloud.Base(
                    GLOBAL_CLIENT,
                    UserAuthData.Base.Instance.invoke()
                )
            ) as T
        }
    }
}