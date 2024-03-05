package com.schopenhauer.nous.util

enum class ErrorType(val code: String, val message: String) {
  // 공용 에러
  UNKNOWN("UnknownError", "알 수 없는 에러가 발생했습니다"),
  JSON("JsonError", "Json 파싱 중 에러가 발생했습니다"),
  LOCAL("LocalError", "로컬 작업에 실패했습니다"),
  PARSE("ParseError", "파싱 작업에 실패했습니다."),
  NETWORK("NetworkError", "네트워크 연결을 확인해주세요"),

  // 도메인 관련 모듈로 분리
  FAIL_LOAD_JOURNALS("FailLoadJournals", "업무 일지를 불러오는데 실패했어요"),
  FAIL_SAVE_JOURNAL("FailSaveJournal", "업무 일지를 저장하지 못했어요"),
  TASK_CONTENT_EMPTY("TaskContentEmpty", "업무 내용을 입력해주세요"),
  ALREADY_SAVED_JOURNAL("AlreadySavedJournal", "해당 날짜에 업무 일지가 이미 존재해요"),
  FAIL_LOAD_JOURNAL("FailLoadJournal", "업무 일지를 읽는데 실패했어요"),
  FAIL_DELETE_JOURNAL("FailDeleteJournal", "업무 일지를 삭제하는데 실패했어요"),

  NAVER_SYSTEM("SE99", "서버 오류가 발생했어요.")
}

// 컨버터는 단순하게 API의 로우한 스테이터스나 메세지를 앱만의 에러 객체를 뽑아낸다.
// 레포지토리의 유틸로


// but, 단체

// 에러 타입은 한 곳에 모아두는 것보다 하나의 큰 에러 타입의 클래스가 있고
// 도메인별로 확장으로 추가해준다.