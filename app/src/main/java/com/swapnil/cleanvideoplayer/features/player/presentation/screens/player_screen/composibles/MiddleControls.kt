package com.swapnil.cleanvideoplayer.features.player.presentation.screens.player_screen.composibles

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.swapnil.cleanvideoplayer.R
import com.swapnil.cleanvideoplayer.features.player.presentation.screens.player_screen.events.PlayerEvent
import com.swapnil.cleanvideoplayer.features.player.presentation.screens.player_screen.states.PlayerState
import com.swapnil.cleanvideoplayer.ui.theme.CleanVideoPlayerTheme

@Composable
fun MiddleControls(
    playerState: PlayerState,
    modifier: Modifier = Modifier, onAction: (PlayerEvent) -> Unit
) {

    val isPlaying = playerState.isVideoPlaying
    val playPauseIcon = if(isPlaying) R.drawable.ic_pause else R.drawable.ic_play

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
    ) {

        VideoControlButton(
            icon = R.drawable.ic_backword,
            onClick = {}
        )
        VideoControlButton(
            icon = playPauseIcon,
            onClick = {
                onAction(PlayerEvent.PlayPauseVideo)
            },
        )
        VideoControlButton(
            icon = R.drawable.ic_forword,
            onClick = {}
        )

    }

}

@Composable
fun VideoControlButton(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    IconButton(onClick = onClick) {
        Icon(
            imageVector = icon,
            contentDescription = "back",
            modifier = modifier
        )
    }
}

@Composable
fun VideoControlButton(
    icon: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "back",
            tint = Color.White,
            modifier = modifier
        )
    }
}

@Preview
@Composable
private fun MiddleControlsPreview() {
    CleanVideoPlayerTheme {
        MiddleControls(
            playerState = PlayerState(),
            onAction = {})
    }
}