package io.maa96.ubar.presentation.navigation

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.maa96.ubar.presentation.ui.list.ListScreen
import io.maa96.ubar.presentation.ui.list.ListScreenViewModel
import io.maa96.ubar.presentation.ui.register.map.LocationPickerWithPermissions
import io.maa96.ubar.presentation.ui.register.register.RegistrationScreen
import io.maa96.ubar.presentation.ui.register.register.RegistrationViewModel

sealed class RegistrationNavigation(val route: String) {
    object Registration : RegistrationNavigation("registration")
    object LocationPicker : RegistrationNavigation("location_picker")

    object RegisteredList : RegistrationNavigation("list")
}

@Composable
fun RegistrationNavigationGraph(
    navController: NavHostController,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val registrationViewModel: RegistrationViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = RegistrationNavigation.RegisteredList.route,
        modifier = modifier
    ) {
        composable(route = RegistrationNavigation.RegisteredList.route) {
            val viewModel: ListScreenViewModel = hiltViewModel()
            val state = viewModel.state.collectAsState()

            ListScreen(
                state = state.value,
                onNavigateBack = { navController.popBackStack() },
                onAddAddressClick = { navController.navigate(RegistrationNavigation.Registration.route) }
            )
        }

        composable(RegistrationNavigation.Registration.route) {
            RegistrationScreen(
                viewModel = registrationViewModel,
                onBackClick = onBackClick,
                onNextClick = {
                    navController.navigate(RegistrationNavigation.LocationPicker.route)
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        composable(RegistrationNavigation.LocationPicker.route) {
            LocationPickerWithPermissions(
                viewModel = registrationViewModel,
                onInformationSent = {
                    Toast.makeText(navController.context, "Information sent", Toast.LENGTH_SHORT).show()
                    navController.navigate(RegistrationNavigation.RegisteredList.route)
                },
            )
        }
    }
}

@Composable
fun RegistrationContainer(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    RegistrationNavigationGraph(
        navController = navController,
        onBackClick = onBackClick,
        modifier = modifier
    )
}