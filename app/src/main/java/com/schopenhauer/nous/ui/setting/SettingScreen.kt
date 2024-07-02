package com.schopenhauer.nous.ui.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.schopenhauer.nous.R
import com.schopenhauer.nous.ui.theme.NousTheme

@Composable
fun SettingScreen(
	modifier: Modifier = Modifier,
	viewModel: SettingViewModel = hiltViewModel()
) {
	val uiState by viewModel.uiState.collectAsState()

	SettingScreen(
		modifier = modifier,
		version = uiState.version,
		onClick = {}
	)
}

@Composable
fun SettingScreen(
	modifier: Modifier = Modifier,
	version: String,
	onClick: () -> Unit
) {
	Column(
		modifier = modifier.fillMaxSize()
	) {
		Text(
			modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
			text = stringResource(id = R.string.setting),
			style = MaterialTheme.typography.titleLarge
		)
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(dimensionResource(id = R.dimen.padding_medium)),
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceBetween
		) {
			Text(
				text = stringResource(id = R.string.version),
				style = MaterialTheme.typography.bodyLarge
			)
			Text(
				text = version,
				style = MaterialTheme.typography.labelMedium
			)
		}
		HorizontalDivider()

		Row(
			modifier = Modifier
				.fillMaxWidth()
				.clickable { onClick() },
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceBetween
		) {
			Text(
				modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
				text = stringResource(id = R.string.notification),
				style = MaterialTheme.typography.bodyLarge
			)
		}
		HorizontalDivider()

		Row(
			modifier = Modifier
				.fillMaxWidth()
				.clickable { onClick() },
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceBetween
		) {
			Text(
				modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
				text = stringResource(id = R.string.terms_of_use),
				style = MaterialTheme.typography.bodyLarge
			)
			Icon(
				modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
				painter = painterResource(id = R.drawable.ic_angle_right_sm),
				contentDescription = null,
				tint = MaterialTheme.colorScheme.outlineVariant
			)
		}
		HorizontalDivider()

		Row(
			modifier = Modifier
				.fillMaxWidth()
				.clickable { onClick() },
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceBetween
		) {
			Text(
				modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
				text = stringResource(id = R.string.privacy_policy),
				style = MaterialTheme.typography.bodyLarge
			)
			Icon(
				modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
				painter = painterResource(id = R.drawable.ic_angle_right_sm),
				contentDescription = null,
				tint = MaterialTheme.colorScheme.outlineVariant
			)
		}
		HorizontalDivider()

		Row(
			modifier = Modifier
				.fillMaxWidth()
				.clickable { onClick() },
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceBetween
		) {
			Text(
				modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
				text = stringResource(id = R.string.open_source_license),
				style = MaterialTheme.typography.bodyLarge
			)
			Icon(
				modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
				painter = painterResource(id = R.drawable.ic_angle_right_sm),
				contentDescription = null,
				tint = MaterialTheme.colorScheme.outlineVariant
			)
		}
		HorizontalDivider()
	}
}

@Preview(showBackground = true)
@Composable
fun SettingScreenLightPreview() {
	NousTheme {
		SettingScreen(
			version = "1.0.0",
			onClick = {}
		)
	}
}