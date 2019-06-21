package us.harvard.minesweeper.model

import android.util.Log
import java.util.*
import kotlin.random.Random

object MinesweeperModel {

    // matrix has padding of 1 unit
    val GRID_SIZE : Int  = 5
    var fieldMatrix = Array<Array<Field>>(GRID_SIZE + 2) {
        Array<Field>(GRID_SIZE + 2)
        { Field(false, 0, false, false) }
    }

    public fun resetModel() {

        // reset matrix
        for (i in 0..GRID_SIZE+1) {
            for (j in 0..GRID_SIZE + 1) {
                fieldMatrix[i][j].isBomb = false
                fieldMatrix[i][j].minesAround = 0
                fieldMatrix[i][j].isFlagged = false
                fieldMatrix[i][j].wasClicked = false
            }
        }

        // padding
        for (i in 0..GRID_SIZE+1) {
            fieldMatrix[0][i].minesAround = -1
            fieldMatrix[GRID_SIZE+1][i].minesAround = -1
            fieldMatrix[i][0].minesAround = -1
            fieldMatrix[i][GRID_SIZE+1].minesAround = -1
        }

        // RANDOMLY GENERATES INDEXES FOR SOME # OF BOMBS
        val NUM_BOMBS : Int = 3

        var x : Int = 0
        var y : Int = 0
        var bombs_placed : Int = 0

        Log.d("RAND", "BOMB PLACEMENT")
        while (bombs_placed < NUM_BOMBS) {
            x = Random.nextInt(1, GRID_SIZE+1)
            y = Random.nextInt(1, GRID_SIZE+1)

            if (!fieldMatrix[x][y].isBomb) {
                fieldMatrix[x][y].isBomb = true
                bombs_placed++
                Log.d("RAND", "Bomb #$bombs_placed is at fieldMatrix ($x, $y)")


                // PADDING
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

    }

}


