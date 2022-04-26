package com.riteh.autoshare.ui.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.riteh.autoshare.R


class UserInformationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_information)

        val btn = findViewById(R.id.btn_edit) as Button
        btn.setOnClickListener {
            val intent = Intent(it.context, UserUpdateActivity::class.java)
            startActivity(intent)
        }

    }



}