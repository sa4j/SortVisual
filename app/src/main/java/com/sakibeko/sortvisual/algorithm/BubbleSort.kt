/*
 * Copyright 2021 sakibeko
 */
package com.sakibeko.sortvisual.algorithm

/**
 * ソートアルゴリズム:バブルソート
 */
class BubbleSort(targetData: MutableList<Int>) : ISort(targetData) {
    /**
     * ソートする(ソートアルゴリズムを実装する).
     */
    override fun sort(targetData: MutableList<Int>) {
        for (i in targetData.size - 1 downTo 1) {
            for (j in 0 until i) {
                swapIfNeeded(targetData, j, j + 1)
            }
        }
    }
}