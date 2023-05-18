package com.example.mangoapp.jwt

import com.example.mangoapp.BASE_URL
import com.example.mangoapp.sign_in.cloud.SignInData
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

interface CheckJwt : SignInData.Mapper<Boolean> {

    class Base(
        private val mClient: OkHttpClient
    ) : CheckJwt {

        override fun map(
            refreshToken: String,
            accessToken: String,
            userId: Long,
            isUserExist: Boolean
        ): Boolean {
            val response = mClient.newCall(Request.Builder()
                .addHeader("Authorization", "Bearer $accessToken")
                .url("$BASE_URL/users/check-jwt/")
                .build()).execute()
            return if (response.isSuccessful) {
                val body = JSONObject(response.body!!.string())
                body.getBoolean("is_valid")
            } else {
                throw Exception()
            }
        }
    }
}