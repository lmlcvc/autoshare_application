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


class UserCardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_card)


        val addCardBtn: Button = findViewById(R.id.addCardBtn)
        val cardForm = findViewById<CardForm>(R.id.card_form)

        cardForm.cardRequired(true)
            .expirationRequired(true)
            .cvvRequired(true)
            .cardholderName(CardForm.FIELD_REQUIRED)
            .postalCodeRequired(true)
            .mobileNumberRequired(true)
            .setup(this)

        addCardBtn.setOnClickListener{
            if(cardForm.isValid()){
                val cardNumber: String = cardForm.getCardNumber()
                val expirationMonth: String = cardForm.getExpirationMonth()
                val expirationYear: String = cardForm.getExpirationYear()
                val cvv: String = cardForm.getCvv()
                val cardholderName: String = cardForm.getCardholderName();
                val postalCode: String = cardForm.getPostalCode();
                val countryCode: String = cardForm.getCountryCode();
                val mobileNumber: String = cardForm.getMobileNumber();

                val newCardUser = UserCard(cardNumber, expirationMonth, expirationYear, cvv, cardholderName,
                    postalCode, countryCode, mobileNumber)

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
                Log.d("myTag", newCardUser.mobileNumber + newCardUser.cardholderName);
                Toast.makeText(this, "Your card has been successfully saved!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}