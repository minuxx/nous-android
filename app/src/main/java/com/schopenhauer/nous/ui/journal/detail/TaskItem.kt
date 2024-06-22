package com.schopenhauer.nous.ui.journal.detail

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.schopenhauer.nous.R
import com.schopenhauer.nous.ui.theme.NousTheme

@Composable
fun TaskItem(
	content: String,
	isDeletable: Boolean,
	onClick: () -> Unit,
	onDelete: () -> Unit,
	modifier: Modifier = Modifier
) {
	Row(
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.SpaceBetween,
		modifier = modifier
			.fillMaxWidth()
			.heightIn(48.dp)
	) {
		Text(
			text = content,
			style = MaterialTheme.typography.bodyLarge
		)
		if (isDeletable) {
			IconButton(onClick = onDelete) {
				Icon(
					painter = painterResource(id = R.drawable.ic_trash),
					contentDescription = stringResource(id = R.string.content_description_delete_task),
					tint = MaterialTheme.colorScheme.error,
					modifier = Modifier.size(24.dp)
				)
			}
		}
	}
}

@Preview(showBackground = true)
@Composable
fun TaskItemLightPreview() {
	NousTheme {
		Surface {
			TaskItem(
				content = "컴포즈 마이그레이션",
				isDeletable = true,
				onClick = {},
				onDelete = {},
				modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
			)
		}
	}
}

@Preview(showBackground = true)
@Composable
fun TaskItemDarkPreview() {
	NousTheme(darkTheme = true) {
		Surface {
			TaskItem(
				content = "컴포즈 마이그레이션",
				isDeletable = false,
				onClick = {},
				onDelete = {},
				modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
			)
		}
	}
}