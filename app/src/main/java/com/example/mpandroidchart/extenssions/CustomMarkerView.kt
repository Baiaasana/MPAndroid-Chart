package com.example.mpandroidchart.extenssions

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import android.widget.TextView
import androidx.core.view.get
import com.example.mpandroidchart.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF

class CustomMarkerView(context: Context, layoutResource: Int, private val lineChart: LineChart) :
    MarkerView(context, layoutResource) {

    private val tvContent: TextView = findViewById(R.id.tvContent)
    private val linePaint: Paint = Paint()

    init {
        linePaint.color = Color.RED
        linePaint.strokeWidth = 2f
        linePaint.style = Paint.Style.STROKE
    }


    @SuppressLint("SetTextI18n")
    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        val xLabel = lineChart.xAxis.valueFormatter.getFormattedValue(e!!.x)
        e?.let {
            tvContent.text = "${e.y} ${xLabel.toString()} "
        }

//        lineChart.data.dataSets.filter { !it.isVisible }.forEach {
//            if(highlight?.x != null && it.valueFormatter.getFormattedValue(e.x) == xLabel){
//                visibility = View.GONE
//            }else{
//                visibility = View.VISIBLE
//            }
//        }

        super.refreshContent(e, highlight)
    }

    override fun draw(canvas: Canvas?, posX: Float, posY: Float) {
        canvas?.let {
            // Draw a vertical line at the bottom of the highlighted y-value
            val highlightPos = lineChart.getHighlightByTouchPoint(posX, posY)
            if (highlightPos != null) {
                val x = highlightPos.xPx
                val y = lineChart.viewPortHandler.contentBottom()
                canvas.drawLine(x, y, x, 2f, linePaint)
            }
        }
        super.draw(canvas, posX, posY)
    }

    override fun getOffset(): MPPointF {
        return MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
    }


}
