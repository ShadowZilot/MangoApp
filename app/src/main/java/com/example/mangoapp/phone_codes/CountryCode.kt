package com.example.mangoapp.phone_codes

import com.google.i18n.phonenumbers.PhoneNumberUtil
import java.util.Locale

data class CountryCode(
    private val mCountryCode: String,
    private val mFlag: String,
    private val mName: String,
    private val mCode: Int
) {
    constructor(countryCode: String) : this(
        countryCode,
        FlagProvider.Base().flag(countryCode),
        Locale("", countryCode).displayName,
        PhoneNumberUtil.getInstance().getCountryCodeForRegion(countryCode)
    )

    fun <T> map(mapper: Mapper<T>) = mapper.map(
        mCountryCode,
        mFlag,
        mName,
        mCode
    )

    interface Mapper<T> {
        fun map(
            countryCode: String,
            flag: String,
            name: String,
            code: Int
        ) : T
    }
}