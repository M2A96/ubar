package io.maa96.ubar.presentation.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.maa96.ubar.R
import io.maa96.ubar.domain.model.Address

@Composable
fun ListScreen(
    state: ListScreenState,
    onNavigateBack: () -> Unit,
    onAddAddressClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            ListHeader(
                onBackClick = onNavigateBack
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.your_addresses),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(48.dp)
                    )
                }
            } else if (state.addresses.isNullOrEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.no_registred_address),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn {
                    items(state.addresses) { address ->
                        AddressCard(address = address)
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = onAddAddressClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.add_address)
            )
        }
    }
}

@Composable
private fun ListHeader(
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
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back"
            )
        }

        Text(
            text = stringResource(R.string.my_address),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun AddressCard(
    address: Address,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = address.address,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = address.coordinateMobile,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Start
                )

                Text(
                    text = address.firstNameLastName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListScreenPreview() {
    val addresses = listOf(
        Address(
            address = "تهران، خیابان ولیعصر",
            coordinateMobile = "09123456789",
            firstNameLastName = "محمد احمدی"
        ),
        Address(
            address = "تهران، خیابان شریعتی",
            coordinateMobile = "09198765432",
            firstNameLastName = "علی محمدی"
        )
    )

    MaterialTheme {
        ListScreen(
            state = ListScreenState(addresses = addresses),
            onNavigateBack = {},
            onAddAddressClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddressCardPreview() {
    val address = Address(
        address = "تهران، خیابان ولیعصر",
        coordinateMobile = "09123456789",
        firstNameLastName = "محمد احمدی"
    )

    MaterialTheme {
        Surface {
            AddressCard(address = address)
        }
    }
}