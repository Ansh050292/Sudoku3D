package com.example.sudoku3d.core

fun isValid(x: Int, y: Int, z: Int, num: Int): Boolean {
    val board = boardState.value

    for (i in 0 until SIZE) {
        if (i != x && board[i][y][z].value == num) return false
        if (i != y && board[x][i][z].value == num) return false
        if (i != z && board[x][y][i].value == num) return false
    }
    return true
}

fun isSolved(): Boolean {
    val board = boardState.value

    for (z in 0 until SIZE)
        for (y in 0 until SIZE)
            for (x in 0 until SIZE) {
                val v = board[x][y][z].value
                if (v == 0 || !isValid(x, y, z, v)) return false
            }

    return true
}

