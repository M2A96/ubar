package io.maa96.ubar.presentation.ui.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.maa96.ubar.R
import io.maa96.ubar.presentation.theme.*

@Composable
fun ValidationIcon(
    isValid: Boolean,
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = Icons.Default.Check,
        contentDescription = stringResource(
            if (isValid) R.string.content_description_valid
            else R.string.content_description_invalid
        ),
        tint = if (isValid) LightSecondary else LightOnBackground,
        modifier = modifier
    )
}

@Composable
fun FormTextField(
    labelResourceId: Int,
    value: String,
    onValueChange: (String) -> Unit,
    isValid: Boolean = false,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(labelResourceId),
            color = LightPrimary,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Box {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = LightPrimary,
                    unfocusedBorderColor = LightBorder,
                    focusedTextColor = LightOnSurface,
                    unfocusedTextColor = LightOnSurface,
                    cursorColor = LightPrimary,
                    focusedContainerColor = LightSurfaceVariant,
                    unfocusedContainerColor = LightSurface
                )
            )
            if (value.isNotEmpty()) {
                ValidationIcon(
                    isValid = isValid,
                    modifier = Modifier.align(Alignment.CenterEnd)
                        .padding(end = 12.dp)
                )
            }
        }
    }
}

@Composable
fun GenderToggle(
    selected: Genders,
    onGenderSelected: (Genders) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(
                color = LightSurface,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.dp,
                color = LightBorder,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        GenderButton(
            textResourceId = R.string.gender_male,
            isSelected = selected == Genders.MALE,
            onClick = { onGenderSelected(Genders.MALE) },
            modifier = Modifier.weight(1f)
        )
        GenderButton(
            textResourceId = R.string.gender_female,
            isSelected = selected == Genders.FEMALE,
            onClick = { onGenderSelected(Genders.FEMALE) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun GenderButton(
    textResourceId: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) LightSecondary else LightSurface,
            contentColor = if (isSelected) LightPrimary else LightOnBackground
        ),
        shape = RoundedCornerShape(0.dp),
        modifier = modifier.fillMaxHeight()
    ) {
        Text(
            text = stringResource(textResourceId),
            color = if (isSelected) LightSelectedText else LightOnBackground,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        )
    }
}

enum class Genders {
    MALE, FEMALE
}

@Composable
fun RegistrationHeader(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(LightSurface),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = stringResource(R.string.registration_title),
            fontSize = 18.sp,
            color = LightPrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
        IconButton(
            onClick = onBackClick,
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.button_back),
                tint = LightPrimary
            )
        }
    }
}

@Composable
fun RegistrationScreen(
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(LightBackground)
        ) {
            // Keep header outside of scrollable content
            Column(
            ) {
                RegistrationHeader(onBackClick = onBackClick)

                Text(
                    text = stringResource(R.string.registration_subtitle),
                    color = LightOnBackground,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }

            // Make the form content scrollable
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 20.dp)
            ) {
                var firstName by remember { mutableStateOf("") }
                var lastName by remember { mutableStateOf("") }
                var mobile by remember { mutableStateOf("") }
                var phone by remember { mutableStateOf("") }
                var address by remember { mutableStateOf("") }
                var gender by remember { mutableStateOf(Genders.MALE) }

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = LightSurface,
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, LightBorder)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        FormTextField(
                            labelResourceId = R.string.label_first_name,
                            value = firstName,
                            onValueChange = { firstName = it },
                            isValid = firstName.length >= 3,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        FormTextField(
                            labelResourceId = R.string.label_last_name,
                            value = lastName,
                            onValueChange = { lastName = it },
                            isValid = lastName.length >= 3,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        FormTextField(
                            labelResourceId = R.string.label_mobile,
                            value = mobile,
                            onValueChange = { mobile = it },
                            isValid = mobile.matches(Regex("^09\\d{9}$")),
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        FormTextField(
                            labelResourceId = R.string.label_phone,
                            value = phone,
                            onValueChange = { phone = it },
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        FormTextField(
                            labelResourceId = R.string.label_address,
                            value = address,
                            onValueChange = { address = it },
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        Text(
                            text = stringResource(R.string.label_gender),
                            color = LightPrimary,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        GenderToggle(
                            selected = gender,
                            onGenderSelected = { gender = it }
                        )

                        Button(
                            onClick = onNextClick,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = LightSecondary,
                                contentColor = LightPrimary
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.button_next),
                                modifier = Modifier.padding(vertical = 4.dp),
                                fontSize = 20.sp,
                                color = LightSelectedText,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
@Preview
@Composable
fun RegistrationScreenPreview() {
    RegistrationScreen(
        onBackClick = {},
        modifier = Modifier.fillMaxSize(),
        onNextClick = {}
    )
}