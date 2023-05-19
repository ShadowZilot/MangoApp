package com.example.mangoapp.profile.data

import org.json.JSONObject

data class ProfileData(
    private val mId: Long,
    private val mName: String,
    private val mUsername: String,
    private val mBirthdate: String,
    private val mPhone: String,
    private val mAvatar: String
) {
    constructor(item: JSONObject) : this(
        item.getLong("id"),
        item.getString("name"),
        item.getString("username"),
        item.getString("birthday"),
        item.getString("phone"),
        item.getJSONObject("avatars").getString("avatar")
    )

    fun <T> map(mapper: Mapper<T>) = mapper.map(
        mId,
        mName,
        mUsername,
        mBirthdate,
        mPhone,
        mAvatar
    )

    interface Mapper<T> {
        fun map(
            id: Long,
            name: String,
            username: String,
            birthdate: String,
            phone: String,
            avatar: String
        ) : T
    }
}