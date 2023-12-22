package com.capstone.setravelapp.view.activity.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.setravelapp.R
import com.capstone.setravelapp.databinding.ActivityLogInBinding
import com.capstone.setravelapp.data.remote.response.LoginResponse
import com.capstone.setravelapp.data.Result
import com.capstone.setravelapp.data.local.session.StorySession
import com.capstone.setravelapp.view.activity.customview.EmailEditText
import com.capstone.setravelapp.view.activity.customview.PasswordEditText
import com.capstone.setravelapp.view.activity.customview.VibeButton
import com.capstone.setravelapp.view.activity.signup.RegisterActivity
import com.capstone.setravelapp.view.activity.main.MainActivity

class LogInActivity : AppCompatActivity() {
    private var logBinding: ActivityLogInBinding? = null
    private val binding get() = logBinding!!
    private lateinit var logButton: VibeButton
    private lateinit var emailEdit: EmailEditText
    private lateinit var passwordEdit: PasswordEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logBinding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val isLoggedIn = StorySession(this).isLogin()
        if (isLoggedIn) {
            val intent = Intent(this@LogInActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        logButton = findViewById(R.id.btn_login)
        emailEdit = findViewById(R.id.addEmail)
        passwordEdit = findViewById(R.id.addPassword)

        emailEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                buttonEnable()
            }

            override fun afterTextChanged(s: Editable) {}
        })

        passwordEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                buttonEnable()
            }

            override fun afterTextChanged(s: Editable) {}
        })

        val factory: LoginViewModelFactory = LoginViewModelFactory.getInstance(this)
        val viewModel: LoginViewModel by viewModels { factory }

        binding.progressBar.visibility = View.GONE

        val pref = StorySession(this)

        binding.apply {
            btnLogin.setOnClickListener {
                viewModel.login(
                    addEmail.text.toString(),
                    addPassword.text.toString()
                ).observe(this@LogInActivity) {
                    when (it) {
                        is Result.Loading -> {
                            progressBar.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            progressBar.visibility = View.GONE
                            pref.setUser(LoginResponse(accessToken = it.data.accessToken))
                            val intent = Intent(this@LogInActivity, MainActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            finish()
                            Toast.makeText(
                                this@LogInActivity,
                                resources.getString(R.string.loginSuccess),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is Result.Error -> {
                            progressBar.visibility = View.GONE
                            Toast.makeText(
                                this@LogInActivity,
                                resources.getString(R.string.loginError),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }

            tvToSignUp.setOnClickListener {
                val intent = Intent(this@LogInActivity, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun buttonEnable() {
        val emailIn = emailEdit.text
        val passwordIn = passwordEdit.text
        logButton.isEnabled =
            emailIn != null && emailIn.toString().isNotEmpty() && passwordIn != null && passwordIn.toString().isNotEmpty()
    }
}
