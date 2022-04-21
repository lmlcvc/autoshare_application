package com.riteh.autoshare

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowInsets
import androidx.appcompat.app.AppCompatActivity
import com.riteh.autoshare.ui.auth.AuthActivity
import com.riteh.autoshare.ui.home.MainActivity


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        removeBorder()
        newContent()
    }

    fun removeBorder(){
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }

    fun newContent(){
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            val homeIntent = Intent(this, AuthActivity::class.java)
            startActivity(homeIntent)
            finish()
        }, 3000)
    }
}