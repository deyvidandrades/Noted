package com.deyvidandrades.noted.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.deyvidandrades.noted.R
import com.deyvidandrades.noted.assistentes.Persistencia
import java.util.concurrent.Executor

class AuthActivity : AppCompatActivity() {
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_auth)

        Persistencia.getInstance(this)

        AppCompatDelegate.setDefaultNightMode(
            if (Persistencia.isDarkTheme) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )

        if (Persistencia.isPrivado) autenticar() else startActivity(Intent(this, MainActivity::class.java))

        val btnAuth: Button = findViewById(R.id.btn_autenticar)

        btnAuth.setOnClickListener {
            autenticar()
        }
    }

    private fun autenticar() {
        executor = ContextCompat.getMainExecutor(this)

        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(this@AuthActivity, getString(R.string.falha_ao_autenticar), Toast.LENGTH_SHORT).show()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Persistencia.auth = true
                    startActivity(Intent(this@AuthActivity, MainActivity::class.java))
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(this@AuthActivity, getString(R.string.falha_ao_autenticar), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        )

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.autenticacao))
            .setSubtitle(getString(R.string.para_acessar_as_anotacoes))
            .setNegativeButtonText(getString(R.string.cancelar))
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}