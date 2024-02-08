package com.schopenhauer.nous.util

enum class ErrorType(val code: String, val message: String) {
  UNKNOWN("UnknownError", "알 수 없는 에러가 발생했습니다"),
  JSON("JsonError", "Json 파싱 중 에러가 발생했습니다"),
  LOCAL("LocalError", "로컬 작업에 실패했습니다"),
  NETWORK("NetworkError", "네트워크 연결을 확인해주세요")
}
