package io.maa96.ubar.presentation.navigation

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.maa96.ubar.presentation.ui.login.LoginScreen
import io.maa96.ubar.presentation.ui.login.LoginScreenViewModel

sealed class Screen(val route: String) {
    data object Login: Screen("login")
    data object List: Screen("list")
}

fun NavGraphBuilder.ubarNavGraph(naveController: NavController) {
    composable(route = Screen.Login.route) {
        val viewModel: LoginScreenViewModel = hiltViewModel()
        val state = viewModel.state.collectAsState()

        LoginScreen(
            state = state.value,
            onEvent = viewModel::onEvent,
            onNavigateBack = { naveController.popBackStack() }
        )

    }
}