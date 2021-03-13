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
     * ソートする.
     */
    override fun sort(targetData: MutableList<Int>) {
        quickSort(targetData, 0, targetData.size)
    }

    /**
     * 比較位置(追加)を取得する.
     */
    override fun getAdditionalPosition(): Int {
        return if (canPlay()) {
            mPivotHistories[mPlaybackPosition]
        } else {
            mPivotHistories[mSortHistories.size - 1]
        }
    }

    /**
     * クイックソートでソートする.
     */
    private fun quickSort(targetData: MutableList<Int>, frontIndex: Int, backIndex: Int) {
        // 比較位置が重複している場合、何もしない
        if (backIndex - frontIndex <= 1) {
            return
        }

        // 1.基準値を決定する
        // (今回は真ん中にある値を基準値に選んでいる.選び方は色々ある.)
        val pivotIndex = (frontIndex + backIndex) / 2
        val pivot = targetData[pivotIndex]
        val newPivotIndex = backIndex - 1
        // 基準値を右端に移動する
        swapAndSavePivot(targetData, pivotIndex, newPivotIndex, newPivotIndex)

        // 2.「基準値未満」は前方に、「基準値以上」は後方に移動する.
        var i = frontIndex
        for (j in frontIndex until backIndex) {
            if (targetData[j] < pivot) {
                // 「基準値未満」であれば入れ替える(「基準値未満」は前方に移動する).
                swapAndSavePivot(targetData, i++, j, newPivotIndex)
            } else {
                // ソートアルゴリズムを可視化するために比較履歴を保存する(ソートアルゴリズムとは無関係である).
                saveComparisonHistory(i, j, newPivotIndex)
            }
        }

        // 3.基準値と「基準値未満」の末尾の位置を入れ替える.
        swapAndSavePivot(targetData, i, newPivotIndex, newPivotIndex)

        // 4.「基準値未満」に対して再帰的に処理を行う.
        quickSort(targetData, frontIndex, i)
        // 5.「基準値以上」に対して再帰的に処理を行う.
        quickSort(targetData, i + 1, backIndex)
    }

    /**
     * 入れ替え処理と同時に基準値を保存する.
     */
    private fun swapAndSavePivot(
        targetData: MutableList<Int>, frontIndex: Int, backIndex: Int, pivotIndex: Int
    ) {
        swap(targetData, frontIndex, backIndex)
        mPivotHistories.add(pivotIndex)
    }

    /**
     * 比較履歴を保存する.
     */
    private fun saveComparisonHistory(frontIndex: Int, backIndex: Int, pivotIndex: Int) {
        mSortHistories.add(emptyMap())
        mComparisonHistories.add(Pair(frontIndex, backIndex))
        mPivotHistories.add(pivotIndex)
    }

}