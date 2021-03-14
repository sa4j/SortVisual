/*
 * Copyright 2021 sakibeko
 */
package com.sakibeko.sortvisual.viewmodel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sakibeko.sortvisual.R
import com.sakibeko.sortvisual.algorithm.*
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

    /** 比較位置(前方) */
    val mFrontPosition = MutableLiveData<Int>()

    /** 比較位置(後方) */
    val mBackPosition = MutableLiveData<Int>()

    /** 比較位置(追加) */
    val mAdditionalPosition = MutableLiveData<Int>()

    /** ソートアルゴリズム */
    private val mSortAlgorithm: ISort

    /**
     * ソートの再生方向フラグ.
     * true:順再生する  false:逆再生する
     */
    private var mIsPlay = true

    /** 自動ソート用のハンドラ */
    private val mHandler = Handler(Looper.myLooper())

    /** 自動ソート用のRunnable */
    private val mRunnable = object : Runnable {
        override fun run() {
            if (!playback()) {
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
        mSortAlgorithm.setup()
        // 現在の比較位置を設定する
        setCurrentPosition()
    }


    /**
     * 自動でソートする.
     */
    fun auto(isAuto: Boolean) {
        mIsAutoProgress.value = isAuto
        if (isAuto) {
            mIsPlay = true
            mHandler.post(mRunnable)
        } else {
            mHandler.removeCallbacks(mRunnable)
        }
    }

    /**
     * ソート結果を1step再生する.
     */
    fun play() {
        mIsPlay = true
        playback()
    }

    /**
     * ソート結果を1step逆再生する.
     */
    fun back() {
        mIsPlay = false
        playback()
    }

    /**
     * ソート結果を1step再生・逆再生する.
     * @return 表示内容に変更があればtrueを返す
     */
    private fun playback(): Boolean {
        if (mIsPlay) {
            if (!mSortAlgorithm.canPlay()) {
                return false
            }
            mSortAlgorithm.play()
        } else {
            if (!mSortAlgorithm.canBack()) {
                return false
            }
            mSortAlgorithm.back()
        }

        // 現在の比較位置を設定する
        setCurrentPosition()

        return true
    }

    /**
     * 現在の比較位置を設定する.
     */
    private fun setCurrentPosition() {
        mFrontPosition.value = mSortAlgorithm.getFrontPosition()
        mBackPosition.value = mSortAlgorithm.getBackPosition()
        mAdditionalPosition.value = mSortAlgorithm.getAdditionalPosition()
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
        R.string.sort_insertion -> InsertionSort(targetData)
        else -> throw IllegalArgumentException("Unsupported algorithm.")
    }
}