package com.example.mangoapp.sign_in.ui

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.mangoapp.phone_codes.CountryCode
import com.example.mangoapp.phone_codes.CountryCodeToString
import com.example.mangoapp.phone_codes.CountryToShortString
import com.example.mangoapp.phone_codes.IsSameCountryCode

class CountryAdapter(
    context: Context,
    private val mCountries: List<CountryCode>,
    private val mSpinner: Spinner
) : ArrayAdapter<String>(context,
    android.R.layout.simple_spinner_dropdown_item) {

    fun searchPosition(countryCode: String) : Int {
        val comparator = IsSameCountryCode(countryCode)
        return mCountries.indexOf(mCountries.find {
            it.map(comparator)
        })
    }

    override fun getItem(position: Int): String {
        return if (position == mSpinner.selectedItemPosition) {
            mCountries[position].map(CountryToShortString())
        } else {
            mCountries[position].map(CountryCodeToString())
        }
    }

    override fun getCount(): Int {
        return mCountries.size
    }
}