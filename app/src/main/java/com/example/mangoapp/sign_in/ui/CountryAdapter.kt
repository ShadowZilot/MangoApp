package com.example.mangoapp.sign_in.ui

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.mangoapp.phone_codes.CountryCode
import com.example.mangoapp.phone_codes.CountryCodeToString
import com.example.mangoapp.phone_codes.CountryToShortString

class CountryAdapter(
    context: Context,
    private val mCountries: List<CountryCode>,
    private val mSpinner: Spinner
) : ArrayAdapter<String>(context,
    android.R.layout.simple_spinner_dropdown_item) {

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