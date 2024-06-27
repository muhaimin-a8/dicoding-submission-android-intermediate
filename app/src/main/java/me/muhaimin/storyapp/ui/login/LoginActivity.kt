package me.muhaimin.storyapp.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import me.muhaimin.storyapp.data.ResultState
import me.muhaimin.storyapp.data.dto.UserLoginRequest
import me.muhaimin.storyapp.databinding.ActivityLoginBinding
import me.muhaimin.storyapp.ui.ViewModelFactory
import me.muhaimin.storyapp.ui.home.HomeActivity
import me.muhaimin.storyapp.ui.register.RegisterActivity
import me.muhaimin.storyapp.util.showLoading
import me.muhaimin.storyapp.util.showToast

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        binding.btnLogin.setOnClickListener {
            val user = UserLoginRequest(
                email = binding.emailEditText.editText?.text.toString(),
                password = binding.passwordEditText.editText?.text.toString(),
            )
            viewModel.login(user).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is ResultState.Loading -> {
                            binding.progressIndicator.showLoading(true)
                        }

                        is ResultState.Success -> {

                            showToast(this, result.data.message)
                            binding.progressIndicator.showLoading(false)

                            startActivity(Intent(this, HomeActivity::class.java))
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
    }
}