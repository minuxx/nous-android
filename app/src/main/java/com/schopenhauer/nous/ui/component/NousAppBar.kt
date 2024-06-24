package com.schopenhauer.nous.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.schopenhauer.nous.R

@Composable
fun NousAppBar(
	modifier: Modifier = Modifier,
	title: String,
	onLeftIconClick: () -> Unit,
	onRightClickIcon: () -> Unit
) {
	Row(
		verticalAlignment = Alignment.CenterVertically,
		modifier = Modifier.padding(
			end = dimensionResource(id = R.dimen.padding_medium)
		)
	) {
		IconButton(
			onClick = onLeftIconClick,
		) {
			Icon(
				painter = painterResource(id = R.drawable.ic_angle_left),
				contentDescription = null,
			)
		}
		Text(
			text = title,
			style = MaterialTheme.typography.titleSmall
		)
		Spacer(modifier = Modifier.weight(1f))
		TextButton(onClick = onRightClickIcon) {
			Text(
				text = stringResource(id = R.string.delete),
				style = MaterialTheme.typography.titleSmall,
			)
		}
	}
}