package com.example.mangoapp.phone_codes

class CountryToShortString : CountryCode.Mapper<String> {

    override fun map(countryCode: String, flag: String, name: String, code: Int) = "+$code $flag"
}