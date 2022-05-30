package com.riteh.autoshare.ui.home.user

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.riteh.autoshare.R
import kotlinx.android.synthetic.main.activity_user_card.*


class UserCardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_card)

        applyCardFormatting()

        val addCardBtn: Button = findViewById(R.id.addCardBtn)

        btn_clear.setOnClickListener {
            card_number.setText("")
            card_due.setText("")
            card_cvv.setText("")
            user_name.setText("")
            user_surname.setText("")
            user_address.setText("")
            user_city.setText("")
            user_area.setText("")
            user_country.setText("")
        }

        addCardBtn.setOnClickListener {
            if (validateInput()) {
                val cardNumber: String = card_number.text.toString().replace("-", "")
                val cardDue: String = card_due.text.toString()
                val cardCvv: String = card_cvv.text.toString()
                val cardholderName: String = user_name.text.toString()
                val cardholderSurname: String = user_surname.text.toString()
                val address: String = user_address.text.toString()
                val city: String = user_city.text.toString()
                val postalCode: String = user_area.text.toString()
                val country: String = user_country.text.toString()

                val newCardUser = UserCard(
                    cardNumber, cardDue, cardCvv, cardholderName, cardholderSurname, address,
                    city, postalCode, country
                )

                val prefs = getSharedPreferences("USER_CARD_PREFERENCES", Context.MODE_PRIVATE)
                val prefsEditor: SharedPreferences.Editor = prefs.edit()

                val gson = Gson()
                var json: String? = prefs.getString("userCard", "{}")
                val userCard: UserCard = gson.fromJson(json, UserCard::class.java)


                if (userCard.cardsList.isNullOrEmpty()) {
                    userCard.cardsList = mutableListOf(newCardUser)
                } else {
                    userCard.cardsList.add(newCardUser)
                }

                json = gson.toJson(userCard)
                prefsEditor.putString("userCard", json)

                try {
                    prefsEditor.apply()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

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
            || card_number.text.toString() == "" || card_due.text.toString() == ""
            || card_cvv.text.toString() == ""
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

        if (card_number.text.toString() == "") {
            card_number.error = getString(R.string.required_field)
        }

        if (card_due.text.toString() == "") {
            card_due.error = getString(R.string.required_field)
        }

        if (card_cvv.text.toString() == "") {
            card_cvv.error = getString(R.string.required_field)
        }
    }

    private fun applyCardFormatting() {
        card_number.addTextChangedListener(CardNumberFormatX())
        card_due.addTextChangedListener(DueDateFormat())
        card_cvv.addTextChangedListener(CvvFormat())
    }
}