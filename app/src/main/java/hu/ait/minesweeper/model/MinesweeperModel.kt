package hu.ait.minesweeper.model

import hu.ait.minesweeper.view.Field
import hu.ait.minesweeper.view.MinesweeperView
import kotlin.random.Random

object MinesweeperModel {


    val fieldMatrix: Array<Array<Field>> = arrayOf(
        arrayOf(Field(0, false, false, false),
                Field(0, false, false, false),
                Field(0, false, false, false),
                Field(0, false, false, false),
                Field(0, false, false, false)),
        arrayOf(Field(0, false, false, false),
                Field(0, false, false, false),
                Field(0, false, false, false),
                Field(0, false, false, false),
                Field(0, false, false, false)),
        arrayOf(Field(0, false, false, false),
                Field(0, false, false, false),
                Field(0, false, false, false),
                Field(0, false, false, false),
                Field(0, false, false, false)),
        arrayOf(Field(0, false, false, false),
                Field(0, false, false, false),
                Field(0, false, false, false),
                Field(0, false, false, false),
                Field(0, false, false, false)),
        arrayOf(Field(0, false, false, false),
                Field(0, false, false, false),
                Field(0, false, false, false),
                Field(0, false, false, false),
                Field(0, false, false, false)))

    public fun getClickedStatus(x: Int, y: Int) = fieldMatrix[x][y].wasClicked

    public fun getFlagStatus(x: Int, y: Int) = fieldMatrix[x][y].isFlagged

    public fun getBombStatus(x: Int, y: Int) = fieldMatrix[x][y].hasMine

    public fun setClicked(x: Int, y: Int) {
        fieldMatrix[x][y].wasClicked = true
    }

    public fun setFlag(x: Int, y: Int) {
        fieldMatrix[x][y].isFlagged = true
    }

    public fun setBomb(x: Int, y: Int) {
        fieldMatrix[x][y].hasMine = true
    }

    public fun getMinesAround(x: Int, y: Int) = fieldMatrix[x][y].minesAround

    public fun setMinesAround(x: Int, y: Int) {
        fieldMatrix[x][y].minesAround++
    }

    public fun resetModel() {
        for (i in 0..4) {
            for (j in 0..4) {
                fieldMatrix[i][j] = Field(0, false, false, false)
            }
        }
        pickMines()

    }

    public fun pickMines() {
        for (i in 0..2) {
            val row = Random.nextInt(0, 4)
            val col = Random.nextInt(0, 4)
            setBomb(col, row)
            updateMinesNearby(col, row)
        }
    }

    private fun updateMinesNearby(x: Int, y: Int) {
        if (x-1 >= 0) {
            MinesweeperModel.setMinesAround(x-1, y)
        }
        if (y-1 >= 0) {
            MinesweeperModel.setMinesAround(x, y-1)
        }
        if (y+1 <= 4) {
            MinesweeperModel.setMinesAround(x, y + 1)
        }
        if (x+1 <= 4) {
            MinesweeperModel.setMinesAround(x + 1, y)
        }
        if (x-1 >= 0 && y-1 >= 0) {
            MinesweeperModel.setMinesAround(x-1, y-1)
        }
        if (x-1 >=0 && y+1 <= 4) {
            MinesweeperModel.setMinesAround(x-1, y+1)
        }
        if (x+1 <= 4 && y-1 >= 0) {
            MinesweeperModel.setMinesAround(x+1, y-1)
        }
        if (x+1 <= 4 && y+1 <= 4) {
            MinesweeperModel.setMinesAround(x+1, y+1)
        }

    }

}