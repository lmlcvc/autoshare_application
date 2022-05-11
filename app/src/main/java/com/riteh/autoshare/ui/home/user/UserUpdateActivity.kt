package com.riteh.autoshare.ui.home.user

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.riteh.autoshare.R
import kotlinx.android.synthetic.main.activity_user_information.*

class UserUpdateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_update)

        arrow_back.setOnClickListener {
            this.finish()
        }
    }
}