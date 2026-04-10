package com.example.buoi5

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.buoi5.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var contacts: MutableList<Contact>
    private lateinit var adapter: ContactAdapter
    private val PHONE_REGEX = """^(0|\+84)(\s|\.)?((3[2-9])|(5[5689])|(7[06-9])|(8[1-9])|(9[0-46-9]))(\d)(\s|\.)?(\d{3})(\s|\.)?(\d{3})$""".toRegex()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        contacts = mutableListOf()
        adapter = ContactAdapter(
            contacts = contacts,
            onItemClick = { contact ->
                Toast.makeText(this, "Bạn đang gọi ${contact.name} - ${contact.phone}", Toast.LENGTH_SHORT).show()
            },
            onDeleteClick = { position ->
                val name = contacts[position].name
                contacts.removeAt(position)
                adapter.notifyItemRemoved(position)
                adapter.notifyItemRangeChanged(position, contacts.size)
                Toast.makeText(this, "Đã xóa $name", Toast.LENGTH_SHORT).show()
            }
        )

        binding.rvContacts.layoutManager = LinearLayoutManager(this)
        binding.rvContacts.adapter = adapter

        binding.btnAdd.setOnClickListener {
            validateAndAddContact()
        }
    }

    private fun validateAndAddContact() {
        val name = binding.edtName.text.toString().trim()
        val phone = binding.edtPhone.text.toString().trim()

        binding.tilName.error = null
        binding.tilPhone.error = null

        if (name.isEmpty()) {
            binding.tilName.error = "Vui lòng nhập tên"
            binding.edtName.requestFocus()
            return
        }

        if (contacts.any { it.name.equals(name, ignoreCase = true) }) {
            binding.tilName.error = "Tên liên lạc này đã tồn tại"
            binding.edtName.requestFocus()
            return
        }

        if (phone.isEmpty()) {
            binding.tilPhone.error = "Vui lòng nhập số điện thoại"
            binding.edtPhone.requestFocus()
            return
        }

        if (!isValidPhone(phone)) {
            binding.tilPhone.error = "Số điện thoại phải có từ 10-11 chữ số"
            binding.edtPhone.requestFocus()
            return
        }

        val newContact = Contact(name, phone)
        contacts.add(newContact)
        adapter.notifyItemInserted(contacts.size - 1)
        binding.rvContacts.scrollToPosition(contacts.size - 1)

        clearInputs()
        Toast.makeText(this, "Thêm liên lạc thành công!", Toast.LENGTH_SHORT).show()
    }

    private fun isValidPhone(phone: String): Boolean {
        return phone.matches(PHONE_REGEX)
    }

    private fun clearInputs() {
        binding.edtName.text?.clear()
        binding.edtPhone.text?.clear()
        binding.edtName.clearFocus()
        binding.edtPhone.clearFocus()
    }
}