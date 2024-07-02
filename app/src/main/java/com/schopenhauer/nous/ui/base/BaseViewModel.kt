package com.schopenhauer.nous.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

interface UiEvent
interface UiState
interface UiEffect

abstract class BaseViewModel<State : UiState, Event : UiEvent, Effect : UiEffect> : ViewModel() {
	private val initialState: State by lazy { createInitialState() }
	abstract fun createInitialState() : State

	private val _uiState = MutableStateFlow(initialState)
	val uiState = _uiState.asStateFlow()

	private val currentState: State
		get() = uiState.value

	private val _uiEvent = MutableSharedFlow<Event>()
	val uiEvent = _uiEvent.asSharedFlow()

	private val _uiEffect = Channel<Effect>(capacity = Channel.BUFFERED)
	val uiEffect = _uiEffect.receiveAsFlow()

	init {
		subscribeUiEvent()
	}

	fun setUiState(reduce: State.() -> State) {
		val state = currentState.reduce()
		_uiState.value = state
	}

	fun setUiEvent(event: Event) {
		viewModelScope.launch {
			_uiEvent.emit(event)
		}
	}

	fun setUiEffect(builder: () -> Effect) {
		val effect = builder()
		viewModelScope.launch {
			_uiEffect.send(effect)
		}
	}

	private fun subscribeUiEvent() {
		viewModelScope.launch {
			uiEvent.collect {
				handleUiEvent(it)
			}
		}
	}

	abstract fun handleUiEvent(event: Event)
}