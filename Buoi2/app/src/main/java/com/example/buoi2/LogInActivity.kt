package com.example.buoi2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.buoi2.databinding.ActivityLogInBinding

class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLogInBinding.inflate(layoutInflater)

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.tvSignUp.setOnClickListener {
            var intent = Intent(this, SignUpActivity::class.java)

            startActivity(intent)
        }

        binding.logInBtn.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ Email và Mật khẩu!", Toast.LENGTH_SHORT).show()
            }
            else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Vui lòng nhập đúng định dạng Email!", Toast.LENGTH_SHORT).show()
            }
            else if (password.length < 8) {
                Toast.makeText(this, "Mật khẩu phải dài ít nhất 8 ký tự!", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}