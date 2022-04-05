package com.example.popularstackgb

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.MainThread
import androidx.core.view.isVisible
import com.example.popularstackgb.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(), LoginContract.View {
    private lateinit var binding: ActivityLoginBinding
    private var presenter: LoginContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = initPresenter()
        presenter?.onAttach(this)

        binding.actionLoginButton.setOnClickListener {
            presenter?.onLogin(
                binding.usernameEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }

        binding.actionForgotPasswordTextView.setOnClickListener {
            presenter?.onForgotPassword(binding.usernameEditText.text.toString())
        }

        binding.actionSingUpButton.setOnClickListener {
            presenter?.onSignUp(
                binding.usernameEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }
    }

    @MainThread
    override fun setSuccess() {
        binding.titleLoginTextView.text = getString(R.string.result_login_success)
        binding.loginLoadingProgressIndicator.isVisible = false
        hideInputFields()
        hideButtons()
    }

    @MainThread
    private fun hideButtons() {
        binding.actionForgotPasswordTextView.isVisible = false
        binding.actionLoginButton.isVisible = false
        binding.actionSingUpButton.isVisible = false
    }

    @MainThread
    private fun hideInputFields() {
        binding.usernameEditText.isVisible = false
        binding.passwordEditText.isVisible = false
    }

    @MainThread
    override fun setError(error: String) {
        Toast.makeText(this, "Error: $error", Toast.LENGTH_SHORT).show()
    }

    @MainThread
    override fun showLoading() {
        binding.actionLoginButton.isEnabled = false
        binding.actionSingUpButton.isEnabled = false
        binding.actionForgotPasswordTextView.isEnabled = false
        binding.loginLoadingProgressIndicator.isVisible = true
        hideKeyboard(this)
    }

    @MainThread
    override fun hideLoading() {
        binding.actionLoginButton.isEnabled = true
        binding.actionSingUpButton.isEnabled = true
        binding.actionForgotPasswordTextView.isEnabled = true
        binding.loginLoadingProgressIndicator.isVisible = false
    }

    override fun getHandler(): Handler {
        return Handler(Looper.getMainLooper())
    }

    override fun passwordReminderSuccess(password: String) {
        Toast.makeText(this, "Password: $password", Toast.LENGTH_SHORT).show()
    }

    override fun addAccountSuccess(login: String) {
        Toast.makeText(this, "$login was created", Toast.LENGTH_SHORT).show()
        cleaning()
    }

    private fun initPresenter(): LoginPresenter {
        val presenter = lastCustomNonConfigurationInstance as? LoginPresenter
        return presenter ?: LoginPresenter()
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return presenter
    }

    private fun cleaning() {
        binding.usernameEditText.text.clear()
        binding.passwordEditText.text.clear()
    }

    private fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        var view: View? = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}