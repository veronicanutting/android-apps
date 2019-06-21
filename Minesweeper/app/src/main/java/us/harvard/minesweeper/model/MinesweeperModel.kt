package us.harvard.minesweeper.model

import android.util.Log
import java.util.*
import kotlin.random.Random

object MinesweeperModel {

    var fieldMatrix = Array<Array<Field>>(5) {
        Array<Field>(5)
        { Field(false, 0, false, false) }
    }

    public fun resetModel() {
        for (i in 0..4) {
            for (j in 0..4) {
                fieldMatrix[i][j].isBomb = false
                fieldMatrix[i][j].minesAround = 0
                fieldMatrix[i][j].isFlagged = false
                fieldMatrix[i][j].wasClicked = false
            }
        }


        // RANDOMLY GENERATES INDEXES FOR SOME # OF BOMBS
        val NUM_BOMBS : Int = 3

        var x : Int = 0
        var y : Int = 0
        var bombs_placed : Int = 0

        while (bombs_placed < NUM_BOMBS - 1) {
            Log.d("RAND", "GENERATING RANDOM NUMS FOR BOMB PLACEMENT")
            x = Random.nextInt(0, 5)
            y = Random.nextInt(0, 5)
            Log.d("RAND", "Bomb #$bombs_placed is at ($x, $y)")

            if (!fieldMatrix[x][y].isBomb) {
                fieldMatrix[x][y].isBomb = true
                bombs_placed++

                // now will do mines around stuff
            }

        }

        /*
        // hardcoding bombs
        fieldMatrix[0][0].isBomb = true
        fieldMatrix[1][1].isBomb = true
        fieldMatrix[2][2].isBomb = true
*/

        // CALCULATES MINESAROUND VALUES FOR WHOLE BOARD
        // this could go in the for loop that generates bombs

        /*

        // would be something like


         */





        // hardcoding minesAround values
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

    /*
    //private fun generateRandom(): Random = Random(5)

    private fun generateRandom(a : Int) : Int {
        val rand = java.util.Random(a.toLong())
        return rand.nextInt(5) // number between 0 and 99 inclusive
    }*/

}


