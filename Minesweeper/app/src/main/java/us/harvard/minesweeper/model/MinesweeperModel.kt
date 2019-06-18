package us.harvard.minesweeper.model

import java.util.*

object MinesweeperModel {

    var fieldMatrix = Array<Array<Field>>(5) { Array<Field>(5)
    {Field(false, 0, false,false)} }

    public fun resetModel() {
        for (i in 0..4) {
            for (j in 0..4) {
                fieldMatrix[i][j].wasClicked = false
                fieldMatrix[i][j].isBomb = false
                fieldMatrix[i][j].isFlagged = false
                fieldMatrix[i][j].minesAround = 0
            }
        }

        // hardcoding Bombs
        fieldMatrix[0][0].isBomb = true
        fieldMatrix[1][1].isBomb = true

    }

}


