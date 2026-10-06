package com.example.muse.core.navigation

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.muse.R

@Preview
@Composable
fun PreviewBottomNavigation() {
    Scaffold(bottomBar = { BottomNavigation() }) { }
}

@Composable
fun BottomNavigation(modifier: Modifier = Modifier) {
    NavigationBar(
        modifier = modifier
    ) {
        NavigationBarItem(
            icon = {
                Image(
                    painter = painterResource(
                        id = R.drawable.home
                    ),
                    contentDescription = "Home"
                )
            },
            label = {
                Text(
                    text = stringResource(R.string.bottom_navigation_home)
                )
            },
            selected = true,
            onClick = {}
        )
        NavigationBarItem(
        icon = {
            Image(
                painter = painterResource(
                    id = R.drawable.home
                ),
                contentDescription = "Home"
            )
        },
        label = {
            Text(
                text = stringResource(R.string.bottom_navigation_home)
            )
        },
        selected = true,
        onClick = {}
        )
    }
}