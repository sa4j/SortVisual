/*
 * Copyright 2021 sakibeko
 */
package com.sakibeko.sortvisual.algorithm

abstract class ISort(private val mTargetData: MutableList<Int>) {

    /** インデックス:前方 */
    var mFrontIndex: Int = -1
        protected set

    /** インデックス:後方 */
    var mBackIndex: Int = -1
        protected set

    /** インデックス:追加 */
    var mAdditionalIndex: Int = -1
        protected set

    /** step番号 */
    var mStepNo = 0
        protected set

    /** 比較履歴 */
    protected val mCompareHistories = mutableListOf<Pair<Int, Int>>()

    /** 入れ替え履歴 */
    private val mSwapHistories = mutableListOf<Pair<Int, Int>>()


    /**
     * ソートする.
     */
    fun sort() = sort(mTargetData.toMutableList())

    /**
     * 初回判定.
     * @return 初回であればtrueを返す.
     */
    fun isFirst(): Boolean {
        return mStepNo == 0
    }

    /**
     * 完了判定.
     * @return ソートが完了したらtrueを返す.
     */
    fun isCompleted(): Boolean {
        return mStepNo == mCompareHistories.size
    }

    /**
     * ソートを1step進める.
     */
    open fun next() {
        step(mCompareHistories[mStepNo++])
    }

    /**
     * ソートを1step戻す.
     */
    open fun previous() {
        step(mCompareHistories[--mStepNo])
    }

    /**
     * ソートする(ソートアルゴリズムを実装する).
     */
    protected abstract fun sort(targetData: MutableList<Int>)

    /**
     * 入れ替える.
     * 同時に履歴を保存する.
     */
    protected fun swap(targetData: MutableList<Int>, frontIndex: Int, backIndex: Int) {
        mCompareHistories.add(Pair(frontIndex, backIndex))
        mSwapHistories.add(mCompareHistories[mCompareHistories.size - 1])
        swapWithoutHistory(targetData, frontIndex, backIndex)
    }

    /**
     * 入れ替えが必要であれば入れ替える.
     * 同時に履歴を保存する.
     */
    protected fun swapIfNeeded(
        targetData: MutableList<Int>, frontIndex: Int, backIndex: Int
    ): Boolean {
        mCompareHistories.add(Pair(frontIndex, backIndex))
        if (targetData[frontIndex] <= targetData[backIndex]) {
            return false
        }
        mSwapHistories.add(mCompareHistories[mCompareHistories.size - 1])
        swapWithoutHistory(targetData, frontIndex, backIndex)
        return true
    }

    /**
     * ソートを1step進める/戻す.
     */
    private fun step(compareTarget: Pair<Int, Int>) {
        mFrontIndex = compareTarget.first
        mBackIndex = compareTarget.second
        if (containsInSwapHistories(compareTarget)) {
            swapWithoutHistory(mTargetData, mFrontIndex, mBackIndex)
        }
    }

    /**
     * 入れ替え履歴に存在すればtrueを返す.
     */
    private fun containsInSwapHistories(compareTarget: Pair<Int, Int>): Boolean {
        mSwapHistories.forEach {
            if (it === compareTarget) {
                return true
            }
        }
        return false
    }

}

/**
 * 入れ替える.
 */
private fun swapWithoutHistory(targetData: MutableList<Int>, frontIndex: Int, backIndex: Int) {
    val tmp = targetData[frontIndex]
    targetData[frontIndex] = targetData[backIndex]
    targetData[backIndex] = tmp
}
