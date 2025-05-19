@file:Suppress("UNREACHABLE_CODE")

@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.myapplication

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.camera.compose.CameraXViewfinder
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.CameraX.Companion.BASE_URL
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.example.myapplication.ui.theme.ApiService
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody

import org.chromium.base.Callback
import org.chromium.base.ThreadUtils.runOnUiThread
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import kotlin.random.Random

class CameraX : ComponentActivity() {
    companion object {  //Server URL
        public const val BASE_URL = "https://79c4-2003-e9-d734-7a00-8d84-26da-c3d2-646f.ngrok-free.app/beer/"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                CameraPreviewScreen(viewModel =  CameraPreviewViewModel(),
                                    onBackToMenuClicked = {
                                        finish()
                                    }
                    )
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraPreviewScreen(
    viewModel: CameraPreviewViewModel,
    onBackToMenuClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    if (cameraPermissionState.status.isGranted) {
        CameraPreviewContent(viewModel,
            onBackToMenuClicked = onBackToMenuClicked,
            modifier)
    } else {

        Column(
            modifier = modifier.fillMaxSize().wrapContentSize().widthIn(max = 480.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val textToShow = if (cameraPermissionState.status.shouldShowRationale) {
                // If the user has denied the permission but the rationale can be shown,
                // then gently explain why the app requires this permission
                "Ooops, sieht so aus, als würden wir deine Kameraberechtigung benötigen"
            } else {
                // If it's the first time the user lands on this feature, or the user
                // doesn't want to be asked again for this permission, explain that the
                // permission is required
                "Hi, sieht so aus, als würden wir deine Kameraberechtigung benötigen! ✨\n" +
                        "Gib uns deine Berechtigung und lass uns die Bierreise starten! \uD83C\uDF89"
            }
            Text(textToShow, textAlign = TextAlign.Center)
            Spacer(Modifier.height(16.dp))
            Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                Text("Starte die Kamera")
            }
        }
    }
}

@Composable
fun CameraPreviewContent(
    viewModel: CameraPreviewViewModel,
    onBackToMenuClicked: () -> Unit,
    modifier: Modifier = Modifier,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    val surfaceRequest by viewModel.surfaceRequest.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var photoUri by remember { mutableStateOf<android.net.Uri?>(null) }
    var message by remember { mutableStateOf("Press the button to upload the image.") }
    LaunchedEffect(lifecycleOwner) {
        viewModel.bindToCamera(context.applicationContext, lifecycleOwner)
    }

    surfaceRequest?.let { request ->
        CameraXViewfinder(
            surfaceRequest = request,
            modifier = modifier
        )
    }

    Box(modifier = Modifier.fillMaxSize()){
            surfaceRequest?.let { request ->
                CameraXViewfinder(
                    surfaceRequest = request,
                    modifier = Modifier.fillMaxSize()
                )
            }
        // Kamera Icon
        IconButton(
            onClick = {
                viewModel.takePhoto(context = context,
                    onImageSaved = {file -> photoUri = file.toUri() },
                onError = {error -> error.printStackTrace() })
                },
            modifier = Modifier.align(Alignment.BottomCenter).padding(32.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.fotoicon),
                tint = Color.White,
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )
        }
        // Home Icon
        IconButton(
            onClick = onBackToMenuClicked,
            modifier = Modifier.align(Alignment.TopStart).padding(32.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.homeicon),
                tint = Color.White,
                contentDescription = null,
            )
        }
        // Foto senden Icon
        IconButton(
            onClick = {

            },
            modifier = Modifier.align(Alignment.BottomEnd).padding(32.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.fotosendenicon),
                tint = Color.White,
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )
        }

        photoUri?.let {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = null,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

