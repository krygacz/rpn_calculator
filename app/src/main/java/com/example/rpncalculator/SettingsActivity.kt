package com.example.rpncalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.switchmaterial.SwitchMaterial
import java.lang.StrictMath.max
import kotlin.math.min

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        this.updateDarkMode()
        this.updatePrecision()

        findViewById<Button>(R.id.buttonDecrease).setOnClickListener {
            this.updatePrecision(-1)
        }

        findViewById<Button>(R.id.buttonIncrease).setOnClickListener {
            this.updatePrecision(+1)
        }

        findViewById<SwitchMaterial>(R.id.switchDarkMode).setOnCheckedChangeListener { _, isChecked ->
            updateDarkMode(
                isChecked
            )
        }

        findViewById<Button>(R.id.buttonSave).setOnClickListener {
            this.finish()
        }
    }

    private fun updatePrecision(change: Int? = null) {
        if (change != null)
            Preferences.precision += change
        Preferences.precision = min(max(Preferences.precision, 0), 10)
        val textViewPrecision = findViewById<TextView>(R.id.textViewPrecision)
        textViewPrecision.text = Preferences.precision.toString()
    }

    private fun updateDarkMode(change: Boolean? = null) {
        if (change != null)
            if (change) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        val switchDarkMode = findViewById<SwitchMaterial>(R.id.switchDarkMode)
        switchDarkMode.isChecked = (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
    }
}