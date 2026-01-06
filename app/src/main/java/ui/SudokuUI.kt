package com.example.sudoku3d.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sudoku3d.core.*

@Composable
fun SudokuUI() {

    val board = boardState.value

    var layer by remember { mutableStateOf(0) }
    var noobMode by remember { mutableStateOf(true) }
    var difficulty by remember { mutableStateOf(0) }
    var showWin by remember { mutableStateOf(false) }

    Column(Modifier.padding(16.dp)) {

        Text("Ebene Z = $layer", fontSize = 22.sp)

        for (y in 0 until SIZE) {
            Row {
                for (x in 0 until SIZE) {
                    val cell = board[x][y][layer]

                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .border(1.dp, Color.Black)
                            .background(
                                when {
                                    cell.fixed -> Color.LightGray
                                    noobMode && cell.value != 0 && !isValid(x, y, layer, cell.value) ->
                                        Color.Red.copy(alpha = 0.5f)
                                    else -> Color.White
                                }
                            )
                            .clickable(enabled = !cell.fixed) {

                                cell.value = (cell.value % SIZE) + 1

                                // 🔥 Compose-Refresh erzwingen (KEIN map / KEIN toTypedArray)
                                val newBoard = Array(SIZE) { ix ->
                                    Array(SIZE) { iy ->
                                        Array(SIZE) { iz ->
                                            val c = board[ix][iy][iz]
                                            Cell(c.value, c.fixed)
                                        }
                                    }
                                }

                                boardState.value = newBoard

                                if (isSolved()) showWin = true
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(if (cell.value == 0) "" else cell.value.toString())
                    }
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        Button(onClick = { layer = (layer + 1) % SIZE }) {
            Text("Ebene wechseln")
        }

        Spacer(Modifier.height(8.dp))

        Row {
            Button(onClick = { difficulty = 0; generatePuzzle(0) }) { Text("Easy") }
            Spacer(Modifier.width(6.dp))
            Button(onClick = { difficulty = 1; generatePuzzle(1) }) { Text("Medium") }
            Spacer(Modifier.width(6.dp))
            Button(onClick = { difficulty = 2; generatePuzzle(2) }) { Text("Hard") }
        }

        Spacer(Modifier.height(8.dp))

        Button(onClick = { noobMode = !noobMode }) {
            Text(if (noobMode) "Noob-Modus: AN" else "Noob-Modus: AUS")
        }
    }

    if (showWin) {
        AlertDialog(
            onDismissRequest = {},
            confirmButton = {
                Button(onClick = {
                    showWin = false
                    generatePuzzle(difficulty)
                }) { Text("Neues Spiel") }
            },
            title = { Text("🎉 Gewonnen!") },
            text = { Text("Du hast das Sudoku korrekt gelöst!") }
        )
    }
}
