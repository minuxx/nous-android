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