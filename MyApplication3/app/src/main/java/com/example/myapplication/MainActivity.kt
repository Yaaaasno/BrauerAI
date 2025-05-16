package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                Box(modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ){

                    IconButton(onClick = {
                        val intent = Intent(this@MainActivity, CameraX::class.java)
                        startActivity(intent)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.bildericonss_background),
                            contentDescription = null
                        )
                    }
                }
                }
            }
        }
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hallo $name!",
            modifier = modifier
        )
    }

    @Composable
    fun FilledTonalButtonExample(onClick: () -> CameraX) {
        val viewModel = remember { CameraPreviewViewModel() }
        CameraPreviewScreen(viewModel)

        FilledTonalButton(onClick = { }) {
            Text("Foto schießen")
        }
    }


