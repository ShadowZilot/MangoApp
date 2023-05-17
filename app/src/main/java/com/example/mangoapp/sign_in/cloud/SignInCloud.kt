package com.example.mangoapp.sign_in.cloud

import com.example.mangoapp.BASE_URL
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

interface SignInCloud {

    suspend fun sendAuth(phone: String) : Boolean

    suspend fun checkAuthCode(phone: String, code: String) : SignInData

    class Base(
        private val mClient: OkHttpClient
    ) : SignInCloud {

        override suspend fun sendAuth(phone: String): Boolean {
            val response = mClient.newCall(Request.Builder()
                .post(FormBody.Builder()
                    .add("phone", phone)
                    .build())
                .url("$BASE_URL/users/send-auth-code/")
                .build()).execute()
            return if (response.isSuccessful) {
                val body = JSONObject(response.body!!.string())
                body.getBoolean("is_success").also {
                    response.close()
                }
            } else {
                false
            }
        }

        override suspend fun checkAuthCode(phone: String, code: String): SignInData {
            val data = JSONObject().apply {
                put("phone", phone)
                put("code", code)
            }
            val mediaType = "application/json; charset=utf-8".toMediaType()
            val requestBody = data.toString().toRequestBody(mediaType)
            val response = mClient.newCall(Request.Builder()
                .post(requestBody)
                .url("$BASE_URL/users/check-auth-code/")
                .build()).execute()
            return if (response.isSuccessful) {
                val body = JSONObject(response.body!!.string())
                SignInData(body).also {
                    response.close()
                }
            } else {
                if (response.code == 404) {
                    SignInData("", "",
                        -1, false)
                } else {
                    throw Exception()
                }
            }
        }
    }
}