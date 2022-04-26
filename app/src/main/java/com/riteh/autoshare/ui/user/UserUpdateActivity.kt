package com.riteh.autoshare.ui.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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