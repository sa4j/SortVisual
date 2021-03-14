/*
 * Copyright 2021 sakibeko
 */
package com.sakibeko.sortvisual.algorithm

/**
 * ソートアルゴリズム:挿入ソート
 */
class InsertionSort(targetData: MutableList<Int>) : ISort(targetData) {

    /**
     * ソートする(ソートアルゴリズムを実装する).
     */
    override fun sort(targetData: MutableList<Int>) {
        for (i in 1 until targetData.size) {
            val insertionValue = targetData[i]

            var j = i
            while (j > 0) {
                if (targetData[j - 1] > insertionValue) {
                    replaceValue(targetData, j, targetData[j - 1])
                    mComparisonHistories.add(Pair(j - 1, j))
                    --j
                } else {
                    break
                }
            }
            replaceValue(targetData, j, insertionValue)
            mComparisonHistories.add(Pair(-1, j))
        }
    }

    /**
     * 値を変更する.
     */
    private fun replaceValue(
        targetData: MutableList<Int>, targetIndex: Int, value: Int
    ) {
        // 変更履歴を保存する
        val sortHistory = mutableMapOf<Int, Pair<Int, Int>>()
        sortHistory[targetIndex] = Pair(targetData[targetIndex], value)
        mSortHistories.add(sortHistory)
        // 値を変更する
        targetData[targetIndex] = value
    }

}