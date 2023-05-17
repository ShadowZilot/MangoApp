package com.example.mangoapp.sign_in.cloud

class IsUserExist : SignInData.Mapper<Boolean> {
    override fun map(
        refreshToken: String,
        accessToken: String,
        userId: Long,
        isUserExist: Boolean
    ) = isUserExist
}