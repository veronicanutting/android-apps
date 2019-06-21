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

    val GRID_SIZE : Int  = 5

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // fill in background
        canvas?.drawRect(0f,0f,
            width.toFloat(),height.toFloat(), paintBackGround!!)

        // draw grid
        drawFields(canvas)

        drawGrid(canvas)
    }


    private fun drawGrid(canvas: Canvas?) {

        paintLine!!.setColor(Color.BLACK)

        canvas?.drawRect(
            0f, 0f,
            width.toFloat(), height.toFloat(), paintLine!!)

        // draw horizontal and vertical lines
        for (i in 1..GRID_SIZE-1) {
            canvas?.drawLine(
                0f, i * height.toFloat() / GRID_SIZE,
                width.toFloat(), i * height.toFloat() / GRID_SIZE, paintLine!!)

            canvas?.drawLine(
                i * width.toFloat() / GRID_SIZE, 0f, i * width.toFloat() / GRID_SIZE,
                height.toFloat(), paintLine!!)
        }

    }


    private fun drawFields(canvas: Canvas?) {

        Log.d("DRAW_FIELD", "DRAWING FIELDS")

        for (i in 1..GRID_SIZE) {
            for (j in 1..GRID_SIZE) {

                if (fieldMatrix[i][j].wasClicked) {
                    Log.d("DRAW_FIELD", "Apparently fieldMatrix ($i,$j) was clicked")

                    if (!fieldMatrix[i][j].isFlagged) {
                        Log.d("DRAW_FIELD", "We should draw the number ${fieldMatrix[i][j].minesAround.toString()}")

                        canvas?.drawText(fieldMatrix[i][j].minesAround.toString(),
                                (((i-1) * width) / GRID_SIZE + width / (2*GRID_SIZE)).toFloat(),
                                (((j-1) * height) / GRID_SIZE + height / (2*GRID_SIZE)).toFloat(), paintText!!)

                    }
                        else if (fieldMatrix[i][j].wasClicked && fieldMatrix[i][j].isFlagged) {
                            Log.d("DRAW_FIELD", "We should draw flag at grid (${i-1},${j-1})")

                            paintLine!!.setColor(Color.WHITE)

                            val centerX = (((i-1) * width) / GRID_SIZE + width / (2*GRID_SIZE)).toFloat()
                            val centerY = (((j-1) * height) / GRID_SIZE + height / (2*GRID_SIZE)).toFloat()
                            val radius = height / (GRID_SIZE * 3) //??
                            canvas?.drawCircle(centerX, centerY, radius.toFloat(), paintLine!!)
                        }

                }
            }
        }
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {

        // store where you clicked
        val tX = event.x.toInt() / (width /GRID_SIZE)
        val tY = event.y.toInt() / (height /GRID_SIZE)
        val flagMode = (context as MainActivity).isFlagChecked()

        Log.d("ON_TOUCH", "Apparently ($tX,$tY) was touched on grid")

        if (tX < GRID_SIZE && tY < GRID_SIZE && !fieldMatrix[tX+1][tY+1].wasClicked) {
            Log.d("ON_TOUCH", "Corresponds to fieldMatrix (${tX+1},${tY+1}), which had not been previously clicked")

            fieldMatrix[tX+1][tY+1].wasClicked = true

            if (flagMode) {
                if (fieldMatrix[tX+1][tY+1].isBomb) {
                    fieldMatrix[tX+1][tY+1].isFlagged = true
                } else {
                    endGame("You've made a fatal flagging error!!")
                }
            } else {
                if (fieldMatrix[tX+1][tY+1].isBomb) {
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