package com.example.muse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.muse.ui.theme.MuseTheme

import com.example.muse.ui.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MuseTheme(darkTheme = false) {
                AppNavigation()
            }
        }
    }
}


@Preview
//@Preview(name = "Dark Theme", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun MessagePreview() {
    MuseTheme {
        AppNavigation()
        }
    }

