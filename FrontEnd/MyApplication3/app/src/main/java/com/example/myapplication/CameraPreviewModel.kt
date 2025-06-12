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

/**
 * ViewModel, das alle CameraX‑bezogenen Use‑Cases hält und die Preview‑Oberfläche
 * via StateFlow an die UI liefert.
 *
 * Aufrufe
 * ------
 *  * [bindToCamera] – Stellt die Live‑Preview her und bindet sie an den
 *                     Lifecycle des Compose‑Hosts.
 *  * [takePhoto] – Fertigt ein Einzel‑Bild an und legt es im App‑Cache ab.
 *
 * Lebenszyklus
 * ------------
 *  * Solange die Composable, die das ViewModel hält, aktiv ist, läuft die
 *    Kamera. Beim Abbruch (Coroutine‑Cancellation) wird *unbindAll()*
 *    aufgerufen, um Ressourcen freizugeben.
 */
class CameraPreviewViewModel : ViewModel() {

    /* ---------------------------------------------------------------------- */
    /*                               StateFlow                                */
    /* ---------------------------------------------------------------------- */

    /**
     * Stellt der UI das aktuelle [SurfaceRequest]‑Objekt bereit, das von
     * [Preview.setSurfaceProvider] geliefert wird. Ein `null` bedeutet, dass
     * die Kamera noch nicht bereit ist oder der Preview‑Stream gerade
     * neugestartet wird.
     */
    private val _surfaceRequest = MutableStateFlow<SurfaceRequest?>(null)
    val surfaceRequest: StateFlow<SurfaceRequest?> = _surfaceRequest

    /* ---------------------------------------------------------------------- */
    /*                              Use‑Cases                                 */
    /* ---------------------------------------------------------------------- */

    /**
     * Preview‑Use‑Case für den Live‑Stream. Der *SurfaceProvider* schreibt das
     * jeweils neue [SurfaceRequest] in den StateFlow, sodass die Composable
     * reagieren kann.
     */
    private var previewUseCase = Preview.Builder().build().apply {
        setSurfaceProvider { request -> _surfaceRequest.update { request } }
    }

    /**
     * Image‑Capture‑Use‑Case wird lazy initialisiert, sobald [bindToCamera]
     * aufgerufen wird. Erzeugt Einzel‑Fotos hoher Qualität über CameraX.
     */
    private var imageCaptureUseCase: ImageCapture? = null

    /* ---------------------------------------------------------------------- */
    /*                              Public API                                */
    /* ---------------------------------------------------------------------- */

    /**
     * Bindet Preview- und ImageCapture‑Use‑Case an die Rück-Kamera.
     *
     * @param appContext     *Application*‑Kontext, benötigt für das Abrufen
     *                       des [ProcessCameraProvider].
     * @param lifecycleOwner Owner, an dessen Lifecycle die Kamera gebunden wird
     *                       (typischerweise die Compose‑Activity).
     */
    suspend fun bindToCamera(appContext: Context, lifecycleOwner: LifecycleOwner) {
        val processCameraProvider = ProcessCameraProvider.awaitInstance(appContext)

        // ImageCapture initialisieren (maximale Qualität)
        imageCaptureUseCase = ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
            .build()

        try {
            // Vorhandene Use‑Cases trennen und neu binden
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

        // Coroutine läuft, bis das ViewModel gecleared wird
        try {
            awaitCancellation()
        } finally {
            processCameraProvider.unbindAll()
        }
    }

    /**
     * Löst die Aufnahme eines Einzel‑Fotos aus.
     *
     * @param context       *Context* für den Main‑Executor und Dateisystem.
     * @param onImageSaved  Callback mit der gespeicherten Datei bei Erfolg.
     * @param onError       Callback bei Fehlern (z. B. Auslöse‑Timeout).
     */
    fun takePhoto(
        context: Context,
        onImageSaved: (File) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val imageCapture = imageCaptureUseCase //Speichert die Kamera in der Variable
        if (imageCapture == null) {
            onError(IllegalStateException("ImageCapture noch nicht initialisiert"))
            return
        }

        val photoFile = File(context.cacheDir, "photo_${System.currentTimeMillis()}.jpg") //Setzt den Speicherort des Fotos (Cache)
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture( //von CameraX importierte takePicture-Funktion
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    onImageSaved(photoFile) //Bei Erfolg wird die Funktion ausgeführt
                }

                override fun onError(exception: ImageCaptureException) {
                    onError(exception)
                }
            }
        )
    }
}