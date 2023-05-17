package com.example.mangoapp.sign_in.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mangoapp.GLOBAL_CLIENT
import com.example.mangoapp.R
import com.example.mangoapp.base.ResultData
import com.example.mangoapp.sign_in.cloud.SignInCloud
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SignInViewModel(
    private val mSignIn: SignInCloud
) : ViewModel() {

    suspend fun sendAuthCode(phone: String, phoneCode: Int) : Flow<ResultData<Boolean>> {
        return flow {
            emit(object : ResultData<Boolean>(mIsLoading = true) {})
            emit(
                try {
                    val data = mSignIn.sendAuth(
                        "+$phoneCode${phone
                            .replace("(", "")
                            .replace(")", "")
                            .replace(" ", "")
                            .replace("-", "")
                        }"
                    )
                    object : ResultData<Boolean>(mData = data) {}
                } catch (e: Exception) {
                    object : ResultData<Boolean>(mMessage = R.string.something_went_wrong) {}
                }
            )
        }.flowOn(Dispatchers.IO)
    }

    class Factory: ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SignInViewModel(SignInCloud.Base(GLOBAL_CLIENT)) as T
        }
    }
}