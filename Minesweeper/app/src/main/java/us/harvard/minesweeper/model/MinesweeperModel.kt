package us.harvard.minesweeper.model

import java.util.*

object MinesweeperModel {

    var fieldMatrix = Array<Array<Field>>(5) { Array<Field>(5)
    {Field(false, 0, false,false)} }

    public fun resetModel() {
        for (i in 0..4) {
            for (j in 0..4) {
                fieldMatrix[i][j].wasClicked = false
                if (i==j) {
                    fieldMatrix[i][j].isBomb = true
                }

            }
        }
    }

}
