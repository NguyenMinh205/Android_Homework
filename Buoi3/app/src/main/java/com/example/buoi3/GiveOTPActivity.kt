package com.example.buoi3

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.buoi3.databinding.ActivityGiveOtpBinding

class GiveOTPActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGiveOtpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityGiveOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        setupOtpInputs()

        binding.btnVerify.setOnClickListener {
            val otp = "${binding.etOtp1.text}${binding.etOtp2.text}${binding.etOtp3.text}${binding.etOtp4.text}"

            if (otp.length < 4) {
                Toast.makeText(this, "Please enter full 4-digit OTP", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(this, "OTP verified successfully!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, ResetPasswordActivity::class.java))
            finish()
        }
    }

    private fun setupOtpInputs() {
        val otpInputs = arrayOf(binding.etOtp1, binding.etOtp2, binding.etOtp3, binding.etOtp4)

        for (i in 0 until otpInputs.size) {
            otpInputs[i].addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s?.length == 1 && i < otpInputs.size - 1) {
                        otpInputs[i + 1].requestFocus()
                    }
                }
                override fun afterTextChanged(s: Editable?) {}
            })

            otpInputs[i].setOnKeyListener { v, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_DEL && event.action == KeyEvent.ACTION_DOWN) {
                    if (otpInputs[i].text.isEmpty() && i > 0) {
                        otpInputs[i - 1].requestFocus()
                        otpInputs[i - 1].text?.clear()
                        return@setOnKeyListener true
                    }
                }
                false
            }
        }
    }
}