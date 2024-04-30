package com.schopenhauer.nous.data.network

const val READ_TIMEOUT_SECONDS = 15L
const val CONNECT_TIMEOUT_SECONDS = 15L
const val WRITE_TIMEOUT_SECONDS = 10L

enum class NetworkError(val code: String, val message: String) {
	CONNECT("NET400", "네트워크 연결을 확인해주세요"),
}

enum class NetworkOwner(val baseUrl: String, val codeKey: String, val messageKey: String) {
	NOUS("", "code", "message"),
	NAVER("https://openapi.naver.com/v1/", "errorCode", "errorMessage")
}