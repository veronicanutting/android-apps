package us.harvard.minesweeper.model

data class Field(var isBomb: Boolean, var minesAround: Int,
                 var isFlagged: Boolean, var wasClicked: Boolean)