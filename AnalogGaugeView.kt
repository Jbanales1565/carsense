package com.example.final_part2

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

class AnalogGaugeView(context: Context) : View(context) {
    // Constructor and other necessary methods

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawColor(Color.WHITE) // Example background color

        // Example drawing code
        val centerX = width / 2
        val centerY = height / 2
        val radius = Math.min(centerX, centerY).toFloat()

        val paint = Paint()
        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5f

        canvas?.drawCircle(centerX.toFloat(), centerY.toFloat(), radius, paint)
    }
}
