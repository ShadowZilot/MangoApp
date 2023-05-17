package com.example.mangoapp.phone_codes

class IsSameCountryCode(
    private val mComparableCountryCode: String
) : CountryCode.Mapper<Boolean> {

    override fun map(countryCode: String, flag: String,
                     name: String, code: Int) = mComparableCountryCode == countryCode
}