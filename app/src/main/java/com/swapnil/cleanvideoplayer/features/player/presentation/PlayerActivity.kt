package com.swapnil.cleanvideoplayer.features.player.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.swapnil.cleanvideoplayer.features.player.presentation.screens.player_screen.PlayerScreenRoot
import com.swapnil.cleanvideoplayer.features.player.presentation.screens.player_screen.composibles.TimeStampsDrawer
import com.swapnil.cleanvideoplayer.features.player.presentation.screens.player_screen.events.PlayerEvent
import com.swapnil.cleanvideoplayer.features.player.presentation.screens.player_screen.viewModels.PlayerViewModel
import com.swapnil.cleanvideoplayer.ui.theme.CleanVideoPlayerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlayerActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: PlayerViewModel by viewModels()

        viewModel.orientation.observe(this){
            requestedOrientation = it
        }

        enableEdgeToEdge()
        setContent {
            CleanVideoPlayerTheme {
                Surface(Modifier.fillMaxSize()) {
                    //val viewModel = hiltViewModel<PlayerViewModel>()

                    val videoUri = intent.data

                    videoUri?.let {
                        viewModel.onAction(PlayerEvent.OnAddVideo(it))
                    }

                    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                    val coroutineScope = rememberCoroutineScope()

                    ModalNavigationDrawer(
                        drawerState = drawerState,
                        drawerContent = {
                            TimeStampsDrawer(drawerState = drawerState,
                                modifier = Modifier.wrapContentWidth(Alignment.End)
                                )
                        }) {
                        PlayerScreenRoot(
                            viewModel = viewModel,
                            onBackClick = { finish() },
                            onMenuClick = {
                                coroutineScope.launch {
                                    if(drawerState.isOpen)
                                        drawerState.close()
                                    else
                                        drawerState.open()
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}