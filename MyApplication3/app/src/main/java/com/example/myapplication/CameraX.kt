@file:Suppress("UNREACHABLE_CODE")

@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.myapplication

import android.Manifest
import android.R.attr.contentDescription
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
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
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import kotlin.random.Random
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull as toMediaTypeOrNull1
import okhttp3.RequestBody.Companion.asRequestBody as asRequestBody1
import androidx.compose.ui.layout.ContentScale


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

/**
 *
 *
 *
 */
fun uploadImageFromFile(file: File, updateMessage: (String) -> Unit) {
    val retrofit = Retrofit.Builder()
        .baseUrl(CameraX.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(ApiService::class.java)

    val mediaType = "image/jpeg".toMediaTypeOrNull1()
    val requestBody = file.asRequestBody1(mediaType)
    val imagePart = MultipartBody.Part.createFormData("upload_file", file.name, requestBody)

    apiService.uploadImage(imagePart).enqueue(object : Callback<ResponseBody> {
        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            val message = if (response.isSuccessful) {
                val responseBody = response.body()?.string() ?: "No response body"
                "Upload successful: $responseBody"
            } else {
                "Upload failed: ${response.code()}"
            }
            updateMessage(message)
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            updateMessage("Upload error: ${t.message}")
        }
    })
}

/**
 *
 *
 *
 */
fun findLatestCapturedPhoto(cacheDir: File): File? {
    return cacheDir.listFiles { file ->
        file.name.startsWith("photo_") && file.name.endsWith(".jpg")
    }?.maxByOrNull { it.lastModified() }
}

/**
 *
 *
 *
 */
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

/**
 *
 *
 *
 */
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
    var showTable by remember { mutableStateOf(false) }
    LaunchedEffect(lifecycleOwner) {
        viewModel.bindToCamera(context.applicationContext, lifecycleOwner)
    }

    surfaceRequest?.let { request ->
        CameraXViewfinder(
            surfaceRequest = request,
            modifier = modifier
        )
    }
    Box(modifier = Modifier.fillMaxSize()) {
        surfaceRequest?.let { request ->
            CameraXViewfinder(
                surfaceRequest = request,
                modifier = Modifier.fillMaxSize()
            )
        }
        // Kamera Icon
        IconButton(
            onClick = { //Kamera Button Funktion
                showTable = false   //sagt an, dass
                viewModel.takePhoto(    //Kamera Funktion. takePhoto ist eine in CameraPreviewModel definierte Funktion
                    context = context,  //Context wird übergeben
                    onImageSaved = { file ->
                        photoUri = file.toUri()     //Speichert die URI des aufgenommenen Bildes
                    },
                    onError = { error ->            //Fehlerabdeckung
                        error.printStackTrace()     //Zeigt den Fehler in der Konsole an
                    }
                )
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(32.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.fotoicon),
                tint = Color.White,
                contentDescription = "Kamera Button",
                modifier = Modifier.size(64.dp)
            )
        }
        // Home Icon
        IconButton(
            onClick = onBackToMenuClicked,  //Öffnet die Funktion im oberen Teil des Codes
            modifier = Modifier.align(Alignment.TopStart).padding(32.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.homeicon),
                tint = Color.White,
                contentDescription = "Home Button",
                modifier = Modifier.size(64.dp)
            )
        }

        //Provisorischer Hardcode: Mittelbereich, der entweder aufgenommenes Foto oder eine Tabelle zeigt
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize()
        ) {
            if (showTable) {
                Image(
                    painter = painterResource(R.drawable.ergebnis),
                    contentDescription = "Ergebnis",
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            } else {
                photoUri?.let { uri -> // "Uniform Resource Identifier" Ist der Pfad zu einem Bild
                    Image(
                        painter = rememberAsyncImagePainter(uri),   //Zeigt das Bild an, indem er Uri aufruft
                        contentDescription = "Aufgenommenes Foto",
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop    //Größe des Bildes anpassen, da es sonst nicht über den Bildschirm gestreckt wäre
                    )
                }
            }
        }

        // Foto senden Icon
        if (photoUri != null && !showTable) { //Ist er aktiv, wenn ein foto aufgenommen wurde
            IconButton(
                onClick = {
//                  val latestPhoto = findLatestCapturedPhoto(context.cacheDir)
//                  if (latestPhoto != null && latestPhoto.exists()) {
//                  uploadImageFromFile(latestPhoto) { newMessage ->
//                  message = newMessage
                    showTable = true    //Hardcode für Präsentation
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(32.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.fotosendenicon),
                    tint = Color.White,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp)
                )
            }
        }
    }
}

