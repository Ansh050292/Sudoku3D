package com.example.sudoku3d.core

import androidx.compose.runtime.mutableStateOf

const val SIZE = 4

data class Cell(var value: Int = 0, var fixed: Boolean = false)

fun createEmptyBoard(): Array<Array<Array<Cell>>> =
    Array(SIZE) { Array(SIZE) { Array(SIZE) { Cell() } } }

val boardState = mutableStateOf(createEmptyBoard())
