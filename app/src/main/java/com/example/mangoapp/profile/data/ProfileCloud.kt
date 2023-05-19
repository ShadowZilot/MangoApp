package com.example.mangoapp.profile.data

import com.example.mangoapp.BASE_URL
import com.example.mangoapp.registration.data.UserAuthData
import com.example.mangoapp.sign_in.cloud.AccessToken
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

interface ProfileCloud {

    suspend fun profileData(): ProfileData

    class Base(
        private val mClient: OkHttpClient,
        private val mAuthData: UserAuthData
    ) : ProfileCloud {

        override suspend fun profileData(): ProfileData {
            val response = mClient.newCall(
                Request.Builder()
                    .addHeader(
                        "Authorization",
                        "Bearer ${mAuthData.signInData().map(AccessToken())}"
                    )
                    .url("$BASE_URL/users/me/")
                    .build()
            ).execute()
            return if (response.isSuccessful) {
                val body = JSONObject(response.body!!.string())
                ProfileData(body)
            } else {
                throw java.lang.Exception()
            }
        }
    }
}