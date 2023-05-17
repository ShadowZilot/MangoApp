package com.example.mangoapp.phone_codes

class CountryCodeToString : CountryCode.Mapper<String> {

    override fun map(countryCode: String, flag: String, name: String, code: Int) = "+$code $name $flag"
}