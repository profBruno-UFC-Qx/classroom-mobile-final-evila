package com.example.homemates

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.homemates.navigation.AppNavigation
import com.example.homemates.ui.theme.HomeMatesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeMatesTheme {
                AppNavigation()
            }
        }
    }
}