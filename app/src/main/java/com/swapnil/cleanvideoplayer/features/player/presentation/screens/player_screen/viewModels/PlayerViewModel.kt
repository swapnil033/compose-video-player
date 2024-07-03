package com.swapnil.cleanvideoplayer.features.player.presentation.screens.player_screen.viewModels

import android.content.pm.ActivityInfo
import android.media.session.PlaybackState
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.swapnil.cleanvideoplayer.features.player.presentation.screens.player_screen.events.PlayerEvent
import com.swapnil.cleanvideoplayer.features.player.presentation.screens.player_screen.states.PlayerState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    val player: ExoPlayer,
): ViewModel(){

    private val videoUris = savedStateHandle.getStateFlow("videoUris", emptyList<Uri>())

    var state by mutableStateOf(PlayerState())
	private set

    private var _orientation = MutableLiveData(ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT)
    val orientation: LiveData<Int> = _orientation

    init {
        player.prepare()
        player.playWhenReady = true
    }

	fun onAction(event: PlayerEvent){
		when(event) {
            is PlayerEvent.OnAddVideo -> addVideoUri(event.uri)
            is PlayerEvent.OnSetVideo -> setVideo(event.uri)
            is PlayerEvent.PlayPauseVideo -> playPauseVideo()
            is PlayerEvent.OnRotationChange -> changeScreenRotation()
        }
	}
    private fun  changeScreenRotation(){
        if(orientation.value == ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT)
            _orientation.value = ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
        else
            _orientation.value = ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT

    }
    private fun addVideoUri(uri: Uri){
        savedStateHandle["videoUris"] = videoUris.value + uri
        player.addMediaItem(MediaItem.fromUri(uri))
    }

    private fun setVideo(uri: Uri){
        player.setMediaItem(
            MediaItem.fromUri(uri)
        )
    }

    private fun playPauseVideo(){
        if (player.isPlaying){
            player.pause()
        }else {
            player.play()
        }
        state = state.copy(isVideoPlaying = player.isPlaying)
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }
}