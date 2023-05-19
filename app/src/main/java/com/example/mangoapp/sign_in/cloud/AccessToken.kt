package com.example.mangoapp.sign_in.cloud

class AccessToken : SignInData.Mapper<String> {

    override fun map(
        refreshToken: String,
        accessToken: String,
        userId: Long,
        isUserExist: Boolean
    ) = accessToken
}