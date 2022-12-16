package com.example.sudokumobsys

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class SudokuFieldView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private var sqrtSize = 3
    private var size = 9

    private var cellSizePixels = 0F

    private val thickLinePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 5F
    }

    private val thinLinePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 2F
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val sizePixels = Math.min(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(sizePixels, sizePixels)
    }

    override fun onDraw(canvas: Canvas) {
        cellSizePixels = (width / size).toFloat()

        drawLines(canvas)
    }

    private fun drawLines(canvas: Canvas) {

        for (i in 0 until size + 1) {
            val paintToUse = when (i % sqrtSize) {
                0 -> thickLinePaint
                else -> thinLinePaint
            }

            canvas.drawLine(/* startX = */ i * cellSizePixels, /* startY = */
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
}