package com.swapnil.cleanvideoplayer.features.player.presentation.screens.player_screen.composibles

import android.content.pm.ActivityInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.swapnil.cleanvideoplayer.ui.theme.CleanVideoPlayerTheme

@Composable
fun VidePlayer(
    exoPlayer: Player,
    modifier: Modifier = Modifier,
) {
    AndroidView(
        factory = { context ->
            PlayerView(context).apply {
                player = exoPlayer
                useController = false
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
                keepScreenOn = true
            }
        },
        modifier = modifier,
    )
}

@Preview
@Composable
private fun VidePlayerPreview() {
    CleanVideoPlayerTheme {
        val context = LocalContext.current
        val player = ExoPlayer.Builder(context).build()
        VidePlayer(
            exoPlayer = player
        )
    }
}