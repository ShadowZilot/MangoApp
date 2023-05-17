package com.example.mangoapp.sign_in.cloud

import org.json.JSONObject

data class SignInData(
    private val mRefreshToken: String,
    private val mAccessToken: String,
    private val mUserId: Long,
    private val mIsUserExist: Boolean
) {
    constructor(item: JSONObject) : this(
        item.getString("refresh_token"),
        item.getString("access_token"),
        item.getLong("user_id"),
        item.getBoolean("is_user_exists")
    )

    fun <T> map(mapper: Mapper<T>) = mapper.map(
        mRefreshToken,
        mAccessToken,
        mUserId,
        mIsUserExist
    )

    interface Mapper<T> {

        fun map(
            refreshToken: String,
            accessToken: String,
            userId: Long,
            isUserExist: Boolean
        ) : T
    }
}