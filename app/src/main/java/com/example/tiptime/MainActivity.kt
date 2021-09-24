package com.example.tiptime

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.tiptime.databinding.ActivityMainBinding
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fun calculateTip(){
            val stringInTextField = binding.costOfService.text.toString()
            val cost = stringInTextField.toDoubleOrNull()

            if(cost == null){
                binding.tipResult.text = ""
                val emptyCostSnackbar = Snackbar
                    .make(binding.root,getString(R.string.snackbar), LENGTH_INDEFINITE)
                    .setAction("Retry") {}
                emptyCostSnackbar.show()
                return
            }

            val selectedId = binding.tipOptions.checkedRadioButtonId
            val tipPercentage = when (selectedId){
                R.id.option_twenty_percent -> 0.20
                R.id.option_eighteen_percent -> 0.18
                R.id.option_fifteen_percent -> 0.15
                else -> 100000000.0
            }

            var tip = tipPercentage * cost

            val roundUp = binding.roundUpSwitch.isChecked

            if (roundUp){
                tip = ceil(tip)
            }

            val formattedTip = NumberFormat.getCurrencyInstance().format(tip)

            binding.tipResult.text=getString(R.string.tip_amount, formattedTip)
        }
        binding.calculateButton.setOnClickListener { calculateTip() }
    }
}