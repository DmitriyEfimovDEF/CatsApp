package ru.dimadef.cats.app.data

data class Data<T>(
    val value: T,
    val state: State
)

sealed class State {
    object Loading : State()
    object Success : State()
    data class Error(val error: Throwable) : State()
}