/*
 * Copyright 2021 sakibeko
 */
package com.sakibeko.sortvisual.algorithm

/**
 * ソートアルゴリズム:選択ソート
 */
class SelectionSort(targetData: MutableList<Int>) : ISort(targetData) {
    /**
     * ソートする(ソートアルゴリズムを実装する).
     */
    override fun sort(targetData: MutableList<Int>) {
        for (i in 0 until targetData.size - 1) {
            for (j in i + 1 until targetData.size) {
                swapIfNeeded(targetData, i, j)
            }
        }
    }
}