/*
 * Copyright 2021 sakibeko
 */
package com.sakibeko.sortvisual

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

/**
 * Fragment:メニュー画面
 */
class MenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListView(view)
    }

    /**
     * ListViewを設定する.
     */
    private fun setupListView(view: View) {
        val algorithmList = provideSortAlgorithmList()
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, algorithmList)
        val listView = view.findViewById<ListView>(R.id.list_algorithm)
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            val algorithmId = ALGORITHM_LIST[position]
            val action =
                MenuFragmentDirections.actionMenuToVisual(getString(algorithmId), algorithmId)
            findNavController().navigate(action)
        }
    }

    /**
     * ソートアルゴリズムの一覧を作成する.
     */
    private fun provideSortAlgorithmList(): MutableList<String> {
        val algorithmList = mutableListOf<String>()
        ALGORITHM_LIST.forEach {
            algorithmList.add(requireContext().getString(it))
        }
        return algorithmList
    }

}

private val ALGORITHM_LIST =
    listOf(R.string.sort_selection, R.string.sort_bubble, R.string.sort_quick)
