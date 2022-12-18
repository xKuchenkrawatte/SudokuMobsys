package com.example.sudokumobsys

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class SudokuFieldView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private var sqrtSize = 3       //Variables with the size of the field. 3x3 blocks are being generated until 9 fields are reached
    private var size = 9
    private var cellSizePixels = 0F

    private var selectedRow = 4         //This is the Point, where the selected row and column appears at the start of a new game
    private var selectedCol = 4

    private val thickLinePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK                         //configuration of the thick lines
        strokeWidth = 5F
    }

    private val thinLinePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK                         //configuration of the thin lines
        strokeWidth = 2F
    }

    private val selectedCellPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE                         //configuration of the selected cell paint
        color = Color.parseColor("#13b316")
    }

    private val conflictingCellPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE                         //configuration of the conflicting cell paint
        color = Color.parseColor("#d5d5d5")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val sizePixels = Math.min(widthMeasureSpec, heightMeasureSpec)          //onMeasure tells the view how large it should be. The size is set on min in the only read local variable "sizePixels"
        setMeasuredDimension(sizePixels, sizePixels)
    }

    override fun onDraw(canvas: Canvas) {
        cellSizePixels = (width / size).toFloat()                   //The canvas is the view, where lines and numbers are drawn. To float generates a border
        fillCells(canvas)
        drawLines(canvas)
    }

    private fun fillCells(canvas: Canvas){
        if (selectedRow == -1 || selectedCol == -1) return

        for (r in 0..size){
            for (c in 0..size){
                if(r == selectedRow && c == selectedCol) {                                                                  //In this function the color of the cells get generated. The selected cell, column, row and the 3x3 cube
                    fillCell(canvas, r, c, selectedCellPaint )
                } else if (r == selectedRow || c == selectedCol){
                    fillCell(canvas, r, c, conflictingCellPaint)
                } else if ( r / sqrtSize == selectedRow / sqrtSize && c / sqrtSize == selectedCol / sqrtSize) {
                    fillCell(canvas, r, c, conflictingCellPaint)
                }
            }
        }
    }

    private fun fillCell(canvas: Canvas, r: Int, c: Int, paint: Paint) {
        canvas.drawRect(c * cellSizePixels, r * cellSizePixels, (c+1) * cellSizePixels, (r+1) * cellSizePixels, paint)      //This function draws a rectangle to the start of the cell to the start of the next cell
    }

    private fun drawLines(canvas: Canvas) {

        for (i in 0 until size + 1) {
            val paintToUse = when (i % sqrtSize) {
                0 -> thickLinePaint
                else -> thinLinePaint
            }

            canvas.drawLine(/* startX = */ i * cellSizePixels, /* startY = */                           //In this function the lines on the canvas are being drawn. The configuration happened in the "thinLinePaint" and the "thickLinePaint"
                0F, /* stopX = */
                i * cellSizePixels, /* stopY = */
                height.toFloat(), /* paint = */
                paintToUse)

            canvas.drawLine(
                0F,
                i * cellSizePixels,
                width.toFloat(),
                i * cellSizePixels,
                paintToUse
            )
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when (event.action){
            MotionEvent.ACTION_DOWN -> {
                handleTouchEvent(event.x, event.y)
                true
            }
            else -> false                                                                   //In this Section the touchscreen registers an action. If you click on another cell, the colors change.
        }
    }

    private fun handleTouchEvent(x: Float, y: Float){
        selectedRow = (y / cellSizePixels).toInt()
        selectedCol = (x / cellSizePixels).toInt()
        invalidate()
    }
}