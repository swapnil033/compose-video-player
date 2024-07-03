package com.swapnil.cleanvideoplayer.features.player.domain.entity

import android.net.Uri
import androidx.media3.common.MediaItem

data class VideoItemEntity(
    val contentUri : Uri,
    val mediaItem: MediaItem,
    val name: String
)
