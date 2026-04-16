package com.example.self_study

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.self_study.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvLogIn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.btnSignUp.setOnClickListener {
            validateAndSignUp()
        }
    }

    private fun validateAndSignUp() {
        val username = binding.etUsername.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val isChecked = binding.cbTerms.isChecked

        if (username.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter username", Toast.LENGTH_SHORT).show()
            return
        }

        if (email.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter email", Toast.LENGTH_SHORT).show()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(requireContext(), "Invalid email format", Toast.LENGTH_SHORT).show()
            return
        }

        if (password.length < 8) {
            Toast.makeText(requireContext(), "Password must be at least 8 characters", Toast.LENGTH_SHORT).show()
            return
        }

        if (!isChecked) {
            Toast.makeText(requireContext(), "Please accept terms and privacy policy", Toast.LENGTH_SHORT).show()
            return
        }

        Toast.makeText(requireContext(), "Sign up successful!", Toast.LENGTH_SHORT).show()
        
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, LogInFragment())
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}