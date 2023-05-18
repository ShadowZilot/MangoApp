package com.example.mangoapp.registration.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mangoapp.GLOBAL_CLIENT
import com.example.mangoapp.R
import com.example.mangoapp.base.ResultData
import com.example.mangoapp.registration.data.RegistrationCloud
import com.example.mangoapp.sign_in.cloud.SignInData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RegistrationViewModel(
    private val mRegistration: RegistrationCloud
) : ViewModel() {

    suspend fun register(
        phone: String, name: String,
        username: String
    ): Flow<ResultData<SignInData>> {
        return flow {
            emit(object : ResultData<SignInData>(mIsLoading = true) {})
            emit(
                try {
                    val data = mRegistration.register(phone, name, username)
                    object : ResultData<SignInData>(mData = data) {}
                } catch (e: Exception) {
                    object : ResultData<SignInData>(
                        mMessage = R.string.something_went_wrong
                    ) {}
                }
            )
        }.flowOn(Dispatchers.IO)
    }

    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RegistrationViewModel(
                RegistrationCloud.Base(
                    GLOBAL_CLIENT
                )
            ) as T
        }
    }
}