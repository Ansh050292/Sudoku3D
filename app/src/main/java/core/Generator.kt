package com.example.sudoku3d.core

import kotlin.random.Random

fun generatePuzzle(difficulty: Int = 0) {

    val board = createEmptyBoard()

    // 1) Gültige Komplett-Lösung (4x4 Sudoku Grundmuster)
    val template = arrayOf(
        intArrayOf(1,2,3,4),
        intArrayOf(3,4,1,2),
        intArrayOf(2,1,4,3),
        intArrayOf(4,3,2,1)
    )

    for (z in 0 until SIZE)
        for (y in 0 until SIZE)
            for (x in 0 until SIZE) {
                board[x][y][z].value = template[(y + z) % SIZE][x]
                board[x][y][z].fixed = true
            }

    // 2) Schwierigkeit
    val removeRatio = when(difficulty) {
        0 -> 0.35   // Easy
        1 -> 0.55   // Medium
        else -> 0.75 // Hard
    }

    // 3) Zahlen entfernen
    for (z in 0 until SIZE)
        for (y in 0 until SIZE)
            for (x in 0 until SIZE)
                if (Random.nextDouble() < removeRatio) {
                    board[x][y][z].value = 0
                    board[x][y][z].fixed = false
                }

    // 4) Compose-Trigger: **NEUE Board-KOPIE setzen**
    boardState.value = board.map { layer ->
        layer.map { row ->
            row.map { Cell(it.value, it.fixed) }.toTypedArray()
        }.toTypedArray()
    }.toTypedArray()
}

