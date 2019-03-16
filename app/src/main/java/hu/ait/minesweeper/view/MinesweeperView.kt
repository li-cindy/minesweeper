package hu.ait.minesweeper.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import hu.ait.minesweeper.MainActivity
import hu.ait.minesweeper.R
import hu.ait.minesweeper.model.MinesweeperModel
import kotlinx.android.synthetic.main.activity_main.*
import android.support.design.widget.Snackbar
import kotlin.random.Random

class MinesweeperView (context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paintBackground = Paint()
    private val paintLine = Paint()
    private val numMinesColor = Paint()
    private val clickedColor = Paint()

    private var coveredTile = BitmapFactory.decodeResource(resources, R.drawable.covered_tile)
    private var flag = BitmapFactory.decodeResource(resources, R.drawable.flag)
    private var mine = BitmapFactory.decodeResource(resources, R.drawable.bomb)

    private var numCorrectFlagsPlaced = 0
    private var isGameOver = false

    init {
        paintBackground.color = Color.GRAY
        paintBackground.style = Paint.Style.FILL

        paintLine.color = Color.DKGRAY
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeWidth = 8f

        numMinesColor.color = Color.BLUE

        clickedColor.color = Color.GRAY
        clickedColor.style = Paint.Style.FILL

        MinesweeperModel.pickMines()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        coveredTile = Bitmap.createScaledBitmap(coveredTile, width/5, height/5, false)
        flag = Bitmap.createScaledBitmap(flag, width/5, height/5, false)
        mine = Bitmap.createScaledBitmap(mine, width/5, height/5, false)

        numMinesColor.textSize = height / 5f

    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintBackground)

        drawGameBoard(canvas)

        drawClicked(canvas)

    }

    private fun drawGameBoard(canvas: Canvas?) {
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintLine)

        canvas?.drawLine(
            0f, (height / 5).toFloat(), width.toFloat(), (height / 5).toFloat(), paintLine
        )
        canvas?.drawLine(
            0f, (2 * height / 5).toFloat(), width.toFloat(), (2 * height / 5).toFloat(), paintLine
        )
        canvas?.drawLine(
            0f, (3 * height / 5).toFloat(), width.toFloat(), (3 * height / 5).toFloat(), paintLine
        )
        canvas?.drawLine(
            0f, (4 * height / 5).toFloat(), width.toFloat(), (4 * height / 5).toFloat(), paintLine
        )

        canvas?.drawLine(
            (width / 5).toFloat(), 0f, (width / 5).toFloat(), height.toFloat(), paintLine
        )
        canvas?.drawLine(
            (2 * width / 5).toFloat(), 0f, (2 * width / 5).toFloat(), height.toFloat(), paintLine
        )
        canvas?.drawLine(
            (3 * width / 5).toFloat(), 0f, (3 * width / 5).toFloat(), height.toFloat(), paintLine
        )
        canvas?.drawLine(
            (4 * width / 5).toFloat(), 0f, (4 * width / 5).toFloat(), height.toFloat(), paintLine
        )

    }

    private fun drawClicked(canvas: Canvas?) {
        for (i in 0..4) {
            for (j in 0..4) {
                if (!MinesweeperModel.getClickedStatus(i, j)) {
                    drawCoveredTile(canvas, i, j)
                }
                else if (MinesweeperModel.getClickedStatus(i, j) && !(context as MainActivity).flagButton.isChecked) {
                    drawUnflagged(i, j, canvas)
                }
                else if (MinesweeperModel.getClickedStatus(i, j) && (context as MainActivity).flagButton.isChecked) {
                    drawFlagged(i, j, canvas)
                }
            }
        }
    }

    private fun drawFlagged(i: Int, j: Int, canvas: Canvas?) {
        if (MinesweeperModel.getFlagStatus(i, j)) {
            drawFlagTile(canvas, i, j)
        }
        else if (MinesweeperModel.getBombStatus(i, j)) {
            drawMineTile(canvas, i, j)
        }
        else if (!MinesweeperModel.getBombStatus(i, j)) {
            drawNumMines(canvas, i, j)
        }
    }

    private fun drawUnflagged(i: Int, j: Int, canvas: Canvas?) {
        if (!MinesweeperModel.getFlagStatus(i, j)) {
            drawUncoveredTile(canvas, i, j)

            if (!MinesweeperModel.getBombStatus(i, j)) {
                drawNumMines(canvas, i, j)
            } else {
                drawMineTile(canvas, i, j)
            }
        } else {
            drawFlagTile(canvas, i, j)
        }
    }

    private fun drawCoveredTile(canvas: Canvas?, i: Int, j: Int) {
        canvas?.drawBitmap(coveredTile, (i * width / 5).toFloat(), (j * height / 5).toFloat(), null)
    }

    private fun drawMineTile(canvas: Canvas?, i: Int, j: Int) {
        canvas?.drawBitmap(mine, (i * width / 5 + 2).toFloat(), (j * height / 5 + 2).toFloat(), null)
    }

    private fun drawFlagTile(canvas: Canvas?, i: Int, j: Int) {
        canvas?.drawBitmap(flag, (i * width / 5).toFloat(), (j * height / 5).toFloat(), null)
    }

    private fun drawNumMines(canvas: Canvas?, i: Int, j: Int) {
        canvas?.drawText(
            MinesweeperModel.getMinesAround(i, j).toString(),
            (i * width / 5 + 40).toFloat(),
            (j * height / 5 + 150).toFloat(),
            numMinesColor
        )
    }

    private fun drawUncoveredTile(canvas: Canvas?, i: Int, j: Int) {
        canvas?.drawRect(
            (i * width / 5 + 2).toFloat(),
            (j * height / 5 + 2).toFloat(),
            (i * width / 5 + width / 5 - 2).toFloat(),
            (j * height / 5 + height / 5 - 2).toFloat(),
            clickedColor
        )
    }


    private fun showWinMessage() {
        Snackbar.make((context as MainActivity).contentLayout,
            context.getString(R.string.winMessage),
            Snackbar.LENGTH_LONG).show()
    }
    
    private fun showLossMessage() {
        Snackbar.make((context as MainActivity).contentLayout, 
            context.getString(R.string.lossMessage),
            Snackbar.LENGTH_LONG).show()
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (!isGameOver) {
            if (event?.action == MotionEvent.ACTION_DOWN) {
                val tX = event.x.toInt() / (width / 5)
                val tY = event.y.toInt() / (height / 5)

                if (tX < 5 && tY < 5 && !MinesweeperModel.getClickedStatus(tX, tY)) {
                    if ((context as MainActivity).flagButton.isChecked) {
                        MinesweeperModel.setFlag(tX, tY)
                    }
                    if ((MinesweeperModel.getBombStatus(tX, tY) && !MinesweeperModel.getFlagStatus(tX, tY)) ||
                        (!MinesweeperModel.getBombStatus(tX, tY) && MinesweeperModel.getFlagStatus(tX, tY))) {
                        isGameOver = true
                        showLossMessage()
                    }
                    if ((MinesweeperModel.getBombStatus(tX, tY) && MinesweeperModel.getFlagStatus(tX, tY))) {
                        numCorrectFlagsPlaced++
                        if (numCorrectFlagsPlaced == 3) {
                            isGameOver = true
                            showWinMessage()
                        }
                    }
                    MinesweeperModel.setClicked(tX, tY)
                }
            }
        }

        invalidate()
        return true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = View.MeasureSpec.getSize(widthMeasureSpec)
        val h = View.MeasureSpec.getSize(heightMeasureSpec)
        val d = if (w == 0) h else if (h == 0) w else if (w < h) w else h
        setMeasuredDimension(d, d)
    }

    public fun resetParameters() {
        isGameOver = false
        numCorrectFlagsPlaced = 0

    }

}
