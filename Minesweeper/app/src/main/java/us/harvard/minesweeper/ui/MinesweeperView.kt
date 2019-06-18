package us.harvard.minesweeper.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import us.harvard.minesweeper.MainActivity
import us.harvard.minesweeper.model.Field
import us.harvard.minesweeper.model.MinesweeperModel
import us.harvard.minesweeper.model.MinesweeperModel.fieldMatrix


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
        paintText?.textSize = 50f
        paintText?.color = Color.GREEN
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // fill in background
        canvas?.drawRect(0f,0f,
            width.toFloat(),height.toFloat(), paintBackGround!!)

        // draw grid
        drawGrid(canvas)
        drawFields(canvas)
    }


    private fun drawGrid(canvas: Canvas?) {

        paintLine!!.setColor(Color.BLACK)

        canvas?.drawRect(
            0f, 0f,
            width.toFloat(), height.toFloat(), paintLine!!)

        // draw horizontal and vertical lines
        for (i in 1..4) {
            canvas?.drawLine(
                0f, i * height.toFloat() / 5,
                width.toFloat(), i * height.toFloat() / 5, paintLine!!)

            canvas?.drawLine(
                i * width.toFloat() / 5, 0f, i * width.toFloat() / 5,
                height.toFloat(), paintLine!!)
        }

    }


    private fun drawFields(canvas: Canvas?) {

        for (i in 0..4) {
            for (j in 0..4) {

                if (fieldMatrix[i][j].wasClicked) {

                        if (!fieldMatrix[i][j].isFlagged) {

                            canvas?.drawText(fieldMatrix[i][j].minesAround.toString(),
                                (i * width / 5 + width / 10).toFloat(),
                                (j * height / 5 + height / 10).toFloat(), paintText!!)

                        }
                        else if (fieldMatrix[i][j].wasClicked && fieldMatrix[i][j].isFlagged) {

                            paintLine!!.setColor(Color.WHITE)

                            val centerX = (i * width / 5 + width / 10).toFloat()
                            val centerY = (j * height / 5 + height / 10).toFloat()
                            val radius = height / (5 * 3) //??
                            canvas?.drawCircle(centerX, centerY, radius.toFloat(), paintLine!!)
                        }

                }
            }
        }
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {

        // store where you clicked
        val tX = event.x.toInt() / (width /5)
        val tY = event.y.toInt() / (height /5)
        val flagMode = (context as MainActivity).isFlagChecked()

        if (tX < 5 && tY < 5 && !fieldMatrix[tX][tY].wasClicked) {
            fieldMatrix[tX][tY].wasClicked = true

            if (flagMode) {
                if (fieldMatrix[tX][tY].isBomb) {
                    fieldMatrix[tX][tY].isFlagged = true
                } else {
                    endGame("You've made a fatal flagging error!!")
                }
            } else {
                if (fieldMatrix[tX][tY].isBomb) {
                    endGame("You clicked on a bomb while not in flag mode. Boom!!")
                }
            }

            invalidate()
        }
        return super.onTouchEvent(event)
    }


    // ensure dimensions scale
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = View.MeasureSpec.getSize(widthMeasureSpec)
        val h = View.MeasureSpec.getSize(heightMeasureSpec)
        val d = if (w == 0) h else if (h == 0) w else if (w < h) w else h
        setMeasuredDimension(d, d)
    }


    fun resetGame() {
        MinesweeperModel.resetModel()
        invalidate()
    }


    private fun endGame(message : String) {

        Toast.makeText(context as MainActivity, message, Toast.LENGTH_LONG).show()
        resetGame()
    }

}