package com.schopenhauer.nous.util

enum class ErrorType(val code: String, val message: String) {
  UNKNOWN("UnknownError", "알 수 없는 에러가 발생했습니다"),
  JSON("JsonError", "Json 파싱 중 에러가 발생했습니다"),
  LOCAL("LocalError", "로컬 작업에 실패했습니다"),
  NETWORK("NetworkError", "네트워크 연결을 확인해주세요"),

  FAIL_LOAD_JOURNALS("FailLoadJournals", "업무 일지를 불러오는데 실패했어요"),
  TASK_CONTENT_EMPTY("TaskContentEmpty", "업무 내용을 입력해주세요"),
  ALREADY_SAVED_JOURNAL("AlreadySavedJournal", "해당 날짜에 업무 일지가 이미 존재해요"),
  FAIL_SAVE_JOURNAL("FailSaveJournal", "업무 일지를 저장하지 못했어요"),
}
