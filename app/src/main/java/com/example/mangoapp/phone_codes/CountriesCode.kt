package com.example.mangoapp.phone_codes

import com.google.i18n.phonenumbers.PhoneNumberUtil


interface CountriesCode {

    fun codes() : List<CountryCode>

    class Base: CountriesCode {

        override fun codes(): List<CountryCode> {
            val phoneUtil = PhoneNumberUtil.getInstance()
            val regions = phoneUtil.supportedRegions
            return mutableListOf<CountryCode>().apply {
                for (code in regions) {
                    add(CountryCode(code))
                }
            }.sortedBy {
                it.map(object : CountryCode.Mapper<String> {
                    override fun map(countryCode: String, flag: String, name: String, code: Int) = name
                })
            }
        }
    }
}