package com.swapnil.cleanvideoplayer.features.player.presentation.screens.player_screen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.swapnil.cleanvideoplayer.features.player.presentation.screens.player_screen.composibles.MiddleControls
import com.swapnil.cleanvideoplayer.features.player.presentation.screens.player_screen.composibles.TitleAndDuration
import com.swapnil.cleanvideoplayer.features.player.presentation.screens.player_screen.composibles.VidePlayer
import com.swapnil.cleanvideoplayer.features.player.presentation.screens.player_screen.events.PlayerEvent
import com.swapnil.cleanvideoplayer.features.player.presentation.screens.player_screen.viewModels.PlayerViewModel
import com.swapnil.cleanvideoplayer.ui.theme.CleanVideoPlayerTheme

@Composable
fun PlayerScreenRoot(
    viewModel: PlayerViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onMenuClick: () -> Unit,
) {
    PlayerScreen(
        //state = viewModel.state,
        viewModel = viewModel,
        onAction = viewModel::onAction,
        onBackClick = onBackClick,
        onMenuClick = onMenuClick
    )
}

@Composable
private fun PlayerScreen(
    //state: ,
    viewModel: PlayerViewModel,
    onAction: (PlayerEvent) -> Unit,
    onBackClick: () -> Unit,
    onMenuClick: () -> Unit,
) {

    var showControls by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        VidePlayer(
            exoPlayer = viewModel.player,
            modifier = Modifier
                .fillMaxSize()
                .clickable(onClick = {
                    showControls = !showControls
                    Log.d("VIDEO-TAG", "PlayerScreen: $showControls")
                },
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ),
        )

        /*AnimatedVisibility(
            showControls, enter = fadeIn(), exit = fadeOut()
        ) {

        }*/

        AnimatedVisibility(
            visible = showControls, enter = fadeIn(), exit = fadeOut()
        ) {
            TitleAndDuration(
                videoTitle = "Video",
                viewModel = viewModel,
                onBackClick = onBackClick,
                onMenuClick = onMenuClick,
                onAction = viewModel::onAction
            )
        }

    }

}


@Preview
@Composable
private fun PlayerScreenPreview() {
    CleanVideoPlayerTheme {
        PlayerScreen(
            //state = (),
            viewModel = hiltViewModel(),
            onAction = {},
            onBackClick = {},
            onMenuClick = {}
        )
    }
}