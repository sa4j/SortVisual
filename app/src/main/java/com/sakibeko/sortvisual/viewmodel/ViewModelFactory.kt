/*
 * Copyright 2021 sakibeko
 */
package com.sakibeko.sortvisual.viewmodel

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner

/**
 * ViewModelFactoryを取得する.
 */
fun Fragment.getViewModelFactory(defaultArgs: Bundle? = null): ViewModelFactory {
    return ViewModelFactory(this, defaultArgs)
}

/**
 * ViewModelを生成する.
 */
class ViewModelFactory constructor(
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    override fun <T : ViewModel> create(
        key: String, modelClass: Class<T>, handle: SavedStateHandle
    ) = with(modelClass) {
        when {
            isAssignableFrom(VisualViewModel::class.java) -> {
                val targetSize = handle.get<Int>(ARGS_NAME_TARGET_SIZE) ?: 0
                val algorithmId = handle.get<Int>(ARGS_NAME_ALGORITHM_ID) ?: 0
                VisualViewModel(targetSize, algorithmId)
            }
            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}

const val ARGS_NAME_TARGET_SIZE = "target_size"
const val ARGS_NAME_ALGORITHM_ID = "algorithm_id"
