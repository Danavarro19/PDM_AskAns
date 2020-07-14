package com.dnavarro.askanswerviews

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val newIntent = Intent(this, MainActivity::class.java)
        startActivity(newIntent)
        finish()
    }
}