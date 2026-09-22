package com.example.unscramble

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel : ViewModel() {

    // UI state exposed to the UI layer
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    // Internal game variables
    val words = listOf("CAT", "DOG", "BOOK")
    private var currentWordIndex = 0
    private lateinit var currentWord: String

    init {
        resetGame()
    }

    fun resetGame() {
        currentWordIndex = 0
        currentWord = words[currentWordIndex]
        _uiState.value = GameUiState(
            currentScrambledWord = shuffleCurrentWord(currentWord),
            userAnswer = "",
            score = 0,
            isGameOver = false
        )
    }

    fun updateUserAnswer(guessedWord: String) {
        _uiState.update { currentState ->
            currentState.copy(userAnswer = guessedWord)
        }
    }

    fun checkUserGuess() {
        val currentAnswer = _uiState.value.userAnswer
        if (currentAnswer.equals(currentWord, ignoreCase = true)) {
            val updatedScore = _uiState.value.score + 1
            if (currentWordIndex < words.size - 1) {
                currentWordIndex++
                currentWord = words[currentWordIndex]
                _uiState.update { currentState ->
                    currentState.copy(
                        currentScrambledWord = shuffleCurrentWord(currentWord),
                        userAnswer = "",
                        score = updatedScore
                    )
                }
            } else {
                _uiState.update { currentState ->
                    currentState.copy(
                        userAnswer = "",
                        score = updatedScore,
                        isGameOver = true
                    )
                }
            }
        } else {
            // Clear incorrect answer input
            updateUserAnswer("")
        }
    }

    private fun shuffleCurrentWord(word: String): String {
        val tempWord = word.toCharArray()
        tempWord.shuffle()
        while (String(tempWord) == word && word.length > 1) {
            tempWord.shuffle()
        }
        return String(tempWord)
    }
}
