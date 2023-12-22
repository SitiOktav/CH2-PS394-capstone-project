package com.capstone.setravelapp.view.activity.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.capstone.setravelapp.data.remote.response.RegisterResponse
import com.capstone.setravelapp.databinding.ActivityRegisterBinding
import com.capstone.setravelapp.data.Result
import com.capstone.setravelapp.di.Injection
import com.capstone.setravelapp.view.activity.login.LogInActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels {
        RegisterViewModelFactory(Injection.provideRepository(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnSignup.setOnClickListener {
                val username = addUsername.text.toString()
                val email = addEmail.text.toString()
                val password = addPassword.text.toString()
                val confirmPassword = addConfirmPassword.text.toString()

                println("Username: $username")
                println("Email: $email")
                println("Password: $password")
                println("Confirm Password: $confirmPassword")

                viewModel.register(username, email, password, confirmPassword)
                    .observe(this@RegisterActivity, Observer { result ->
                        when (result) {
                            is Result.Loading -> {
                                progressBar.visibility = View.VISIBLE
                            }
                            is Result.Success -> {
                                progressBar.visibility = View.GONE
                                handleSuccess(result.data)
                            }
                            is Result.Error -> {
                                progressBar.visibility = View.GONE
                                handleError(result.message)
                            }
                        }
                    })
            }

            tvToLogin.setOnClickListener {
                startActivity(Intent(this@RegisterActivity, LogInActivity::class.java))
                finish()
            }
        }
    }

    private fun handleSuccess(response: RegisterResponse) {
        // Handle the success response, e.g., show a success message
        Toast.makeText(this, response.msg, Toast.LENGTH_SHORT).show()
    }

    private fun handleError(message: String) {
        // Handle the error, e.g., show an error message
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
