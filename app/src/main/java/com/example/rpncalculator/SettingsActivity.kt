package com.example.rpncalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import com.google.android.material.switchmaterial.SwitchMaterial
import java.lang.StrictMath.max
import kotlin.math.min

class SettingsActivity : AppCompatActivity() {
    private var darkMode: Boolean = Preferences.darkMode
    private var precision = Preferences.precision

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

        findViewById<Switch>(R.id.switchDarkMode).setOnCheckedChangeListener { _, isChecked ->
            updateDarkMode(
                isChecked
            )
        }

        findViewById<Button>(R.id.buttonSave).setOnClickListener {
            Preferences.precision = this.precision
            Preferences.darkMode = this.darkMode
            this.finish()
        }
    }

    private fun updatePrecision(change: Int? = null) {
        if (change != null)
            this.precision += change
        this.precision = min(max(this.precision, 0), 10)
        val textViewPrecision = findViewById<TextView>(R.id.textViewPrecision)
        textViewPrecision.text = this.precision.toString()
    }

    private fun updateDarkMode(change: Boolean? = null) {
        if (change != null)
            this.darkMode = change
        val switchDarkMode = findViewById<SwitchMaterial>(R.id.switchDarkMode)
        switchDarkMode.isChecked = this.darkMode
    }
}