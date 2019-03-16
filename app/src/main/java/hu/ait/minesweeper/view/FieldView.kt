package hu.ait.minesweeper.view

data class Field(var minesAround: Int, var isFlagged: Boolean,
                 var wasClicked: Boolean, var hasMine: Boolean)

