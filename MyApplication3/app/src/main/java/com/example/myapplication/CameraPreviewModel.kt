package com.example.myapplication

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceRequest
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.lifecycle.awaitInstance
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.io.File

class CameraPreviewViewModel : ViewModel() {
    // Used to set up a link between the Camera and your UI.
    private val _surfaceRequest = MutableStateFlow<SurfaceRequest?>(null)      //Kamera Anfrage, für die UI zum Abfragen der Erlaubnis
    val surfaceRequest: StateFlow<SurfaceRequest?> = _surfaceRequest    //StateFlow ermöglicht reaktive Änderung. SurfaceRequest im Allgemeinen für die LiveKamera

    private var previewUseCase = Preview.Builder().build().apply {  //Baut eine Preview für die Kamera auf
        setSurfaceProvider { request -> _surfaceRequest.update { request } }    //Setzt die Anfrage als SurfaceProvider
    }

    private var imageCaptureUseCase: ImageCapture? = null   //Kameramodul welches fotos aufnehmen kann

    suspend fun bindToCamera(appContext: Context, lifecycleOwner: LifecycleOwner) { //Verbindung von Kamera und UI
        val processCameraProvider = ProcessCameraProvider.awaitInstance(appContext) //Ruft CameraProvider ab

        imageCaptureUseCase = ImageCapture.Builder()    //Objekt für Bildaufnahme
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY) //Einstellung des FotoModuses
            .build()

        try {   //Verbindung der Vorschau auf Kamera und Kamera auf UI
            processCameraProvider.unbindAll()
            processCameraProvider.bindToLifecycle(
                lifecycleOwner,
                CameraSelector.DEFAULT_BACK_CAMERA,
                previewUseCase,
                imageCaptureUseCase
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {   //Bleibt aktiv, bis ViewModel abgebrochen wird
            awaitCancellation()
        } finally {
            processCameraProvider.unbindAll()
        }
    }

    fun takePhoto(
        context: Context,
        onImageSaved: (File) -> Unit,   //Was tun bei speicherung
        onError: (Throwable) -> Unit    //Was tun bei Fehler
    ) {
        val imageCapture = imageCaptureUseCase      //Speichert die Kamera in der Variable
        if (imageCapture == null) {
            onError(IllegalStateException("ImageCapture noch nicht initialisiert"))
            return
        }

        val photoFile = File(context.cacheDir, "photo_${System.currentTimeMillis()}.jpg")       //Setzt den Speicherort des Fotos! IN DEN CACHE SPEICHER
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(   //takePicture Funktion von CameraX importiert
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    onImageSaved(photoFile)     //Bei erfolg, wird die Funktion ausgeführt
                }

                override fun onError(exception: ImageCaptureException) {
                    onError(exception)
                }
            }
        )
    }

}