package us.harvard.minesweeper.model

import android.util.Log
import java.util.*
import kotlin.random.Random

object MinesweeperModel {

    // GET FROM INPUT
    val gridSize : Int = 5
    val totalBombs : Int = 3

    var fieldMatrix = Array<Array<Field>>(gridSize + 2) {
        Array<Field>(gridSize + 2)
        { Field(false, 0, false, false) }
    }

    public fun resetModel() {
        resetFields()
        resetPadding()
        placeBombs()
    }

    private fun resetFields() {
        for (i in 0..gridSize + 1) {
            for (j in 0..gridSize + 1) {
                fieldMatrix[i][j].isBomb = false
                fieldMatrix[i][j].minesAround = 0
                fieldMatrix[i][j].isFlagged = false
                fieldMatrix[i][j].wasClicked = false
            }
        }
    }

    private fun resetPadding() {
        for (i in 0..gridSize + 1) {
            fieldMatrix[0][i].minesAround = -1
            fieldMatrix[i][0].minesAround = -1
            fieldMatrix[gridSize+1][i].minesAround = -1
            fieldMatrix[i][gridSize+1].minesAround = -1
        }
    }

    private fun placeBombs() {
        Log.d("BOMB PLACEMENT", "")
        var x : Int = 0
        var y : Int = 0
        var bombsPlaced : Int = 0

        while (bombsPlaced < totalBombs) {
            x = Random.nextInt(1, gridSize+1)
            y = Random.nextInt(1, gridSize+1)

            if (!fieldMatrix[x][y].isBomb) {
                fieldMatrix[x][y].isBomb = true
                bombsPlaced++
                updateMinesAround(x,y)
                Log.d("BOMB PLACEMENT", "Bomb #$bombsPlaced is at fieldMatrix[$x][$y].")
            }
        }
    }

    private fun updateMinesAround(x : Int, y : Int) {

        // top left
        if(!fieldMatrix[x-1][y-1].isBomb && fieldMatrix[x-1][y-1].minesAround > -1) {
            fieldMatrix[x-1][y-1].minesAround ++
        }
        // top center
        if(!fieldMatrix[x][y-1].isBomb && fieldMatrix[x][y-1].minesAround > -1) {
            fieldMatrix[x][y-1].minesAround ++
        }

        // top right
        if(!fieldMatrix[x+1][y-1].isBomb && fieldMatrix[x+1][y-1].minesAround > -1) {
            fieldMatrix[x+1][y-1].minesAround ++
        }

        // middle left
        if(!fieldMatrix[x-1][y].isBomb && fieldMatrix[x-1][y].minesAround > -1) {
            fieldMatrix[x-1][y].minesAround ++
        }

        // middle right
        if(!fieldMatrix[x+1][y].isBomb && fieldMatrix[x+1][y].minesAround > -1) {
            fieldMatrix[x+1][y].minesAround ++
        }

        // bottom left
        if(!fieldMatrix[x-1][y+1].isBomb && fieldMatrix[x-1][y+1].minesAround > -1) {
            fieldMatrix[x-1][y+1].minesAround ++
        }

        // bottom middle
        if(!fieldMatrix[x][y+1].isBomb && fieldMatrix[x][y+1].minesAround > -1) {
            fieldMatrix[x][y+1].minesAround ++
        }

        // bottom middle
        if(!fieldMatrix[x+1][y+1].isBomb && fieldMatrix[x+1][y+1].minesAround > -1) {
            fieldMatrix[x+1][y+1].minesAround ++
        }
    }

}


