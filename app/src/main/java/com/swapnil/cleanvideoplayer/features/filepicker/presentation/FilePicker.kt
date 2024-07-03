package com.swapnil.cleanvideoplayer.features.filepicker.presentation

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.swapnil.cleanvideoplayer.MainActivity
import com.swapnil.cleanvideoplayer.features.player.presentation.PlayerActivity
import com.swapnil.cleanvideoplayer.ui.theme.CleanVideoPlayerTheme

@Composable
fun FilePickerScreenRoot(
    mainActivity: MainActivity
) {

    val playerActivityLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult(),
            onResult = {})

    val selectVideoLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent(),
            onResult = { uri ->
                uri?.let {
                    val playerIntent = Intent(mainActivity, PlayerActivity::class.java).apply {
                        data = it
                    }
                    playerActivityLauncher.launch(playerIntent)
                }
            })

    FilePickerScreen(modifier = Modifier.fillMaxSize(), openVideoPicker = {
        selectVideoLauncher.launch("video/mp4")
    })
}

@Composable
private fun FilePickerScreen(
    modifier: Modifier = Modifier, openVideoPicker: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Button(onClick = {
            openVideoPicker()
        }) {
            Text(text = "Pick File")
        }
    }
}


@Preview
@Composable
private fun FilePickerScreenPreview() {
    CleanVideoPlayerTheme {
        FilePickerScreen(openVideoPicker = {})
    }
}