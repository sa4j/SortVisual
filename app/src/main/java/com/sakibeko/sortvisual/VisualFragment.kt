/*
 * Copyright 2021 sakibeko
 */
package com.sakibeko.sortvisual

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.sakibeko.sortvisual.databinding.FragmentVisualBinding
import com.sakibeko.sortvisual.view.SortResultView
import com.sakibeko.sortvisual.viewmodel.ARGS_NAME_ALGORITHM_ID
import com.sakibeko.sortvisual.viewmodel.ARGS_NAME_TARGET_SIZE
import com.sakibeko.sortvisual.viewmodel.VisualViewModel
import com.sakibeko.sortvisual.viewmodel.getViewModelFactory

/**
 * Fragment:ソートアルゴリズム可視化画面
 */
class VisualFragment : Fragment() {

    /** データバインディング用オブジェクト */
    private lateinit var mViewDataBinding: FragmentVisualBinding

    /** 画面遷移パラメータ */
    private val mArgs: VisualFragmentArgs by navArgs()

    private val mViewModel by viewModels<VisualViewModel> {
        val defaultArgs = Bundle()
        defaultArgs.putInt(ARGS_NAME_TARGET_SIZE, SORT_TARGET_SIZE)
        defaultArgs.putInt(ARGS_NAME_ALGORITHM_ID, mArgs.algorithmId)
        getViewModelFactory(defaultArgs)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // データバインディングの各種設定
        mViewDataBinding = FragmentVisualBinding.inflate(inflater, container, false).apply {
            viewModel = mViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return mViewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserves(view)
        setViewEvents(view)
    }

    /**
     * 画面の監視イベントを設定する.
     */
    private fun setupObserves(view: View) {
        val sortResultView = view.findViewById<SortResultView>(R.id.sort_result)

        mViewModel.mTargetData.observe(viewLifecycleOwner, {
            sortResultView.mTargetData = it
            sortResultView.invalidate()
        })
        mViewModel.mFrontPosition.observe(viewLifecycleOwner, {
            sortResultView.mFrontCursor = it
            sortResultView.invalidate()
        })
        mViewModel.mBackPosition.observe(viewLifecycleOwner, {
            sortResultView.mBackCursor = it
            sortResultView.invalidate()
        })
        mViewModel.mAdditionalPosition.observe(viewLifecycleOwner, {
            sortResultView.mAdditionalCursor = it
            sortResultView.invalidate()
        })
    }

    /**
     * 画面イベントを設定する.
     */
    private fun setViewEvents(view: View) {
        view.findViewById<Button>(R.id.button_previous).setOnClickListener {
            mViewModel.back()
        }
        view.findViewById<Button>(R.id.button_next).setOnClickListener {
            mViewModel.play()
        }
        view.findViewById<ToggleButton>(R.id.button_sort_auto)
            .setOnCheckedChangeListener { _, isChecked ->
                mViewModel.auto(isChecked)
            }
    }

}

/** ソート対象のデータの件数 */
private const val SORT_TARGET_SIZE = 20
