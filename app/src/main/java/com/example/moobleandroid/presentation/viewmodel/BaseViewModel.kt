package com.example.moobleandroid.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

abstract class BaseViewModel<UI_STATE, UI_EFFECT>(
    initialState: UI_STATE
) : ViewModel(), KoinComponent {

    private val _viewState = MutableStateFlow(initialState)
    val viewState = _viewState.asStateFlow()

    private val _viewEffect = MutableSharedFlow<UI_EFFECT>()
    val viewEffect = _viewEffect.asSharedFlow()

    protected fun updateState(update: (UI_STATE) -> UI_STATE) {
        _viewState.update(update)
    }

    protected fun sendEffect(effect: UI_EFFECT) {
        viewModelScope.launch {
            _viewEffect.emit(effect)
        }
    }
}