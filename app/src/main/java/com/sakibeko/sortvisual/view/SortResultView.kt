/*
 * Copyright 2021 sakibeko
 */
package com.sakibeko.sortvisual.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * View:ソート結果
 */
class SortResultView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    /** ソート対象のデータ */
    var mTargetData: List<Int> = emptyList()

    /** カーソル:前方 */
    var mFrontCursor: Int = -1

    /** カーソル:後方 */
    var mBackCursor: Int = -1

    /** カーソル:追加 */
    var mAdditionalCursor: Int = -1

    /** 描画設定 */
    private val mPaint = Paint()


    /**
     * 描画処理.
     */
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (mTargetData.isEmpty()) {
            return
        }

        // 1個分の高さと幅を計算する
        val lineHeight = height / mTargetData.size
        val lineWidth = width / mTargetData.size

        for (i in mTargetData.indices) {
            // カーソルをハイライトする
            mPaint.color = when (i) {
                mFrontCursor -> {
                    Color.RED
                }
                mBackCursor -> {
                    Color.BLUE
                }
                mAdditionalCursor -> {
                    Color.GREEN
                }
                else -> {
                    Color.rgb(0xcc, 0xcc, 0xcc)
                }
            }

            val x1 = 0f
            val x2 = (lineWidth * mTargetData[i]).toFloat()
            val y1 = (lineHeight * i).toFloat()
            val y2 = y1 + lineHeight - lineHeight * 0.05f
            // 矩形を描画する
            canvas?.drawRect(x1, y1, x2, y2, mPaint)
        }
    }

}