package com.example.mangoapp.phone_codes

interface FlagProvider {

    fun flag(countryCode: String): String

    class Base: FlagProvider {

        override fun flag(countryCode: String): String {
            val flagOffset = 0x1F1E6
            val asciiOffset = 0x41
            val firstChar = Character.codePointAt(
                countryCode, 0) - asciiOffset + flagOffset
            val secondChar = Character.codePointAt(
                countryCode, 1) - asciiOffset + flagOffset
            return (String(Character.toChars(firstChar))
                    + String(Character.toChars(secondChar)))
        }
    }
}