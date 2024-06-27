package me.muhaimin.storyapp.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import me.muhaimin.storyapp.R
import me.muhaimin.storyapp.data.ResultState
import me.muhaimin.storyapp.data.dto.UserRegisterRequest
import me.muhaimin.storyapp.databinding.ActivityRegisterBinding
import me.muhaimin.storyapp.ui.ViewModelFactory
import me.muhaimin.storyapp.ui.login.LoginActivity
import me.muhaimin.storyapp.util.showLoading
import me.muhaimin.storyapp.util.showToast

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        setupView()
    }

    private fun setupView() {
        binding.btnRegister.setOnClickListener {
            val user = UserRegisterRequest(
                name = binding.nameEditText.editText?.text.toString(),
                email = binding.emailEditText.editText?.text.toString(),
                password = binding.passwordEditText.editText?.text.toString(),
            )
            viewModel.register(user).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is ResultState.Loading -> {
                            binding.progressIndicator.showLoading(true)
                        }

                        is ResultState.Success -> {
                            showToast(this, result.data.message)
                            binding.progressIndicator.showLoading(false)

                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        }

                        is ResultState.Error -> {
                            showToast(this, result.error)
                            binding.progressIndicator.showLoading(false)
                        }
                    }
                }
            }
        }

        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}