package com.example.mpandroidchart.extenssions

fun findMinMax(values: List<Float>): Pair<Float, Float> {
    var max = 0f
    var min = 10f
    values.forEach {
        if (it > max) {
            max = it
        }
        if (it < min) {
            min = it
        }
    }
    return Pair(min, max)
}