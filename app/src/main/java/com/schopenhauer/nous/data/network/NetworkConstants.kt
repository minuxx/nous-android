package com.schopenhauer.nous.data.network

const val READ_TIMEOUT_SECONDS = 15L
const val CONNECT_TIMEOUT_SECONDS = 15L
const val WRITE_TIMEOUT_SECONDS = 10L

const val X_NAVER_CLIENT_ID_HEADER = "X-Naver-Client-Id"
const val X_NAVER_CLIENT_SECRET_HEADER = "X-Naver-Client-Secret"
const val NAVER_SEARCH_BASE_URL = "https://openapi.naver.com/v1/"
const val NAVER_SEARCH_PAGE_SIZE = 20
const val NAVER_SEARCH_EMPLOYMENT_NEWS_QUERY = "채용"
const val NAVER_SEARCH_NEWS_SORT_STRATEGY = "date"


enum class NetworkError(val code: String, val message: String) {
	CONNECT("NET400", "네트워크 연결을 확인해주세요"),
}

enum class NetworkOwner(val codeKey: String, val messageKey: String) {
	NOUS("code", "message"),
	NAVER("errorCode", "errorMessage")
}