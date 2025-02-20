package io.maa96.ubar.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import io.maa96.ubar.presentation.theme.UbarTheme

@Composable
fun UbarApp(){
    UbarTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Screen.List.route
        ) {
            ubarNavGraph(navController)
        }
    }
}