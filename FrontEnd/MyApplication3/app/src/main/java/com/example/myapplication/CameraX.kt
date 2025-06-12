@file:Suppress("UNREACHABLE_CODE")
@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.myapplication

import android.Manifest
import android.R.attr.contentDescription
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
import com.example.myapplication.MainActivity
import org.json.JSONObject
import kotlin.jvm.java

/**
 * Activity, die die CameraX-Preview und den gesamten Foto-Capture-Flow beheimatet.
 *
 * Die Klasse kapselt ausschließlich UI-Logik; das eigentliche Camera-Binding
 * geschieht im [CameraPreviewViewModel]. Nach erfolgreichem Foto-Capture kann
 * das Bild an einen REST-Endpunkt hochgeladen werden. Ein Klick auf das Home-
 * Icon schließt die Activity und kehrt zur MainActivity zurück.
 */
class CameraX : ComponentActivity() {
    companion object {
        /**
         * Basis‑URL der REST‑Schnittstelle. Zeigt aktuell auf einen temporären
         * ngrok‑Tunnel. Für den Produktivbetrieb in eine feste URL ändern.
         */
        public const val BASE_URL = "https://79c4-2003-e9-d734-7a00-8d84-26da-c3d2-646f.ngrok-free.app/beer/"
    }

    //Ergebnislauncher für Schnitzeljagd
    private lateinit var ergebnisLauncher: ActivityResultLauncher<Intent>

    /**
     * Initialisiert Edge‑to‑Edge‑Darstellung und setzt das Compose‑UI.
     *
     * Beim Zurücknavigieren (Home‑Icon) wird einfach `finish()` aufgerufen,
     * da diese Activity keine tiefer gehende Navigation besitzt.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Ergebnis-Launcher registrieren
        ergebnisLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val erledigtesBier = result.data?.getStringExtra("erledigtesBier")
                val resultIntent = Intent().apply {
                    putExtra("erledigtesBier", erledigtesBier)
                }
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }

        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                CameraPreviewScreen(
                    viewModel = CameraPreviewViewModel(),
                    onBackToMenuClicked = { finish() },
                    onSendClick = {
                        // Öffnet die CameraX‑Preview‑Activity
                        val intent = Intent(this@CameraX, ErgebnisScreen::class.java)
                        ergebnisLauncher.launch(intent)
                    }
                )
            }
        }
    }
}

/* -------------------------------------------------------------------------- */
/*                          Netzwerk / Datei‑Hilfsfunktionen                  */
/* -------------------------------------------------------------------------- */

/**
 * Lädt eine JPEG‑Datei per Multipart‑Upload zu einem Server.
 *
 * @param file          Das Bild, das hochgeladen werden soll.
 * @param updateMessage Lambda zum Aktualisieren einer UI‑Message (z.B. Toast
 *                      oder Status‑Text) – erhält eine Meldung über Erfolg oder
 *                      Fehler.
 */

/**
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

            val jsonString = response.body()?.string() //body statt jsonString

            val message = if (response.isSuccessful) {
                val json = JSONObject(jsonString) // body statt jsonString
                val bier1 = json.getString("bier1")
                val bier2 = json.getString("bier2")
                val bier3 = json.getString("bier3")
                val bier4 = json.getString("bier4")
                val bier5 = json.getString("bier5")
                val flopbier = json.getString("flopbier")

                "Upload successful: $json"
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
*/

/**
 * Sucht das zuletzt erstellte Foto im Cache‑Verzeichnis.
 *
 * Die Kamera legt ihre Bilder im Format `photo_<Timestamp>.jpg` ab. Diese
 * Funktion filtert entsprechend und nimmt die jüngste Datei.
 *
 * @return  Die zuletzt erstellte Bild‑Datei oder `null`, falls keine gefunden.
 */
fun findLatestCapturedPhoto(cacheDir: File): File? {
    return cacheDir.listFiles { file ->
        file.name.startsWith("photo_") && file.name.endsWith(".jpg")
    }?.maxByOrNull { it.lastModified() }
}

/* -------------------------------------------------------------------------- */
/*                                 UI‑Layer                                   */
/* -------------------------------------------------------------------------- */

/**
 * Prüft die Kameraberechtigung und zeigt entweder die Kamera‑Preview oder eine
 * freundliche Permission‑Rationale.
 *
 * @param viewModel            State‑ & Camera‑Handling.
 * @param onBackToMenuClicked  Callback, der beim Klick auf das Home‑Icon
 *                             ausgelöst wird.
 * @param modifier             Externer Modifier‑Chain.
 */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraPreviewScreen(
    viewModel: CameraPreviewViewModel,
    onBackToMenuClicked: () -> Unit,
    modifier: Modifier = Modifier,
    onSendClick: () -> Unit
) {
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    if (cameraPermissionState.status.isGranted) {
        // Berechtigung vorhanden → Kamera‑UI anzeigen
        CameraPreviewContent(viewModel, onBackToMenuClicked = onBackToMenuClicked,
            modifier, onSendClick = onSendClick)
    } else {
        /* ---------- Rationale / Permission‑Request ---------- */
        Column(
            modifier = modifier.fillMaxSize().wrapContentSize().widthIn(max = 480.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val textToShow = if (cameraPermissionState.status.shouldShowRationale) {
                // If the user has denied the permission but the rationale can be shown,
                // then gently explain why the app requires this permission
                "Um diese App nutzen zu können, brauchen wir deine Kameraberechtigung!"
            } else {
                // If it's the first time the user lands on this feature, or the user
                // doesn't want to be asked again for this permission, explain that the
                // permission is required
                "Hi, wir benötigen Zugriff auf deine Kamera zum Benutzen der App! ✨\n" +
                "Gib uns deine Berechtigung und lass uns die Bierreise starten! \uD83C\uDF89"
            }
            Text(textToShow, textAlign = TextAlign.Center)
            Spacer(Modifier.height(16.dp))
            Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                Text("Kamera freigeben")
            }
        }
    }
}

/**
 * Zeigt die Live‑Preview (CameraXViewfinder), einen Auslöser‑Button, ein Home‑
 * Icon sowie wahlweise die zuletzt geschossene Aufnahme oder ein Ergebnis‑
 * Bild.
 *
 * @param viewModel           Zustandsobjekt, das die CameraX‑Bindung enthält.
 * @param onBackToMenuClicked Callback für das Home‑Icon.
 * @param modifier            Externer Modifier.
 * @param lifecycleOwner      Wird standardmäßig aus Compose‑Umgebung gezogen,
 *                            kann aber im Testfall überschrieben werden.
 */
@Composable
fun CameraPreviewContent(
    viewModel: CameraPreviewViewModel,
    onBackToMenuClicked: () -> Unit,
    modifier: Modifier = Modifier,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onSendClick: () -> Unit
) {
    /* ---------------- State & lokale Referenzen ---------------- */
    val surfaceRequest by viewModel.surfaceRequest.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var photoUri by remember { mutableStateOf<android.net.Uri?>(null) }
    var message by remember { mutableStateOf("Press the button to upload the image.") }
    var showTable by remember { mutableStateOf(false) }

    /* ---------------- Kamera an Lifecycle binden ---------------- */
    LaunchedEffect(lifecycleOwner) {
        viewModel.bindToCamera(context.applicationContext, lifecycleOwner)
    }

    /* ---------------- Live‑Preview ---------------- */
    surfaceRequest?.let { request ->
        CameraXViewfinder(
            surfaceRequest = request,
            modifier = modifier
        )
    }

    /* ---------------- UI‑Overlay ---------------- */
    Box(modifier = Modifier.fillMaxSize()) {

        surfaceRequest?.let { request ->
            CameraXViewfinder(
                surfaceRequest = request,
                modifier = Modifier.fillMaxSize()
            )
        }

        /* ---------- Kamera‑Auslöser ---------- */
        IconButton(
            onClick = {
                showTable = false   //beim neuen Foto Ergebnis‑Bild ausblenden
                viewModel.takePhoto(
                    context = context,
                    onImageSaved = { file -> photoUri = file.toUri() },
                    onError = { error -> error.printStackTrace()
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
                contentDescription = "Foto aufnehmen",
                modifier = Modifier.size(64.dp)
            )
        }

        /* ---------- Ergebnis‑ bzw. Foto‑Anzeige ---------- */
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize()
        ) {
            if (showTable) {
                // TODO: Ersetze Hardcode durch echtes Ergebnis, sobald vorhanden
                Image(
                    painter = painterResource(R.drawable.ergebnis),
                    contentDescription = "Ergebnis",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            } else {
                photoUri?.let { uri -> //"Uniform Resource Identifier" Ist der Pfad zu einem Bild
                    Image(
                        painter = rememberAsyncImagePainter(uri), //zeigt das Bild an, indem er Uri aufruft
                        contentDescription = "Aufgenommenes Foto",
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop //Größe des Bildes anpassen, da es sonst nicht über den Bildschirm gestreckt wäre
                    )
                }
            }
        }
        /* ---------- Home‑Icon ---------- */
        IconButton(
            onClick = onBackToMenuClicked,  //öffnet die Funktion im oberen Teil des Codes
            modifier = Modifier.align(Alignment.TopStart).padding(32.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.homeicon),
                tint = Color.White,
                contentDescription = "Zurück zum Menü",
                modifier = Modifier.size(64.dp)
            )
        }

        /* ---------- Foto senden ---------- */
        if (photoUri != null && !showTable) {
            IconButton(
                onClick = {
                    val intent = Intent(context, ErgebnisScreen::class.java).apply {
                        putExtra("bier1", "Wolters")
                        putExtra("bier2", "Astra Rakete")
                        putExtra("bier3", "Krombacher")
                        putExtra("bier4", "Veltins")
                        putExtra("bier5", "Kölsch")
                        putExtra("flopbier", "5,0")
                    }
                    context.startActivity(intent)
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