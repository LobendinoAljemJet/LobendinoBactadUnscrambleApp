package com.example.unscramble

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.unscramble.ui.theme.UnscrambleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnscrambleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GameScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    gameViewModel: GameViewModel = viewModel()
) {
    val gameUiState by gameViewModel.uiState.collectAsState()

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "UNSCRAMBLE",
            fontSize = 30.sp
        )
        Text(
            text = if (gameUiState.isGameOver) "DONE!" else gameUiState.currentScrambledWord,
            fontSize = 40.sp
        )
        Text(
            text = if (gameUiState.isGameOver) "You finished the game!" else "Unscramble the word!"
        )
        if (!gameUiState.isGameOver) {
            OutlinedTextField(
                value = gameViewModel.userAnswer,
                onValueChange = {
                    gameViewModel.updateUserAnswer(it)
                },
                label = {
                    Text("Enter your answer")
                }
            )
            Button(
                onClick = {
                    gameViewModel.checkUserGuess()
                }
            ) {
                Text("SUBMIT")
            }
        } else {
            Button(
                onClick = {
                    gameViewModel.resetGame()
                }
            ) {
                Text("PLAY AGAIN")
            }
        }
        Text(
            text = "Score: ${gameUiState.score}"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    UnscrambleTheme {
        GameScreen()
    }
}