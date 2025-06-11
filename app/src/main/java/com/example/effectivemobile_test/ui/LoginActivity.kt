package com.example.effectivemobile_test.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.example.effectivemobile_test.databinding.ActivityLoginBinding
import androidx.core.net.toUri

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        setupListeners()
    }

    private fun initViews() {
        binding.button.isEnabled = false
        binding.editTextEmail.filters = arrayOf(cyrillicFilter)
        binding.editTextPassword.filters = arrayOf(noSpaceFilter)
    }

    private fun setupListeners() {
        binding.editTextEmail.addTextChangedListener(loginTextWatcher)
        binding.editTextPassword.addTextChangedListener(loginTextWatcher)

        binding.button.setOnClickListener {
            if (isFormValid()) {
                navigateToMainScreen()
            }
        }

        binding.vkBtn.setOnClickListener {
            openUrl("https://vk.com/")
        }

        binding.matesBtn.setOnClickListener {
            openUrl("https://ok.ru/")
        }
    }

    private val loginTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(editable: Editable?) {
            validateForm()
        }
    }

    private fun validateForm() {
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()

        val isEmailValid = email.isNotEmpty() && isValidEmail(email)
        val isPasswordValid = password.isNotEmpty()

        binding.button.isEnabled = isEmailValid && isPasswordValid

        if (email.isNotEmpty() && !isValidEmail(email) && !binding.editTextEmail.hasFocus()) {
            binding.editTextEmail.error = "Некорректный email (пример: example@gmail.com)"
        } else {
            binding.editTextEmail.error = null
        }
    }

    private fun isFormValid(): Boolean {
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()
        return isValidEmail(email) && password.isNotEmpty()
    }

    private fun navigateToMainScreen() {
        val intent = Intent(this, RootActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        startActivity(intent)
    }

    private val cyrillicFilter = InputFilter { source, _, _, _, _, _ ->
        if (source.toString().matches(Regex(".*[а-яА-Я].*"))) {
            ""
        } else {
            null
        }
    }

    private val noSpaceFilter = InputFilter { source, _, _, _, _, _ ->
        if (source.toString().contains(" ")) {
            ""
        } else {
            null
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z]+\\.[A-Za-z]{2,}\$")
        return emailPattern.matches(email)
    }
}