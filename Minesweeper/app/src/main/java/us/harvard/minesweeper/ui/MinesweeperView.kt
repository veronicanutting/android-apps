package us.harvard.minesweeper.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import us.harvard.minesweeper.MainActivity
import us.harvard.minesweeper.model.MinesweeperModel
import us.harvard.minesweeper.model.MinesweeperModel.fieldMatrix
import us.harvard.minesweeper.model.MinesweeperModel.totalBombs


class MinesweeperView(context: Context?, attrs: AttributeSet?) : View(context, attrs)
{
    var paintBackGround : Paint? = null
    var paintLine : Paint? = null
    var paintText : Paint? = null

    init {
        paintBackGround = Paint()
        paintBackGround?.color = Color.LTGRAY
        paintBackGround?.style = Paint.Style.FILL

        paintLine = Paint()
        paintLine?.color = Color.WHITE
        paintLine?.style = Paint.Style.STROKE
        paintLine?.strokeWidth = 5f

        paintText = Paint()
        paintText?.color = Color.GREEN
        paintText?.textSize = 50f
    }

    // GET FROM INPUT
    val gridSize : Int = 5
    val totalBombs : Int = 3

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawRect(0f,0f,
            width.toFloat(),height.toFloat(), paintBackGround!!)

        drawGrid(canvas)
        drawFields(canvas)
    }

    private fun drawGrid(canvas: Canvas?) {
        paintLine!!.setColor(Color.BLACK)

        canvas?.drawRect(
            0f, 0f,
            width.toFloat(), height.toFloat(), paintLine!!)

        for (i in 1..gridSize-1) {

            // horizontal lines
            canvas?.drawLine(
                0f, i * height.toFloat() / gridSize,
                width.toFloat(), i * height.toFloat() / gridSize, paintLine!!)

            // vertical lines
            canvas?.drawLine(
                i * width.toFloat() / gridSize, 0f, i * width.toFloat() / gridSize,
                height.toFloat(), paintLine!!)
        }

    }


    private fun drawFields(canvas: Canvas?) {

        for (i in 1..gridSize) {
            for (j in 1..gridSize) {

                if (fieldMatrix[i][j].wasClicked) {

                    if (!fieldMatrix[i][j].isFlagged) {

                        canvas?.drawText(fieldMatrix[i][j].minesAround.toString(),
                                (((i - 1) * width) / gridSize + width / (2 * gridSize)).toFloat(),
                                (((j - 1) * height) / gridSize + height / (2 * gridSize)).toFloat(), paintText!!)

                    } else if (fieldMatrix[i][j].wasClicked && fieldMatrix[i][j].isFlagged) {
                        paintLine!!.setColor(Color.WHITE)

                        val centerX = (((i - 1) * width) / gridSize + width / (2 * gridSize)).toFloat()
                        val centerY = (((j - 1) * height) / gridSize + height / (2 * gridSize)).toFloat()
                        val radius = height / (3 * gridSize) //??

                        canvas?.drawCircle(centerX, centerY, radius.toFloat(), paintLine!!)
                    }

                }

            }
        }

    }


    override fun onTouchEvent(event: MotionEvent): Boolean {

        val tX = event.x.toInt() / (width /gridSize)
        val tY = event.y.toInt() / (height /gridSize)
        val flagMode = (context as MainActivity).isFlagChecked()
        var bombsFound = 0

        if (tX < gridSize && tY < gridSize && !fieldMatrix[tX+1][tY+1].wasClicked) {
            fieldMatrix[tX+1][tY+1].wasClicked = true

            if (flagMode) {
                if (fieldMatrix[tX+1][tY+1].isBomb) {
                    fieldMatrix[tX+1][tY+1].isFlagged = true
                    bombsFound ++
                    if (bombsFound == MinesweeperModel.totalBombs) {
                        endGame("Congratulations! You found all the bombs! You win!")
                    }
                } else {
                    endGame("Uh oh! You made a fatal flagging error. You lose!")
                }

            } else {
                if (fieldMatrix[tX+1][tY+1].isBomb) {
                    endGame("Boom! You clicked on a bomb. You lose!")
                }
            }

            invalidate()
        }
        return super.onTouchEvent(event)
    }

    fun resetGame() {
        MinesweeperModel.resetModel()
        invalidate()
    }

    private fun endGame(message : String) {
        Toast.makeText(context as MainActivity, message, Toast.LENGTH_LONG).show()
        resetGame()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = View.MeasureSpec.getSize(widthMeasureSpec)
        val h = View.MeasureSpec.getSize(heightMeasureSpec)
        val d = if (w == 0) h else if (h == 0) w else if (w < h) w else h
        setMeasuredDimension(d, d)
    }

}