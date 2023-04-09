package com.example.mpandroidchart.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mpandroidchart.CheckBoxCallback
import com.example.mpandroidchart.R
import com.example.mpandroidchart.databinding.FragmentLineChartBinding
import com.example.mpandroidchart.extenssions.CustomMarkerView
import com.example.mpandroidchart.extenssions.findMinMax
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlin.math.roundToInt

class LineChartFragment : Fragment(), CheckBoxCallback {

    private lateinit var lineChart: LineChart

    private var _binding: FragmentLineChartBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LineChartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLineChartBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lineChart = binding.lineChart2
        viewModel.generateEntries(7)
        setCallbacks()
        setupChart()
        setDataToLineChart()
        listeners()
    }

    private fun setCallbacks() {
        binding.checkBoxEUR.setCallBack(this)
        binding.checkBoxRUB.setCallBack(this)
        binding.checkBoxUSD.setCallBack(this)
    }

    private fun listeners() {

        binding.apply {
            lastweek.setOnClickListener {
                viewModel.generateEntries(7)
                lineChart.xAxis.labelCount = 7
                setDataToLineChart()
                check()
                lineChart.invalidate()

            }
            lastMonth.setOnClickListener {
                viewModel.generateEntries(5)
                lineChart.xAxis.labelCount = 5
                setDataToLineChart()
                check()
                lineChart.invalidate()
            }
            lastYear.setOnClickListener {
                viewModel.generateEntries(12)
                lineChart.xAxis.labelCount = 12
                setDataToLineChart()
                MYsetXAxisLabelsYear()
                check()
                lineChart.invalidate()
            }
        }
    }

    private fun MYsetXAxisLabelsYear() {
        val xAxis = binding.lineChart2.xAxis
        xAxis.setLabelCount(12, true)
//        xAxis.mAxisRange = 12f
        val list = arrayListOf(
            "იან",
            "თებ",
            "მარ",
            "აპრ",
            "მაი",
            "ივნ",
            "ივლ",
            "აგვ",
            "სექ",
            "ოქტ",
            "ნოე",
            "დეკ"
        )
        xAxis.valueFormatter = IndexAxisValueFormatter(list)
        binding.lineChart2.marker = CustomMarkerView(requireContext(), R.layout.custom_marker_view, lineChart)
    }

    private fun setupChart() {
        lineChart.apply {
            animateX(1200, Easing.EaseInSine)
            setTouchEnabled(true)
            setPinchZoom(true)
            description.isEnabled = false
            legend.isEnabled = false
            axisRight.isEnabled = false
            isDoubleTapToZoomEnabled = false
            isKeepPositionOnRotation = true

            setNoDataText("სერვისი დროებით არ არის ხელმისაწვდომი")
            setNoDataTextColor(resources.getColor(R.color.error_color))

            xAxis.apply {
                setDrawGridLines(false)
                setDrawAxisLine(false)
                position = XAxis.XAxisPosition.BOTTOM
                granularity = 1f
                yOffset = 16f

            }

            val (usdMin, usdMax) = findMinMax(viewModel.currencyUSD.value!!.map { it.y })
            val (eurMin, eurMax) = findMinMax(viewModel.currencyEUR.value!!.map { it.y })
            val (rubMin, rubMax) = findMinMax(viewModel.currencyRUB.value!!.map { it.y })

            val min = listOf(usdMin, eurMin, rubMin).minOrNull() ?: 0f
            val max = listOf(usdMax, eurMax, rubMax).maxOrNull() ?: 0f

            Log.d(" log min1", " ".plus(min))
            Log.d("log max1", " ".plus(max))

            axisLeft.apply {
                setDrawGridLines(true)
                setDrawAxisLine(false)
                xOffset = 16f
//                axisMaximum = (max + 1f).roundToInt().toFloat()
//                axisMinimum = (min - 1f).roundToInt().toFloat()
            }
        }
    }

    private fun setDataToLineChart() {

        val lineChart = binding.lineChart2
        val dataSet = ArrayList<ILineDataSet>()

        val usdDataSet = LineDataSet(viewModel.currencyUSD.value, "USD")
        usdDataSet.apply {
            setCircleColor(resources.getColor(R.color.white_system))
            mode = LineDataSet.Mode.LINEAR
            color = ContextCompat.getColor(requireContext(), R.color.snackbar_text_color)
            valueTextColor = ContextCompat.getColor(requireContext(), R.color.textColorPrimary)
            circleHoleColor = resources.getColor(R.color.snackbar_text_color)
        }
        val eurDataSet = LineDataSet(viewModel.currencyEUR.value, "EUR")
        eurDataSet.apply {
            setCircleColor(resources.getColor(R.color.white_system))
            circleHoleColor = resources.getColor(R.color.setting_btn_color)
            mode = LineDataSet.Mode.LINEAR
            color = ContextCompat.getColor(requireContext(), R.color.setting_btn_color)
            valueTextColor = ContextCompat.getColor(requireContext(), R.color.textColorPrimary)

        }

        val rubDataSet = LineDataSet(viewModel.currencyRUB.value, "RUB")
        rubDataSet.apply {
            setCircleColor(resources.getColor(R.color.white_system))
            circleHoleColor = resources.getColor(R.color.warning_color)
            mode = LineDataSet.Mode.LINEAR
            color = ContextCompat.getColor(requireContext(), R.color.warning_color)
            valueTextColor = ContextCompat.getColor(requireContext(), R.color.textColorPrimary)

        }

        dataSet.add(usdDataSet)
        dataSet.add(eurDataSet)
        dataSet.add(rubDataSet)

        val lineData = LineData(dataSet)


        lineChart.data = lineData

        lineChart.data.dataSets.forEach { dataSet ->
            if (dataSet is LineDataSet) {
                dataSet.apply {
                    setDrawHighlightIndicators(false)
                    highLightColor = Color.BLACK
                    highlightLineWidth = 1f
                    enableDashedHighlightLine(6f, 6f, 6f)
                    setDrawValues(false)
                    setDrawCircles(false)
                    lineWidth = 2f
                    valueTextSize = 15f
                    circleRadius = 4f
                }
            }
        }

        lineChart.invalidate()



        lineChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onNothingSelected() {
                binding.lineChart2.highlightValue(null)
                lineChart.setDrawMarkers(false)
            }

            override fun onValueSelected(e: Entry, h: Highlight) {

                lineChart.data.dataSets.forEach { dataSet ->
                    if (dataSet is LineDataSet) {
                        dataSet.apply {
                            setDrawVerticalHighlightIndicator(true)
                        }
                    }
                }
                binding.lineChart2.marker =
                    CustomMarkerView(requireContext(), R.layout.custom_marker_view, lineChart)

                lineChart.setDrawMarkers(true)
                Log.d(
                    "values",
                    " ".plus(e.x.toString()).plus(" ").plus(h.x.toString()).plus(e.data)
                        .plus(" ${h.drawX}").plus(h.dataSetIndex)
                        .plus(" ${h.xPx}")
                )

                when (dataSet[h.dataSetIndex].label) {
                    "USD" -> {
                        usdDataSet.setDrawCircles(true)
                        eurDataSet.setDrawCircles(false)
                        rubDataSet.setDrawCircles(false)
                    }
                    "EUR" -> {
                        usdDataSet.setDrawCircles(false)
                        eurDataSet.setDrawCircles(true)
                        rubDataSet.setDrawCircles(false)
                    }
                    "RUB" -> {
                        eurDataSet.setDrawCircles(false)
                        rubDataSet.setDrawCircles(true)
                        usdDataSet.setDrawCircles(false)
                    }
                }
            }
        })
    }

    override fun check() {
        lineChart.marker = null
        lineChart.data.dataSets.forEach { dataSet ->
            if (dataSet is LineDataSet) {
                dataSet.apply {
                    setDrawHighlightIndicators(false)
                    setDrawCircles(false)
                }
            }
        }
        lineChart.data.dataSets.find { it.label.toString() == "USD" }!!.isVisible =
            binding.checkBoxUSD.checked
        lineChart.data.dataSets.find { it.label.toString() == "EUR" }!!.isVisible =
            binding.checkBoxEUR.checked
        lineChart.data.dataSets.find { it.label.toString() == "RUB" }!!.isVisible =
            binding.checkBoxRUB.checked
        lineChart.invalidate()
        changeRange()
    }

    private fun changeRange() {

        val minList = mutableListOf<Float>()
        val maxList = mutableListOf<Float>()

        val lines = lineChart.data.dataSets.filter { it.isVisible }

        lineChart.data.dataSets.filter { it.isVisible }.forEach { iLineDataSet ->
            minList.add(iLineDataSet.yMin)
            maxList.add(iLineDataSet.yMax)
        }
        val maxF =  maxList.maxOrNull() ?: 0f
        val minF =  minList.maxOrNull() ?: 0f

        if (lines.isNotEmpty()) {
            lineChart.axisLeft.axisMinimum = if (minF >= 1f) minF - 1f else minF
            lineChart.axisLeft.axisMaximum = maxF + 1f
        }

        Log.d(" log min2", " ".plus(minList.minOrNull()))
        Log.d("log max2", " ".plus(maxList.maxOrNull() ))
    }
}