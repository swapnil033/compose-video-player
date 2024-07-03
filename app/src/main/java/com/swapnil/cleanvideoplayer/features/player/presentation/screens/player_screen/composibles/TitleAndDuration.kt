package com.swapnil.cleanvideoplayer.features.player.presentation.screens.player_screen.composibles

import android.util.Log
import android.widget.ImageButton
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.DefaultTimeBar
import androidx.media3.ui.TimeBar
import com.swapnil.cleanvideoplayer.R
import com.swapnil.cleanvideoplayer.features.player.presentation.screens.player_screen.events.PlayerEvent
import com.swapnil.cleanvideoplayer.features.player.presentation.screens.player_screen.viewModels.PlayerViewModel
import com.swapnil.cleanvideoplayer.features.player.presentation.util.toHhMmSs
import com.swapnil.cleanvideoplayer.ui.theme.CleanVideoPlayerTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleAndDuration(
    videoTitle: String,
    modifier: Modifier = Modifier,
    viewModel: PlayerViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onMenuClick: () -> Unit,
    onAction: (PlayerEvent) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {

        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black.copy(alpha = 0.4f)),
            title = {
                Text(
                    text = videoTitle,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(12.dp),
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = "Back"
                    )
                }
            },
            actions = {
                IconButton(onClick = onMenuClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_menu),
                        contentDescription = "menu",
                    )
                }
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        BottomBar(player = viewModel.player, viewModel = viewModel, onAction = onAction)

    }
}

@Composable
fun BottomBar(
    player: ExoPlayer, modifier: Modifier = Modifier,
    onAction: (PlayerEvent) -> Unit,
    viewModel: PlayerViewModel = hiltViewModel()
) {

    var currentTime by remember {
        mutableLongStateOf(player.currentPosition)
    }
    var totalTime by remember {
        mutableLongStateOf(player.duration)
    }

    var isSeekInProgress by remember {
        mutableStateOf(false)
    }

    val timerCoroutineScope = rememberCoroutineScope()
    LaunchedEffect(key1 = Unit) {
        timerCoroutineScope.launch {
            while (true) {
                delay(500)
                if (!isSeekInProgress) {
                    currentTime = player.currentPosition
                    Log.d("PlayerScreen", "timer running $currentTime")
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Black.copy(alpha = 0.4f))
            .padding(horizontal = 5.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            VideoControlButton(
                icon = R.drawable.ic_screen_rotation,
                onClick = {
                    onAction(PlayerEvent.OnRotationChange)
                }
            )
        }

        CustomTimeBar(player = player,
            currentTime = currentTime,
            isSeekInProgress = { isSeekInProgress = it },
            onSeekBarMove = { currentTime = it })

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = currentTime.toHhMmSs())
            Spacer(modifier = Modifier.weight(1f))
            Text(text = totalTime.toHhMmSs())
        }

        MiddleControls(
            playerState = viewModel.state,
            modifier = Modifier.fillMaxWidth(),
            onAction = viewModel::onAction
        )

        Spacer(modifier = Modifier.height(20.dp))


    }

}

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun CustomTimeBar(
    player: ExoPlayer,
    currentTime: Long,
    modifier: Modifier = Modifier,
    isSeekInProgress: (Boolean) -> Unit,
    onSeekBarMove: (Long) -> Unit,
) {

    val primaryColor = MaterialTheme.colorScheme.primary

    AndroidView(factory = { context ->

        val listener = object : TimeBar.OnScrubListener {
            var previousScrubPosition = 0L
            override fun onScrubStart(timeBar: TimeBar, position: Long) {
                isSeekInProgress(true)
                previousScrubPosition = position
            }

            override fun onScrubMove(timeBar: TimeBar, position: Long) {
                onSeekBarMove(position)
            }

            override fun onScrubStop(timeBar: TimeBar, position: Long, canceled: Boolean) {
                if (canceled) player.seekTo(previousScrubPosition)
                else player.seekTo(position)
                isSeekInProgress(false)
            }

        }

        DefaultTimeBar(context).apply {
            setScrubberColor(primaryColor.toArgb())
            setPlayedColor(primaryColor.toArgb())
            setUnplayedColor(primaryColor.copy(0.3f).toArgb())
            addListener(listener)
            setDuration(player.duration)
            setPosition(currentTime)
        }

    }, update = {
        it.apply {
            setPosition(currentTime)
        }
    },

        modifier = modifier
    )

}

@Preview
@Composable
private fun TitleAndDurationPreview() {
    CleanVideoPlayerTheme {
        TitleAndDuration(
            videoTitle = "Video",
            onBackClick = {},
            onAction = {},
            onMenuClick = {}
        )
    }
}