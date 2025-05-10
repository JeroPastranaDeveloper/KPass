package com.jero.kpass

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.jero.navigation.LocalComposeNavigator
import androidx.compose.runtime.CompositionLocalProvider
import com.jero.kpass.ui.KPassMain
import com.jero.navigation.AppComposeNavigator
import com.jero.navigation.KPassScreen
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val composeNavigator: AppComposeNavigator<KPassScreen> by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CompositionLocalProvider(
                LocalComposeNavigator provides composeNavigator
            ) {
                KPassMain(composeNavigator = composeNavigator)
            }
        }
    }
}