package com.riteh.autoshare.ui.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.riteh.autoshare.R
import kotlinx.android.synthetic.main.activity_user_information.*



class UserInformationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_information)

        arrow_back.setOnClickListener {
            this.finish()
        }

        btn_edit.setOnClickListener {
            val intent = Intent(it.context, UserUpdateActivity::class.java)
            startActivity(intent)
        }

    }

}