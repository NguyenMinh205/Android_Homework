package com.example.self_study

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.self_study.databinding.FragmentGiveOtpBinding

class GiveOTPFragment : Fragment() {

    private var _binding: FragmentGiveOtpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGiveOtpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.btnVerify.setOnClickListener {
            validateAndVerify()
        }

        binding.tvResend.setOnClickListener {
            Toast.makeText(requireContext(), "OTP code has been resent", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateAndVerify() {
        val otp1 = binding.etOtp1.text.toString()
        val otp2 = binding.etOtp2.text.toString()
        val otp3 = binding.etOtp3.text.toString()
        val otp4 = binding.etOtp4.text.toString()

        if (otp1.isEmpty() || otp2.isEmpty() || otp3.isEmpty() || otp4.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter complete OTP", Toast.LENGTH_SHORT).show()
            return
        }

        Toast.makeText(requireContext(), "OTP verified successfully!", Toast.LENGTH_SHORT).show()
        
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, ResetPasswordFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}