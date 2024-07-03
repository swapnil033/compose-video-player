package com.swapnil.cleanvideoplayer.features.player.presentation.screens.player_screen.composibles

import androidx.compose.foundation.background
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.swapnil.cleanvideoplayer.ui.theme.CleanVideoPlayerTheme

@Composable
fun TimeStampsDrawer(
    drawerState: DrawerState,
    modifier: Modifier = Modifier,
) {
    ModalDrawerSheet(
        modifier = modifier.background(Color.Black.copy(alpha = 0.4f))
    ) {
        Text(text = "Time Stamps")
    }
}

@Preview
@Composable
private fun TimeStampsDrawerPreview() {
    CleanVideoPlayerTheme {
        TimeStampsDrawer(rememberDrawerState(initialValue = DrawerValue.Open))
    }
}