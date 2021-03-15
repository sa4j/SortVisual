/**
 * ソートする.
 */
package com.sakibeko.sortvisual.algorithm

class MergeSort(targetData: MutableList<Int>) : ISort(targetData) {

    /**
     * ソートする.
     * @param targetData ソート対象のデータ
     */
    override fun sort(targetData: MutableList<Int>) {
        mergeSort(targetData, 0, targetData.size)
    }

    /**
     * マージソートでマージする.
     * @param targetData ソート対象のデータ
     * @param firstIndex 比較位置(開始)
     * @param lastIndex 比較位置(開始)
     */
    private fun mergeSort(targetData: MutableList<Int>, firstIndex: Int, lastIndex: Int) {
        if (lastIndex - firstIndex == 1) {
            return
        }

        val mid = firstIndex + (lastIndex - firstIndex) / 2

        // 左半分をソート（first - mid）
        mergeSort(targetData, firstIndex, mid)
        // 右半分をソート（mid - last）
        mergeSort(targetData, mid, lastIndex)

        // 左と右のデータをマージする
        val buf = mutableListOf<Int>()
        for (i in firstIndex until mid) {
            buf.add(targetData[i])
        }
        for (i in lastIndex - 1 downTo mid) {
            buf.add(targetData[i])
        }

        // マージする
        var leftIndex = 0
        var rightIndex = buf.size - 1
        for (i in firstIndex until lastIndex) {
            if (buf[leftIndex] <= buf[rightIndex]) {
                saveComparisonHistory(i, leftIndex)
                replaceValue(targetData, i, buf[leftIndex++])
            } else {
                saveComparisonHistory(i, rightIndex)
                replaceValue(targetData, i, buf[rightIndex--])
            }
        }
    }

    /**
     * 値を変更する.
     */
    private fun replaceValue(targetData: MutableList<Int>, targetIndex: Int, value: Int) {
        // 変更履歴を保存する
        val sortHistory = mutableMapOf<Int, Pair<Int, Int>>()
        sortHistory[targetIndex] = Pair(targetData[targetIndex], value)
        mSortHistories.add(sortHistory)
        // 値を変更する
        targetData[targetIndex] = value
    }

}