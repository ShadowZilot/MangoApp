package com.example.mangoapp.registration.domain

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView



interface UsernameErrorWatch {

    fun username() : String

    class Base(
        private val mUsernameField: EditText,
        private val mErrorText: TextView
    ) : UsernameErrorWatch {

        init {
            mUsernameField.addTextChangedListener(
                object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {}

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {}

                    override fun afterTextChanged(s: Editable?) {
                        val text = s.toString()
                        if (text.isEmpty()) {
                            mErrorText.visibility = View.GONE
                        }
                        for (symbol in text) {
                            if (!validateCharacter(symbol)) {
                                mErrorText.visibility = View.VISIBLE
                                break
                            } else {
                                mErrorText.visibility = View.GONE
                            }
                        }
                    }
                }
            )
        }

        override fun username(): String {
            return mUsernameField.text.toString()
        }

        private fun validateCharacter(symbol: Char) : Boolean {
            val symbolCode = symbol.code
            return symbolCode in 'A'.code..'Z'.code
                    || symbolCode in 'a'.code..'z'.code
                    || symbolCode == '-'.code || symbolCode == '_'.code
        }
    }
}