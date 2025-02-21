package io.maa96.ubar.presentation.ui.register.map

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun LocationPickerScreen(
    onLocationSelected: (LatLng) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var mapProperties by remember {
        mutableStateOf(
            MapProperties(
                mapType = MapType.NORMAL,
                isMyLocationEnabled = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            )
        )
    }

    var cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(40.7128, -74.0060), // Default to New York City
            12f
        )
    }

    Box(modifier = modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            properties = mapProperties,
            cameraPositionState = cameraPositionState,
            onMapClick = { /* We'll use center position instead */ }
        )

        // Center marker
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // You can replace this with a custom marker image
            Text(
                text = "📍",
                fontSize = 36.sp
            )
        }

        // Confirm button at the bottom
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = {
                    // Get the center position of the map
                    val centerPosition = cameraPositionState.position.target
                    onLocationSelected(centerPosition)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("تایید موقعیت")
            }
        }
    }
}

// Usage example:
@Composable
fun LocationPickerDemo() {
    LocationPickerScreen(
        onLocationSelected = { location ->
            // Handle the selected location
            println("Selected location: ${location.latitude}, ${location.longitude}")
        }
    )
}

// Don't forget to add these dependencies in your build.gradle:
/*
dependencies {
    implementation "com.google.maps.android:maps-compose:2.11.4"
    implementation "com.google.android.gms:play-services-maps:18.1.0"
}
*/

// And add your Google Maps API key in AndroidManifest.xml:
/*
<meta-data
    android:name="com.google.android.geo.API_KEY"
    android:value="YOUR_API_KEY" />
*/