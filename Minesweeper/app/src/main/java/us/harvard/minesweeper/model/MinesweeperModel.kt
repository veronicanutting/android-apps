package us.harvard.minesweeper.model

import java.util.*

object MinesweeperModel {

    var fieldMatrix = Array<Array<Field>>(5) { Array<Field>(5)
    {Field(false, 0, false,false)} }

    public fun resetModel() {
        for (i in 0..4) {
            for (j in 0..4) {
                fieldMatrix[i][j].isBomb = false
                fieldMatrix[i][j].minesAround = 0
                fieldMatrix[i][j].isFlagged = false
                fieldMatrix[i][j].wasClicked = false
            }
        }

        // hardcoding bombs
        fieldMatrix[0][0].isBomb = true
        fieldMatrix[1][1].isBomb = true
        fieldMatrix[2][2].isBomb = true

        // hardcoding minesAround values
        // this will be a test of whether fist index is "x" or "y"
        fieldMatrix[0][1].minesAround = 2
        fieldMatrix[0][2].minesAround = 1
        fieldMatrix[1][0].minesAround = 2
        fieldMatrix[1][2].minesAround = 2
        fieldMatrix[1][3].minesAround = 1
        fieldMatrix[2][0].minesAround = 1
        fieldMatrix[2][1].minesAround = 2
        fieldMatrix[2][3].minesAround = 1
        fieldMatrix[2][3].minesAround = 1
        fieldMatrix[3][1].minesAround = 1
        fieldMatrix[3][2].minesAround = 1
        fieldMatrix[3][3].minesAround = 1

    }

}


