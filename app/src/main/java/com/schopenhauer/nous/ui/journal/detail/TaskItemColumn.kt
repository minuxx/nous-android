package com.schopenhauer.nous.ui.journal.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.schopenhauer.nous.R
import com.schopenhauer.nous.domain.model.Task
import com.schopenhauer.nous.ui.theme.NousTheme

@Composable
fun TaskItemColumn(
	modifier: Modifier = Modifier,
	tasks: List<Task>,
	onClick: () -> Unit,
	onClickIcon: (() -> Unit)? = null,
) {
	LazyColumn(
		contentPadding = PaddingValues(vertical = dimensionResource(id = R.dimen.padding_small)),
		modifier = modifier.fillMaxWidth()
	) {
		items(
			items = tasks,
			key = { item ->
				item.id
			}
		) { item ->
			TaskItem(
				content = item.content,
				onClick = onClick,
				onClickIcon = onClickIcon,
				modifier = Modifier.padding(
					horizontal = dimensionResource(id = R.dimen.padding_medium),
					vertical = dimensionResource(id = R.dimen.padding_small)
				)
			)
		}
	}
}

@Preview(showBackground = true)
@Composable
fun TaskItemColumnLightPreview() {
	NousTheme {
		Surface {
			TaskItemColumn(
				tasks = listOf(
					Task(1, "컴포즈 마이그레이션"),
					Task(2, "컴포즈 마이그레이션"),
					Task(3, "컴포즈 마이그레이션"),
					Task(4, "컴포즈 마이그레이션")),
				onClick = {},
				onClickIcon = {}
			)
		}
	}
}

@Preview(showBackground = true)
@Composable
fun TaskItemColumnDarkPreview() {
	NousTheme(darkTheme = true) {
		Surface {
			TaskItemColumn(
				tasks = listOf(
					Task(1, "컴포즈 마이그레이션"),
					Task(2, "컴포즈 마이그레이션"),
					Task(3, "컴포즈 마이그레이션"),
					Task(4, "컴포즈 마이그레이션")),
				onClick = {},
				onClickIcon = {}
			)
		}
	}
}