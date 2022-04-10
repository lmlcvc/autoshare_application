package com.riteh.autoshare.ui.user

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.braintreepayments.cardform.view.CardForm
import com.google.gson.Gson
import com.riteh.autoshare.R
import kotlinx.android.synthetic.main.activity_user_card.*


class UserCardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_card)

        val addCardBtn: Button = findViewById(R.id.addCardBtn)
        val cardForm = findViewById<CardForm>(R.id.card_form)

        cardForm.cardRequired(true)
            .expirationRequired(true)
            .cvvRequired(true)
            .setup(this)

        card_form_mobile.mobileNumberRequired(true).setup(this)

        addCardBtn.setOnClickListener {
            if (cardForm.isValid && validateInput()) {
                val cardNumber: String = cardForm.cardNumber
                val expirationMonth: String = cardForm.expirationMonth
                val expirationYear: String = cardForm.expirationYear
                val cvv: String = cardForm.cvv


                val cardholderName: String = user_name.text.toString()
                val cardholderSurname: String = user_surname.text.toString()
                val address: String = user_address.text.toString()
                val city: String = user_city.text.toString()
                val postalCode: String = user_area.text.toString()
                val countryCode: String = user_country.text.toString()
                val mobileNumber: String = card_form_mobile.mobileNumber

                val newCardUser = UserCard(
                    cardNumber, expirationMonth, expirationYear, cvv,
                    cardholderName, cardholderSurname, address, city, postalCode,
                    countryCode, mobileNumber
                )

                val prefs = getSharedPreferences("USER_CARD_PREFERENCES", Context.MODE_PRIVATE)
                val prefsEditor: SharedPreferences.Editor = prefs.edit()

                val gson = Gson()
                val json: String? = gson.toJson(newCardUser)
                prefsEditor.putString("UserCard", json)

                try {
                    prefsEditor.apply()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                Log.d("myTag", newCardUser.mobileNumber + newCardUser.cardholderName)
                Toast.makeText(this, "Your card has been successfully saved!", Toast.LENGTH_SHORT)
                    .show()
                finish()
            } else {
                displayErrors()
            }
        }
    }

    private fun validateInput(): Boolean {
        if (user_name.text.toString() == "" || user_surname.text.toString() == ""
            || user_address.text.toString() == "" || user_city.text.toString() == ""
            || user_area.text.toString() == "" || user_country.text.toString() == ""
        ) {
            return false
        }
        return true
    }

    private fun displayErrors() {
        if (user_name.text.toString() == "") {
            user_name.error = getString(R.string.required_field)
        }

        if (user_surname.text.toString() == "") {
            user_surname.error = getString(R.string.required_field)
        }

        if (user_address.text.toString() == "") {
            user_address.error = getString(R.string.required_field)
        }

        if (user_city.text.toString() == "") {
            user_city.error = getString(R.string.required_field)
        }

        if (user_area.text.toString() == "") {
            user_area.error = getString(R.string.required_field)
        }

        if (user_country.text.toString() == "") {
            user_country.error = getString(R.string.required_field)
        }
    }
}