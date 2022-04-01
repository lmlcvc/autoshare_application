package com.riteh.autoshare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

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