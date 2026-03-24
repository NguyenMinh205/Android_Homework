package com.example.buoi2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.buoi2.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySignUpBinding.inflate(layoutInflater)

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.tvLogIn.setOnClickListener {
            finish()
        }

        binding.ivBack.setOnClickListener {
             finish()
        }

        binding.registerBtn.setOnClickListener {
            val fullName = binding.etFullName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.etEmail.error = "Email sai định dạng (ví dụ: abc@gmail.com)"
                binding.etEmail.requestFocus()
                return@setOnClickListener
            }

            if (password.length < 8) {
                binding.etPassword.error = "Mật khẩu phải dài ít nhất 8 ký tự!"
                binding.etPassword.requestFocus()
                return@setOnClickListener
            }

            Toast.makeText(this, "Dữ liệu hợp lệ! Đang xử lý đăng ký...", Toast.LENGTH_SHORT).show()

            finish()
        }
    }
}