package com.schopenhauer.nous.ui.journal_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.schopenhauer.nous.R
import com.schopenhauer.nous.ui.theme.NousTheme

@Composable
fun TaskItem(
	modifier: Modifier = Modifier,
	content: String,
	onClick: () -> Unit,
	onClickIcon: (() -> Unit)? = null,
) {
	Surface(onClick = onClick) {
		Row(
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceBetween,
			modifier = modifier
				.fillMaxWidth()
				.heightIn(dimensionResource(id = R.dimen.list_item_height_medium)),
		) {
			Text(
				text = content,
				style = MaterialTheme.typography.bodyLarge
			)
			if (onClickIcon != null) {
				IconButton(onClick = onClickIcon) {
					Icon(
						painter = painterResource(id = R.drawable.ic_trash),
						contentDescription = stringResource(id = R.string.content_description_delete_task),
						tint = MaterialTheme.colorScheme.error,
						modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size_medium))
					)
				}
			}
		}
	}
}

@Preview(showBackground = true)
@Composable
fun TaskItemLightPreview() {
	NousTheme {
		TaskItem(
			content = "컴포즈 마이그레이션",
			onClick = {},
			onClickIcon = {},
			modifier = Modifier.padding(
				horizontal = dimensionResource(id = R.dimen.padding_medium),
				vertical = dimensionResource(id = R.dimen.padding_small)
			)
		)
	}
}

@Preview(showBackground = true)
@Composable
fun TaskItemDarkPreview() {
	NousTheme(darkTheme = true) {
		TaskItem(
			content = "컴포즈 마이그레이션",
			onClick = {},
			onClickIcon = {},
			modifier = Modifier.padding(
				horizontal = dimensionResource(id = R.dimen.padding_medium),
				vertical = dimensionResource(id = R.dimen.padding_small)
			)
		)
	}
}