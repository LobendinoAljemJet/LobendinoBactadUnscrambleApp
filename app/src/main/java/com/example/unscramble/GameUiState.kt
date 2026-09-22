package com.example.unscramble

data class GameUiState(
    val currentScrambledWord: String = "",
    val userAnswer: String = "",
    val score: Int = 0,
    val isGameOver: Boolean = false
)
