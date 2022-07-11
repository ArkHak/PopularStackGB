package test

import com.example.popularstackgb.data.loginapi.MockLoginApiImpl
import com.example.popularstackgb.data.loginusecase.LoginUsecaseImpl
import com.example.popularstackgb.ui.login.LoginContract
import com.example.popularstackgb.ui.login.LoginPresenter
import com.example.popularstackgb.utils.ErrorCodes
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class LoginPresenterTest {

    private lateinit var presenter: LoginPresenter

    @Mock
    private lateinit var loginUsecase: LoginUsecaseImpl

    @Mock
    private lateinit var view: LoginContract.View

    @Mock
    private lateinit var loginApi: MockLoginApiImpl


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        presenter = LoginPresenter(loginUsecase)
    }

    @Test
    fun onLogin_checkConnect_true_Test() {
        val login = "admin"
        val password = "admin"
        Mockito.`when`(loginUsecase.checkConnect()).thenReturn(true)

        presenter.onLogin(login, password)
        verify(loginUsecase).checkConnect()
    }

    @Test
    fun onLogin_checkConnect_false_Test() {
        val login = "admin"
        val password = "admin"
        Mockito.`when`(loginUsecase.checkConnect()).thenReturn(false)
//        Mockito.`when`(loginApi.login(login, password)).thenReturn(false)
//        Mockito.`when`(loginUsecase.login(login, password) { any() })
//        doNothing().`when`(loginUsecase.login(login, password) {})
//        Mockito.`when`(mockLoginApi.login(login, password)).thenReturn(true)

        presenter.onLogin(login, password)
        verify(view, Mockito.times(1)).setError(ErrorCodes.SERVER_IS_NOT_AVAILABLE.textError)
    }


    @Test
    fun onForgotPassword_checkConnect_true_Test() {
        val login = "admin"
        Mockito.`when`(loginUsecase.checkConnect()).thenReturn(true)

        presenter.onForgotPassword(login)
        verify(loginUsecase).checkConnect()
    }


    @Test
    fun onSingUp_checkConnect_true_Test() {
        val login = "admin"
        val password = "admin"
        Mockito.`when`(loginUsecase.checkConnect()).thenReturn(true)

        presenter.onSignUp(login, password)
        verify(loginUsecase).checkConnect()
    }
}