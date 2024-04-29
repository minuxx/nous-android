package com.schopenhauer.nous.domain.model

interface Failure {
	fun code(): String
	fun message(): String
}