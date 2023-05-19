package com.example.mangoapp.registration.data

import com.example.mangoapp.BASE_URL
import com.example.mangoapp.jwt.CheckJwt
import com.example.mangoapp.sign_in.cloud.SignInData
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

interface RegistrationCloud {

    suspend fun register(phone: String, name: String, username: String) : SignInData

    class Base(
        private val mClient: OkHttpClient
    ) : RegistrationCloud {

        override suspend fun register(phone: String, name: String,
                                      username: String): SignInData {
            val data = JSONObject().apply {
                put("phone", phone)
                put("name", name)
                put("username", username)
            }
            val mediaType = "application/json; charset=utf-8".toMediaType()
            val requestBody = data.toString().toRequestBody(mediaType)
            val response = mClient.newCall(Request.Builder()
                .post(requestBody)
                .url("$BASE_URL/users/register/")
                .build()).execute()
            return if (response.isSuccessful) {
                val body = JSONObject(response.body!!.string())
                val authData = SignInData(body)
                response.close()
                if (authData.map(CheckJwt.Base(mClient))) {
                    authData
                } else {
                    throw Exception()
                }
            } else {
                throw Exception()
            }
        }
    }
}