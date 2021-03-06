package com.example.popularstackgb.ui.login

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
import com.example.popularstackgb.R
import com.example.popularstackgb.app
import com.example.popularstackgb.databinding.ActivityLoginBinding
import com.example.popularstackgb.res

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var viewModel: LoginContract.ViewModel? = null
    private val handler: Handler by lazy { Handler(Looper.getMainLooper()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = initViewModel()

        binding.actionLoginButton.setOnClickListener {
            viewModel?.onLogin(
                binding.usernameEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }

        binding.actionForgotPasswordTextView.setOnClickListener {
            viewModel?.onForgotPassword(binding.usernameEditText.text.toString())
        }

        binding.actionSingUpButton.setOnClickListener {
            viewModel?.onSignUp(
                binding.usernameEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }

        viewModel?.shouldShowLoading?.subscribe(handler) { shouldShow ->
            if (shouldShow == true) {
                showLoading()
            } else {
                hideLoading()
            }
        }

        viewModel?.isLoginSuccess?.subscribe(handler) { isLogin ->
            if (isLogin == true) {
                setSuccess()
            }
        }

        viewModel?.errorCode?.subscribe(handler) { code ->
            code?.let { setError(code) }
        }

        viewModel?.isAddAccountSuccess?.subscribe(handler) { username ->
            username?.let { addAccountSuccess(it) }
        }

        viewModel?.isPasswordReminderSuccess?.subscribe(handler) { password ->
            password?.let { passwordReminderSuccess(it) }
        }

    }

    private fun setSuccess() {
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

    private fun setError(errorCode: Int) {
        val textError = res.getString(errorCode)
        Toast.makeText(this, "Error: $textError", Toast.LENGTH_SHORT).show()
    }

    private fun showLoading() {
        binding.actionLoginButton.isEnabled = false
        binding.actionSingUpButton.isEnabled = false
        binding.actionForgotPasswordTextView.isEnabled = false
        binding.loginLoadingProgressIndicator.isVisible = true
        hideKeyboard(this)
    }

    private fun hideLoading() {
        binding.actionLoginButton.isEnabled = true
        binding.actionSingUpButton.isEnabled = true
        binding.actionForgotPasswordTextView.isEnabled = true
        binding.loginLoadingProgressIndicator.isVisible = false
    }

    private fun passwordReminderSuccess(password: String) {
        Toast.makeText(this, "Password: $password", Toast.LENGTH_SHORT).show()
    }

    private fun addAccountSuccess(login: String) {
        Toast.makeText(this, "$login was created", Toast.LENGTH_SHORT).show()
        cleaning()
    }

    private fun initViewModel(): LoginViewModel {
        val viewModel = lastCustomNonConfigurationInstance as? LoginViewModel
        return viewModel ?: LoginViewModel(app.loginUsecase)
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return viewModel
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

    override fun onDestroy() {
        super.onDestroy()
        viewModel?.isLoginSuccess?.unsubscribeAll()
        viewModel?.isPasswordReminderSuccess?.unsubscribeAll()
        viewModel?.isAddAccountSuccess?.unsubscribeAll()
        viewModel?.errorCode?.unsubscribeAll()
        viewModel?.shouldShowLoading?.unsubscribeAll()
    }
}