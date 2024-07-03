package com.swapnil.cleanvideoplayer.features.player.presentation.screens.player_screen.states

import android.content.pm.ActivityInfo

data class PlayerState(
    var isVideoPlaying: Boolean = false,
    var screenOrientation: Int = ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT

)
