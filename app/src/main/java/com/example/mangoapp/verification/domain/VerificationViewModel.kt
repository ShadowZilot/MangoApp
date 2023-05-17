package com.example.mangoapp.verification.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mangoapp.GLOBAL_CLIENT
import com.example.mangoapp.R
import com.example.mangoapp.base.ResultData
import com.example.mangoapp.sign_in.cloud.SignInCloud
import com.example.mangoapp.sign_in.cloud.SignInData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class VerificationViewModel(
    private val mSignIn: SignInCloud
) : ViewModel() {

    suspend fun checkAuthCode(phone: String, code: String): Flow<ResultData<SignInData>> {
        return flow {
            emit(object : ResultData<SignInData>(mIsLoading = true) {})
            emit(
                try {
                    val data = mSignIn.checkAuthCode(
                        phone,
                        code
                    )
                    object : ResultData<SignInData>(mData = data) {}
                } catch (e: Exception) {
                    object : ResultData<SignInData>(mMessage = R.string.wrong_code) {}
                }
            )
        }.flowOn(Dispatchers.IO)
    }

    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return VerificationViewModel(SignInCloud.Base(GLOBAL_CLIENT)) as T
        }
    }
}