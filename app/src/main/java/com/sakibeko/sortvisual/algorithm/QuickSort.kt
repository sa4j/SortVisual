/*
 * Copyright 2021 sakibeko
 */
package com.sakibeko.sortvisual.algorithm

/**
 * ソートアルゴリズム:クイックソート
 */
class QuickSort(targetData: MutableList<Int>) : ISort(targetData) {

    /** 基準値の履歴 */
    private val mPivotHistories = mutableListOf<Int>()

    /**
     * ソートを1step進める.
     */
    override fun next() {
        super.next()
        mAdditionalIndex = mPivotHistories[mStepNo - 1]
    }

    /**
     * ソートを1step戻す.
     */
    override fun previous() {
        super.previous()
        mAdditionalIndex = mPivotHistories[mStepNo]
    }

    /**
     * ソートする(ソートアルゴリズムを実装する).
     */
    override fun sort(targetData: MutableList<Int>) {
        quickSort(targetData, 0, targetData.size)
    }

    /**
     * クイックソートでソートする.
     */
    private fun quickSort(targetData: MutableList<Int>, leftIndex: Int, rightIndex: Int) {
        // 比較位置が重複している場合、何もしない
        if (rightIndex - leftIndex <= 1) {
            return
        }

        // 1.基準値を決定する
        // (今回は真ん中にある値を基準値に選んでいる.選び方は色々ある.)
        val pivotIndex = (leftIndex + rightIndex) / 2
        val pivot = targetData[pivotIndex]
        val newPivotIndex = rightIndex - 1
        // 基準値を右端に移動する
        swapAndSavePivot(targetData, pivotIndex, newPivotIndex, newPivotIndex)

        // 2.「基準値未満」は前方に、「基準値以上」は後方に移動する.
        var i = leftIndex
        for (j in leftIndex until rightIndex) {
            // ソートアルゴリズムを可視化するためにindexを保存する(ソートアルゴリズムとは無関係である).
            saveIndices(i, j, newPivotIndex)
            if (targetData[j] < pivot) {
                // 「基準値未満」であれば入れ替える(「基準値未満」は前方に移動する).
                swapAndSavePivot(targetData, i++, j, newPivotIndex)
            }
        }

        // 3.基準値と「基準値未満」の末尾の位置を入れ替える.
        swapAndSavePivot(targetData, i, newPivotIndex, newPivotIndex)

        // 4.「基準値未満」に対して再帰的に処理を行う.
        quickSort(targetData, leftIndex, i)
        // 5.「基準値以上」に対して再帰的に処理を行う.
        quickSort(targetData, i + 1, rightIndex)
    }

    /**
     * 入れ替え処理と同時に基準値を保存する.
     */
    private fun swapAndSavePivot(
        targetData: MutableList<Int>, leftIndex: Int, rightIndex: Int, pivotIndex: Int
    ) {
        swap(targetData, leftIndex, rightIndex)
        mPivotHistories.add(pivotIndex)
    }

    /**
     * インデックスを保存する.
     */
    private fun saveIndices(leftIndex: Int, rightIndex: Int, pivotIndex: Int) {
        mCompareHistories.add(Pair(leftIndex, rightIndex))
        mPivotHistories.add(pivotIndex)
    }

}