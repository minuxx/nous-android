package com.schopenhauer.nous.ui.news.list

import android.text.Html
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.schopenhauer.nous.R
import com.schopenhauer.nous.domain.model.News
import com.schopenhauer.nous.ui.theme.NousTheme

@Composable
fun NewsScreen(
	modifier: Modifier = Modifier,
	newses: List<News>,
	onNewsClick: (String) -> Unit
) {
	Column(
		modifier = modifier.fillMaxSize()
	) {
		Text(
			modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
			text = stringResource(id = R.string.news),
			style = MaterialTheme.typography.titleLarge
		)
		NewsItemColumn(
			newses = newses,
			onNewsClick = onNewsClick
		)
	}
}

@Preview(showBackground = true)
@Composable
fun NewsScreenLightPreview() {
	NousTheme {
		NewsScreen(
			newses = listOf(
				News(
					id = "https:n.news.naver.com/mnews/article/018/0005778160?sid=101",
					title = "국토부 항공교통본부장 등 정부 5개 개방형 직위 공개모집",
					description = "정부 고위공무원단 및 과장급 직위에 전문성과 역량을 갖춘 인재를 영입하기 위한 ‘2024년도 7월 개방형 직위 공개모집’을 실시한다고 인사혁신처가 1일 밝혔다. 개방형 직위는 전문성이 요구되거나 효율적인 정책... ",
					date = "Mon, 01 Jul 2024 12:01:00 +0900",
					link = "https:n.news.naver.com/mnews/article/018/0005778160?sid=101"
				),
				News(
					id = "https:n.news.naver.com/mnews/article/018/0005778160?sid=102",
					title = "국토부 항공교통본부장 등 정부 5개 개방형 직위 공개모집",
					description = "정부 고위공무원단 및 과장급 직위에 전문성과 역량을 갖춘 인재를 영입하기 위한 ‘2024년도 7월 개방형 직위 공개모집’을 실시한다고 인사혁신처가 1일 밝혔다. 개방형 직위는 전문성이 요구되거나 효율적인 정책... ",
					date = "Mon, 01 Jul 2024 12:01:00 +0900",
					link = "https:n.news.naver.com/mnews/article/018/0005778160?sid=101"
				),
				News(
					id = "https:n.news.naver.com/mnews/article/018/0005778160?sid=103",
					title = "국토부 항공교통본부장 등 정부 5개 개방형 직위 공개모집",
					description = "정부 고위공무원단 및 과장급 직위에 전문성과 역량을 갖춘 인재를 영입하기 위한 ‘2024년도 7월 개방형 직위 공개모집’을 실시한다고 인사혁신처가 1일 밝혔다. 개방형 직위는 전문성이 요구되거나 효율적인 정책... ",
					date = "Mon, 01 Jul 2024 12:01:00 +0900",
					link = "https:n.news.naver.com/mnews/article/018/0005778160?sid=101"
				)
			),
			onNewsClick = {}
		)
	}
}

@Preview(showBackground = true)
@Composable
fun NewsScreenDarkPreview() {
	NousTheme(darkTheme = true) {
		NewsScreen(
			newses = listOf(
				News(
					id = "https:n.news.naver.com/mnews/article/018/0005778160?sid=101",
					title = "국토부 항공교통본부장 등 정부 5개 개방형 직위 공개모집",
					description = "정부 고위공무원단 및 과장급 직위에 전문성과 역량을 갖춘 인재를 영입하기 위한 ‘2024년도 7월 개방형 직위 공개모집’을 실시한다고 인사혁신처가 1일 밝혔다. 개방형 직위는 전문성이 요구되거나 효율적인 정책... ",
					date = "Mon, 01 Jul 2024 12:01:00 +0900",
					link = "https:n.news.naver.com/mnews/article/018/0005778160?sid=101"
				),
				News(
					id = "https:n.news.naver.com/mnews/article/018/0005778160?sid=102",
					title = "국토부 항공교통본부장 등 정부 5개 개방형 직위 공개모집",
					description = "정부 고위공무원단 및 과장급 직위에 전문성과 역량을 갖춘 인재를 영입하기 위한 ‘2024년도 7월 개방형 직위 공개모집’을 실시한다고 인사혁신처가 1일 밝혔다. 개방형 직위는 전문성이 요구되거나 효율적인 정책... ",
					date = "Mon, 01 Jul 2024 12:01:00 +0900",
					link = "https:n.news.naver.com/mnews/article/018/0005778160?sid=101"
				),
				News(
					id = "https:n.news.naver.com/mnews/article/018/0005778160?sid=103",
					title = "국토부 항공교통본부장 등 정부 5개 개방형 직위 공개모집",
					description = "정부 고위공무원단 및 과장급 직위에 전문성과 역량을 갖춘 인재를 영입하기 위한 ‘2024년도 7월 개방형 직위 공개모집’을 실시한다고 인사혁신처가 1일 밝혔다. 개방형 직위는 전문성이 요구되거나 효율적인 정책... ",
					date = "Mon, 01 Jul 2024 12:01:00 +0900",
					link = "https:n.news.naver.com/mnews/article/018/0005778160?sid=101"
				)
			),
			onNewsClick = {}
		)
	}
}

@Composable
fun NewsItemColumn(
	modifier: Modifier = Modifier,
	newses: List<News>,
	onNewsClick: (String) -> Unit
) {
	LazyColumn(
		modifier = modifier.fillMaxWidth(),
		contentPadding = PaddingValues(vertical = dimensionResource(id = R.dimen.padding_small))
	) {
		items(
			items = newses,
			key = { item ->
				item.id
			}
		) { item ->
			NewsItem(
				title = Html.fromHtml(item.title, Html.FROM_HTML_MODE_LEGACY).toString(),
				description = Html.fromHtml(item.description, Html.FROM_HTML_MODE_LEGACY).toString(),
				date = item.date,
				onClick = { onNewsClick(item.id) }
			)
		}
	}
}

@Preview(showBackground = true)
@Composable
fun NewsItemColumnLightPreview() {
	NousTheme {
		NewsItemColumn(
			newses = listOf(
				News(
					id = "https:n.news.naver.com/mnews/article/018/0005778160?sid=101",
					title = "국토부 항공교통본부장 등 정부 5개 개방형 직위 공개모집",
					description = "정부 고위공무원단 및 과장급 직위에 전문성과 역량을 갖춘 인재를 영입하기 위한 ‘2024년도 7월 개방형 직위 공개모집’을 실시한다고 인사혁신처가 1일 밝혔다. 개방형 직위는 전문성이 요구되거나 효율적인 정책... ",
					date = "Mon, 01 Jul 2024 12:01:00 +0900",
					link = "https:n.news.naver.com/mnews/article/018/0005778160?sid=101"
				),
				News(
					id = "https:n.news.naver.com/mnews/article/018/0005778160?sid=102",
					title = "국토부 항공교통본부장 등 정부 5개 개방형 직위 공개모집",
					description = "정부 고위공무원단 및 과장급 직위에 전문성과 역량을 갖춘 인재를 영입하기 위한 ‘2024년도 7월 개방형 직위 공개모집’을 실시한다고 인사혁신처가 1일 밝혔다. 개방형 직위는 전문성이 요구되거나 효율적인 정책... ",
					date = "Mon, 01 Jul 2024 12:01:00 +0900",
					link = "https:n.news.naver.com/mnews/article/018/0005778160?sid=101"
				),
				News(
					id = "https:n.news.naver.com/mnews/article/018/0005778160?sid=103",
					title = "국토부 항공교통본부장 등 정부 5개 개방형 직위 공개모집",
					description = "정부 고위공무원단 및 과장급 직위에 전문성과 역량을 갖춘 인재를 영입하기 위한 ‘2024년도 7월 개방형 직위 공개모집’을 실시한다고 인사혁신처가 1일 밝혔다. 개방형 직위는 전문성이 요구되거나 효율적인 정책... ",
					date = "Mon, 01 Jul 2024 12:01:00 +0900",
					link = "https:n.news.naver.com/mnews/article/018/0005778160?sid=101"
				)
			),
			onNewsClick = {}
		)
	}
}

@Preview(showBackground = true)
@Composable
fun NewsItemColumnDarkPreview() {
	NousTheme(darkTheme = true) {
		NewsItemColumn(
			newses = listOf(
				News(
					id = "https:n.news.naver.com/mnews/article/018/0005778160?sid=101",
					title = "국토부 항공교통본부장 등 정부 5개 개방형 직위 공개모집",
					description = "정부 고위공무원단 및 과장급 직위에 전문성과 역량을 갖춘 인재를 영입하기 위한 ‘2024년도 7월 개방형 직위 공개모집’을 실시한다고 인사혁신처가 1일 밝혔다. 개방형 직위는 전문성이 요구되거나 효율적인 정책... ",
					date = "Mon, 01 Jul 2024 12:01:00 +0900",
					link = "https:n.news.naver.com/mnews/article/018/0005778160?sid=101"
				),
				News(
					id = "https:n.news.naver.com/mnews/article/018/0005778160?sid=102",
					title = "국토부 항공교통본부장 등 정부 5개 개방형 직위 공개모집",
					description = "정부 고위공무원단 및 과장급 직위에 전문성과 역량을 갖춘 인재를 영입하기 위한 ‘2024년도 7월 개방형 직위 공개모집’을 실시한다고 인사혁신처가 1일 밝혔다. 개방형 직위는 전문성이 요구되거나 효율적인 정책... ",
					date = "Mon, 01 Jul 2024 12:01:00 +0900",
					link = "https:n.news.naver.com/mnews/article/018/0005778160?sid=101"
				),
				News(
					id = "https:n.news.naver.com/mnews/article/018/0005778160?sid=103",
					title = "국토부 항공교통본부장 등 정부 5개 개방형 직위 공개모집",
					description = "정부 고위공무원단 및 과장급 직위에 전문성과 역량을 갖춘 인재를 영입하기 위한 ‘2024년도 7월 개방형 직위 공개모집’을 실시한다고 인사혁신처가 1일 밝혔다. 개방형 직위는 전문성이 요구되거나 효율적인 정책... ",
					date = "Mon, 01 Jul 2024 12:01:00 +0900",
					link = "https:n.news.naver.com/mnews/article/018/0005778160?sid=101"
				)
			),
			onNewsClick = {}
		)
	}
}

@Composable
fun NewsItem(
	modifier: Modifier = Modifier,
	title: String,
	description: String,
	date: String,
	onClick: () -> Unit
) {
	Column(
		modifier = modifier
			.fillMaxWidth()
			.clickable { onClick() },
	) {
		Column(
			modifier = Modifier.padding(
				horizontal = dimensionResource(id = R.dimen.padding_medium),
				vertical = dimensionResource(id = R.dimen.padding_small)
			),
			verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
		) {
			Text(
				text = Html.fromHtml(title, Html.FROM_HTML_MODE_LEGACY).toString(),
				style = MaterialTheme.typography.titleMedium
			)
			Text(
				text = Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY).toString(),
				style = MaterialTheme.typography.bodyMedium,
				maxLines = 4,
			)
			Text(
				text = date,
				style = MaterialTheme.typography.bodySmall
			)
		}
		HorizontalDivider()
	}
}

@Preview(showBackground = true)
@Composable
fun NewsItemLightPreview() {
	NousTheme {
		NewsItem(
			title = "산업인력공단, '환경' 분야 22개 종목 산업 동향·응시 현황 등 통계 제공",
			description = "67.5%(7871명)는 <b>채용<\\/b>, 19.4%(2259명)는 임금(수당 등), 8.3%(968명)는 인사고과에서 자격을 우대한다고 답했다. 이우영 산업인력공단 이사장은 &quot;ESG 경영의 중요성이 커짐에 따라 환경 분야에 대한 국민의 관심도가... ",
			date = "Mon, 01 Jul 2024 12:01:00 +0900",
			onClick = {}
		)
	}
}
