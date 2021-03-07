/*
 * Copyright 2021 sakibeko
 */
package com.sakibeko.sortvisual

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity() {

    /** ナビゲーションメニューの設定情報 */
    private val mAppBarConfiguration = AppBarConfiguration.Builder(R.id.dest_top).build()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))
        setupToolbar()
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp(mAppBarConfiguration) ||
                super.onSupportNavigateUp()
    }

    /**
     * ツールバーの初期化
     */
    private fun setupToolbar() {
        // アクションバーにナビゲーションサポートを追加する
        val navController = findNavController(R.id.nav_host_fragment)
        setupActionBarWithNavController(navController, mAppBarConfiguration)
    }

}