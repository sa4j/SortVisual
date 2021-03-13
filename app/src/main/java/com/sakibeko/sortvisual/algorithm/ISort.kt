/*
 * Copyright 2021 sakibeko
 */
package com.sakibeko.sortvisual.algorithm

/**
 * ISortはソート処理の過程を記録してstep単位で再生する機能を提供する.
 */
abstract class ISort(private val mTargetData: MutableList<Int>) {

    /** 現在の再生位置 */
    protected var mPlaybackPosition = 0
        private set

    /** 変更履歴 */
    protected val mSortHistories = mutableListOf<Map<Int, Pair<Int, Int>>>()

    /** 比較履歴 */
    protected val mComparisonHistories = mutableListOf<Pair<Int, Int>>()


    /**
     * ソートする(ソートアルゴリズムを実装する).
     */
    protected abstract fun sort(targetData: MutableList<Int>)

    /**
     * 初期化する.
     */
    fun setup() = sort(mTargetData.toMutableList())

    /**
     * 順再生が可能か否かを返す.
     * @return 順再生が可能であればtrueを返す.
     */
    fun canPlay(): Boolean {
        return mPlaybackPosition < mSortHistories.size
    }

    /**
     * 逆再生が可能か否かを返す.
     * @return 逆再生が可能であればtrueを返す.
     */
    fun canBack(): Boolean {
        return mPlaybackPosition > 0
    }

    /**
     * 順再生する.
     */
    fun play() {
        mSortHistories[mPlaybackPosition++].forEach { (k, v) ->
            mTargetData[k] = v.second
        }
    }

    /**
     * 逆再生する.
     */
    fun back() {
        mSortHistories[--mPlaybackPosition].forEach { (k, v) ->
            mTargetData[k] = v.first
        }
    }

    /**
     * 比較位置(前方)を取得する.
     */
    fun getFrontPosition() = getCurrentPositions().first

    /**
     * 比較位置(後方)を取得する.
     */
    fun getBackPosition() = getCurrentPositions().second

    /**
     * 比較位置(追加)を取得する.
     */
    open fun getAdditionalPosition() = -1

    /**
     * データを入れ替える.
     * 同時に変更履歴を保存する.
     */
    protected fun swap(targetData: MutableList<Int>, frontIndex: Int, backIndex: Int) {
        mComparisonHistories.add(Pair(frontIndex, backIndex))
        val beforeData = targetData.toList()
        swapWithoutHistory(targetData, frontIndex, backIndex)
        saveSortHistory(beforeData, targetData)
    }

    /**
     * 必要であればデータを入れ替える.
     * 同時に変更履歴を保存する.
     */
    protected fun swapIfNeeded(
        targetData: MutableList<Int>, frontIndex: Int, backIndex: Int
    ): Boolean {
        return if (targetData[frontIndex] > targetData[backIndex]) {
            swap(targetData, frontIndex, backIndex)
            true
        } else {
            mComparisonHistories.add(Pair(frontIndex, backIndex))
            mSortHistories.add(emptyMap())
            false
        }
    }

    /**
     * 変更履歴を保存する.
     */
    private fun saveSortHistory(beforeData: List<Int>, targetData: List<Int>) {
        val sortHistory = mutableMapOf<Int, Pair<Int, Int>>()
        mSortHistories.add(sortHistory)
        for (i in beforeData.indices) {
            if (beforeData[i] != targetData[i]) {
                sortHistory[i] = Pair(beforeData[i], targetData[i])
            }
        }
    }

    /**
     * 現在の比較履歴を取得する.
     */
    private fun getCurrentPositions(): Pair<Int, Int> {
        return if (canPlay()) {
            mComparisonHistories[mPlaybackPosition]
        } else {
            mComparisonHistories[mSortHistories.size - 1]
        }
    }

}

/**
 * データを入れ替える.
 * 同時に変更履歴を保存しない.
 */
private fun swapWithoutHistory(targetData: MutableList<Int>, frontIndex: Int, backIndex: Int) {
    val tmp = targetData[frontIndex]
    targetData[frontIndex] = targetData[backIndex]
    targetData[backIndex] = tmp
}
