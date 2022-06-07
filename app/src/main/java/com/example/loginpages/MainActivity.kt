package com.example.loginpages

import android.Manifest
import android.app.KeyguardManager
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.example.loginpages.login.BioMetricScreen
import com.example.loginpages.login.toast
import com.example.loginpages.ui.theme.LoginPagesTheme

class MainActivity : ComponentActivity() {
    private var cancellationSignal: CancellationSignal? = null

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginPagesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    BioMetricScreen {
                        launchBiometric()
                    }
                }
            }
        }
    }

    private val authenticationCallback: BiometricPrompt.AuthenticationCallback =
        @RequiresApi(Build.VERSION_CODES.P)
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                super.onAuthenticationSucceeded(result)
                toast("Authentication Succeeded")
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                super.onAuthenticationError(errorCode, errString)
                toast("Authentication Error $errorCode")
            }
        }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkBiometricSupport(): Boolean {
        val keyGuardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

        if (!keyGuardManager.isDeviceSecure) {
            return false
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_BIOMETRIC) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }
        return packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun launchBiometric() {
        if (checkBiometricSupport()) {
            val biometricPrompt = BiometricPrompt.Builder(this).apply {
                setTitle(getString(R.string.prompt_info_title))
                setSubtitle(getString(R.string.prompt_info_subtitle))
                setDescription(getString(R.string.prompt_info_description))
                setConfirmationRequired(false)
                setNegativeButton(
                    getString(R.string.prompt_info_use_app_password),
                    mainExecutor
                ) { _, _ ->
                    toast("Authentication Cancelled")
                }
            }.build()
            biometricPrompt.authenticate(
                getCancellationSignal(),
                mainExecutor,
                authenticationCallback
            )
        }
    }

    private fun getCancellationSignal(): CancellationSignal {
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            toast("Authentication cancelled signal")
        }
        return cancellationSignal as CancellationSignal
    }
}
