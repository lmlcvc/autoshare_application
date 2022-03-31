package com.riteh.autoshare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login);

        btnLogin.setOnClickListener {
            performSignUp()
        }
    }

    private fun performSignUp() {
        if (validateInput()) {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateInput(): Boolean {
        if (inputEmail.text.toString() == "" || inputPassword.text.toString() == "") {
            return false
        }
        return true
    }
}