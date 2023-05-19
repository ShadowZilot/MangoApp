package com.example.mangoapp.registration.data

import android.content.Context
import com.example.mangoapp.sign_in.cloud.SignInData

interface UserAuthData : SignInData.Mapper<Unit> {

    fun signInData(): SignInData

    class Base private constructor(
        context: Context
    ) : UserAuthData {
        private val mPreferences = context.getSharedPreferences(
            "auth_data",
            Context.MODE_PRIVATE
        )

        override fun signInData(): SignInData {
            return SignInData(
                mPreferences.getString("refresh_token", "") ?: "",
                mPreferences.getString("access_token", "") ?: "",
                mPreferences.getLong("user_id", -1),
                true
            )
        }

        override fun map(
            refreshToken: String,
            accessToken: String,
            userId: Long,
            isUserExist: Boolean
        ) {
            mPreferences.edit()
                .putString("refresh_token", refreshToken)
                .putString("access_token", accessToken)
                .putLong("user_id", userId)
                .apply()
        }

        object Instance {
            private var mInstance: UserAuthData? = null

            fun create(context: Context) {
                if (mInstance == null) {
                    mInstance = Base(context)
                }
            }

            operator fun invoke() : UserAuthData {
                return mInstance ?: throw java.lang.Exception()
            }
        }
    }
}