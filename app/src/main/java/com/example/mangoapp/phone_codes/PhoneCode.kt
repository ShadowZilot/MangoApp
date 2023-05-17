package com.example.mangoapp.phone_codes

class PhoneCode : CountryCode.Mapper<Int> {

    override fun map(countryCode: String, flag: String, name: String, code: Int) = code
}