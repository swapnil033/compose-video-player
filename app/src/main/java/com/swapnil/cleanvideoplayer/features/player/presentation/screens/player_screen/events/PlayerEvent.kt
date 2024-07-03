package com.swapnil.cleanvideoplayer.features.player.presentation.screens.player_screen.events

import android.net.Uri

sealed class PlayerEvent {

    data class OnAddVideo(val uri: Uri) : PlayerEvent()
    data class OnSetVideo(val uri: Uri) : PlayerEvent()
    data object PlayPauseVideo: PlayerEvent()
    data object OnRotationChange: PlayerEvent()

}