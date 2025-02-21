package io.maa96.ubar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import io.maa96.ubar.presentation.theme.UbarTheme
import io.maa96.ubar.presentation.navigation.RegistrationContainer

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UbarTheme {
                UbarApp()
            }
        }
    }
}

@Composable
fun UbarApp() {
    RegistrationContainer(
        onBackClick = { /* Handle back click */ },
        modifier = Modifier.fillMaxSize()
    )
}