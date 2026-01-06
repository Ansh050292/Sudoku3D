package com.example.sudoku3d

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.sudoku3d.core.generatePuzzle
import com.example.sudoku3d.ui.SudokuUI

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        generatePuzzle()
        setContent { SudokuUI() }
    }
}
