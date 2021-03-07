/*
 * Copyright 2021 sakibeko
 */
package com.sakibeko.sortvisual.viewmodel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sakibeko.sortvisual.R
import com.sakibeko.sortvisual.algorithm.BubbleSort
import com.sakibeko.sortvisual.algorithm.ISort
import com.sakibeko.sortvisual.algorithm.QuickSort
import com.sakibeko.sortvisual.algorithm.SelectionSort
import kotlin.random.Random

/**
 * ViewModel:ソートアルゴリズム可視化画面
 */
class VisualViewModel(targetSize: Int, algorithmId: Int) : ViewModel() {

    /**
     * 自動ソートフラグ.
     * true:ソート結果を自動再生する  false:ソート結果を手動再生する
     */
    val mIsAutoProgress = MutableLiveData(false)

    /** ソート対象のデータ */
    val mTargetData = MutableLiveData<List<Int>>()

    /** カーソル:前方 */
    val mFrontCursor = MutableLiveData<Int>()

    /** カーソル:後方 */
    val mBackCursor = MutableLiveData<Int>()

    /** カーソル:追加 */
    val mAdditionalCursor = MutableLiveData<Int>()

    /** ソートアルゴリズム */
    private val mSortAlgorithm: ISort

    /**
     * ソート結果の前進フラグ.
     * true:ソート結果を進める  false:ソート結果を戻す.
     */
    private var mIsProgress = true

    /** 自動ソート用のハンドラ */
    private val mHandler = Handler(Looper.myLooper())

    /** 自動ソート用のRunnable */
    private val mRunnable = object : Runnable {
        override fun run() {
            if (!showStep()) {
                return
            }

            if (mIsAutoProgress.value!!) {
                mHandler.postDelayed(this, 100)
            }
        }
    }

    init {
        // ソート対象のデータを生成する
        val targetData = provideTargetData(targetSize)
        mTargetData.value = targetData
        // ソートアルゴリズムを生成する
        mSortAlgorithm = provideSortAlgorithm(algorithmId, targetData)
        // ソートする
        mSortAlgorithm.sort()
    }

    /**
     * 自動でソートする.
     */
    fun auto(isAuto: Boolean) {
        mIsAutoProgress.value = isAuto
        if (isAuto) {
            mIsProgress = true
            mHandler.post(mRunnable)
        } else {
            mHandler.removeCallbacks(mRunnable)
        }
    }

    /**
     * ソート結果を1step進める.
     */
    fun next() {
        mIsProgress = true
        showStep()
    }

    /**
     * ソート結果を1step戻す.
     */
    fun previous() {
        mIsProgress = false
        showStep()
    }

    /**
     * ソート結果を1step表示する.
     * @return 表示内容に変更があればtrueを返す
     */
    private fun showStep(): Boolean {
        if (mIsProgress) {
            if (mSortAlgorithm.isCompleted()) {
                return false
            }
            mSortAlgorithm.next()
        } else {
            if (mSortAlgorithm.isFirst()) {
                return false
            }
            mSortAlgorithm.previous()
        }

        mFrontCursor.value = mSortAlgorithm.mFrontIndex
        mBackCursor.value = mSortAlgorithm.mBackIndex
        mAdditionalCursor.value = mSortAlgorithm.mAdditionalIndex

        return true
    }

}

/**
 * ソート対象のデータを生成する.
 * @param targetSize データの件数
 * @return ソート対象のデータ
 */
private fun provideTargetData(targetSize: Int): MutableList<Int> {
    val list = mutableListOf<Int>()
    for (i in 1..targetSize) {
        list.add(Random.nextInt(targetSize) + 1)
    }
    return list
}

/**
 * ソートアルゴリズムを生成する.
 * @param id ソートアルゴリズムのid
 * @param targetData ソート対象のデータ
 * @return ソートアルゴリズム
 */
private fun provideSortAlgorithm(id: Int, targetData: MutableList<Int>): ISort {
    return when (id) {
        R.string.sort_selection -> SelectionSort(targetData)
        R.string.sort_bubble -> BubbleSort(targetData)
        R.string.sort_quick -> QuickSort(targetData)
        else -> throw IllegalArgumentException("Unsupported algorithm.")
    }
}