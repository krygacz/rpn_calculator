package com.example.rpncalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        findViewById<Button>(R.id.buttonSave).setOnClickListener {
            Preferences.precision = findViewById<TextView>(R.id.textViewPrecision).text.toString().toInt()
            Preferences.darkMode = findViewById<Switch>(R.id.switchDarkMode).isChecked
            this.finish()
        }
    }
}