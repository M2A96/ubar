package io.maa96.ubar.presentation.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.maa96.ubar.R
import io.maa96.ubar.presentation.theme.UbarTheme

@Composable
fun LoginScreen(
    state: LoginScreenState,
    onEvent: (LoginScreenEvent) -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        LoginHeader(
            onBackClick = onNavigateBack
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "لطفا اطلاعات ورود خود را وارد نمایید.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextInputLayout(
            value = state.location.firstName,
            onValueChange = { onEvent(LoginScreenEvent.OnNameChange(it)) },
            label = "نام",
            isValid = state.location.firstName.isNotBlank()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextInputLayout(
            value = state.location.lastName,
            onValueChange = { onEvent(LoginScreenEvent.OnFamilyNameChange(it)) },
            label = "نام خانوادگی"
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextInputLayout(
            value = state.location.coordinateMobile,
            onValueChange = { onEvent(LoginScreenEvent.OnMobilePhoneChange(it)) },
            label = "تلفن همراه",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            isValid = state.location.coordinateMobile.isNotBlank()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextInputLayout(
            value = state.location.coordinatePhoneNumber,
            onValueChange = { onEvent(LoginScreenEvent.OnLandlinePhoneChange(it)) },
            label = "تلفن ثابت",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.exact_address),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End
        )

        TextInputLayout(
            value = state.location.address,
            onValueChange = { onEvent(LoginScreenEvent.OnAddressChange(it)) },
            label = "",
            singleLine = false,
            minLines = 3
        )

        Spacer(modifier = Modifier.height(16.dp))

        GenderSelector(
            selectedGender = state.location.gender,
            onGenderSelected = { onEvent(LoginScreenEvent.OnGenderChange(it)) }
        )

        Spacer(modifier = Modifier.weight(1f))

        if (state.error != null) {
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Button(
            onClick = { onEvent(LoginScreenEvent.OnNextButtonClick(state.location)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary
            ),
            enabled = !state.isLoading && state.location.firstName.isNotBlank() && state.location.coordinateMobile.isNotBlank()
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onTertiary,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text(
                    text = "مرحله بعد",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
private fun LoginHeader(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = androidx.compose.material.icons.Icons.Default.ArrowBack,
                contentDescription = "Back"
            )
        }

        Text(
            text = "ثبت نام",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}


@Composable
private fun GenderSelector(
    selectedGender: String,
    onGenderSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(8.dp)
                )
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(8.dp)
                ),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = stringResource(R.string.gender),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp),
            )
            GenderOption(
                text = stringResource(R.string.male),
                selected = selectedGender == "FEMALE",
                onClick = { onGenderSelected("FEMALE") },
                modifier = Modifier.weight(1f)
            )
            GenderOption(
                text = stringResource(R.string.female),
                selected = selectedGender == "MALE",
                onClick = { onGenderSelected("MALE") },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun GenderOption(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = if (selected) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = if (selected) MaterialTheme.colorScheme.onPrimaryContainer
            else MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGenderOption(){
    UbarTheme {
        GenderOption(
            text = stringResource(R.string.gender),
            selected = true,
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGenderSelector() {
    UbarTheme {
        GenderSelector(
            selectedGender = "MALE",
            onGenderSelected = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginHeader() {
    UbarTheme {
        LoginHeader(
            onBackClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen(
        state = LoginScreenState(),
        onEvent = {},
        onNavigateBack = {}
    )
}


