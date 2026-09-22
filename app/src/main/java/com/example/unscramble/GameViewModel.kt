package com.example.unscramble

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class GameUiState(
    val currentScrambledWord: String = "",
    val score: Int = 0,
    val isGameOver: Boolean = false
)

class GameViewModel : ViewModel() {

    // UI state exposed to the UI layer
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    // Internal game variables
    val words = listOf("CAT", "DOG", "BOOK")
    private var currentWordIndex = 0
    private lateinit var currentWord: String

    // Two-way binding variable for user input handled safely
    var userAnswer by mutableStateOf("")
        private set

    init {
        resetGame()
    }

    fun resetGame() {
        currentWordIndex = 0
        userAnswer = ""
        currentWord = words[currentWordIndex]
        _uiState.value = GameUiState(
            currentScrambledWord = shuffleCurrentWord(currentWord),
            score = 0,
            isGameOver = false
        )
    }

    fun updateUserAnswer(guessedWord: String) {
        userAnswer = guessedWord
    }

    fun checkUserGuess() {
        if (userAnswer.equals(currentWord, ignoreCase = true)) {
            val updatedScore = _uiState.value.score + 1
            if (currentWordIndex < words.size - 1) {
                currentWordIndex++
                currentWord = words[currentWordIndex]
                _uiState.update { currentState ->
                    currentState.copy(
                        currentScrambledWord = shuffleCurrentWord(currentWord),
                        score = updatedScore
                    )
                }
            } else {
                _uiState.update { currentState ->
                    currentState.copy(
                        score = updatedScore,
                        isGameOver = true
                    )
                }
            }
        }
        updateUserAnswer("")
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
