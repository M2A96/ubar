package io.maa96.ubar.presentation.navigation

import android.content.ContentValues.TAG
import android.util.Log
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
import io.maa96.ubar.presentation.ui.register.LocationPickerWithPermissions
import io.maa96.ubar.presentation.ui.register.RegistrationScreen

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
                onAddAddressClick = { navController.navigate(RegistrationNavigation.Registration.route) },
            )
        }

        composable(RegistrationNavigation.Registration.route) {
            RegistrationScreen(
                onBackClick = onBackClick,
                onNextClick = {
                    navController.navigate(RegistrationNavigation.LocationPicker.route)
                },
                modifier = Modifier.fillMaxSize()
            )
        }
        
        composable(RegistrationNavigation.LocationPicker.route) {
            LocationPickerWithPermissions(
                onLocationSelected = { location ->
                    // Handle the selected location
                    // You might want to pass this back to the registration screen
                    // or handle it in a ViewModel
                    Log.i(TAG, "RegistrationNavigationGraph: Location selected: $location")
                },
                modifier = Modifier.fillMaxSize()
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