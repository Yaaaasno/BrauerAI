package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlin.random.Random

/**
 * Haupt‑Activity der BRAUERAI‑App.
 *
 * Diese Klasse ist der Einstiegspunkt der Anwendung und hostet den gesamten
 * Jetpack‑Compose‑UI‑Baum. Navigation zu weiteren Features erfolgt über
 * Callbacks, die als Parameter an die Composables übergeben werden.
 */
class MainActivity : ComponentActivity() {

    /**
     * Lifecycle‑Callback nach dem Erstellen der Activity.
     *
     * @param savedInstanceState    Zustand der Activity aus einer früheren Instanz
     *                              oder `null`, falls die Activity frisch gestartet wird.
     *
     * In dieser Methode wird Edge‑to‑Edge Drawing aktiviert, anschließend wird
     * das Compose‑UI geladen und die drei Menü‑Callbacks an die
     * [MainMenuScreen]-Composable weitergereicht.
     */

    // Launcher für Ergebnis von CameraX / ErgebnisScreen
    private lateinit var cameraXLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Mutable List für die Schnitzeljagd‑Einträge
        val eintraege =
            mutableStateListOf(
                SchnitzeljagdEintrag("Wolters", false),
                SchnitzeljagdEintrag("Astra Rakete", false),
                SchnitzeljagdEintrag("Krombacher", false),
                SchnitzeljagdEintrag("Veltins", false),
                SchnitzeljagdEintrag("Kölsch", false),
            )


        // Ergebnis-Launcher registrieren
        cameraXLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val erledigtesBier = result.data?.getStringExtra("erledigtesBier")
                if (erledigtesBier != null) {
                    // Markiere das Bier als erledigt in der Liste
                    val index = eintraege.indexOfFirst { it.titel == erledigtesBier }
                    if (index >= 0) {
                        eintraege[index] = eintraege[index].copy(erledigt = true)
                    }
                }
            }
        }

        setContent {
            MyApplicationTheme {
                MainMenuScreen(
                    onSettingsClick = {
                        // TODO: Intent für Einstellungen starten, sobald implementiert
                        // val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                        // startActivity(intent)
                    },
                    onCameraClick = {
                        // Öffnet die CameraX‑Preview‑Activity
                        val intent = Intent(this@MainActivity, CameraX::class.java)
                        cameraXLauncher.launch(intent)
                    },
                    schnitzeljagdEintraege = eintraege
                )
            }
        }
    }
}

/**
 * Startbildschirm der App.
 *
 * Der Screen besteht aus drei Bereichen:
 * 1. Einer Button‑Leiste oben (Schnitzeljagd & Einstellungen)
 * 2. Einer mittigen Content‑Area, die je nach Modus eine Info‑Anzeige oder die
 *    Schnitzeljagd‑Liste darstellt
 * 3. Einem Kamera‑Button am unteren Rand
 *
 * @param schnitzelJagd   Callback beim Klick auf den Schnitzeljagd‑Button.
 * @param onSettingsClick Callback beim Klick auf den Einstellungen‑Button.
 * @param onCameraClick   Callback beim Klick auf den Kamera‑Button.
 */
@Composable
fun MainMenuScreen(
    onSettingsClick: () -> Unit,
    onCameraClick: () -> Unit,
    schnitzeljagdEintraege: List<SchnitzeljagdEintrag>
) {
    // Lokaler UI‑State: `false` = Info‑Ansicht, `true` = Schnitzeljagd‑Liste
    val modus = remember { mutableStateOf(false) }

    /* ---------------- Root‑Container ---------------- */
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background((Color(0xFFE8F5E9)))
    ) {

        /* ---------------- Header Buttons ---------------- */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = 100.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            // Schnitzeljagd-Icon-Button (Toggle + Callback)
            IconButton(
                onClick = {
                    modus.value = !modus.value
                },
                modifier = Modifier.padding(5.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.schnitzeljagdbild),
                    contentDescription = "Schnitzeljagdbutton"
                )
            }

            // Einstellungen-Button
            IconButton(
                onClick = onSettingsClick,
                modifier = Modifier
                    .padding(bottom = 50.dp)
                    .padding(start = 195.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.settingsicon),
                    contentDescription = "Einstellungen-Button",
                )
            }
        }

        /* ---------------- Content‑Area ---------------- */
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 32.dp)
            ) {

                // Wenn Modus nicht aktiviert, zeige Infoanzeige
                if (!modus.value) {
                    /* ---- Info‑Ansicht ---- */
                    Icon(
                        painter = painterResource(id = R.drawable.info),
                        contentDescription = "Information",
                        tint = Color(0xFF2E7D32),
                        modifier = Modifier.size(48.dp)
                    )

                    Text(
                        text = "Willkommen in der BRAUERAI-App\n\n" + infoTextRandom(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFF2E7D32),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                } else {
                    /* ---- Schnitzeljagd‑Liste ---- */
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        schnitzeljagdEintraege.forEach { eintrag ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.White)
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = eintrag.titel,
                                    color = Color.Black,
                                    style = MaterialTheme.typography.bodyLarge
                                )

                                Icon(
                                    painter = painterResource(
                                        id = if (eintrag.erledigt)
                                            R.drawable.hakenicon
                                        else
                                            R.drawable.nichterledigticon
                                    ),
                                    contentDescription = if (eintrag.erledigt) "Erledigt" else "Offen",
                                    tint = if (eintrag.erledigt) Color(0xFF388E3C) else Color(0xFFD32F2F)
                                )

                            }
                        }
                    }
                }
            }
        }

            /* ---------------- Kamera‑Button ---------------- */
            IconButton(
                onClick = onCameraClick,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 50.dp),
                enabled = true,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.fotoicon),
                        contentDescription = "Kamera öffnen",
                        modifier = Modifier.size(180.dp)
                    )
                    Spacer(modifier = Modifier.width(7.dp))
                }
            }
    }
}

/**
 * Liefert einen zufälligen Info‑Text für die Begrüßungsanzeige.
 *
 * @return String mit einer humorvollen oder informativen Bier‑Botschaft.
 */
fun infoTextRandom(): String {
    val infos = arrayOf(
        "Was ist dein Lieblingsbier?",
        "Du trinkst bestimmt nur Radler, hihi",
        "Einfach Foto aufnehmen und deine Empfehlung generieren",
        "Was ist dein liebstes Projekt auf dem TDSE?",
        "Schon gewusst? Bier enthält über 800 verschiedene Aromastoffe.",
        "Heute schon ein neues Bier entdeckt?",
        "Welches Bier würdest du deinen Freunden empfehlen?",
        "Fun Fact: In Bayern ist Bier ein Grundnahrungsmittel.",
        "Das perfekte Bier für dich – in nur einem Klick!",
        "Kannst du dein Lieblingsbier nur am Etikett erkennen?",
        "Die beste Bierauswahl beginnt mit einem Foto!",
        "Hast du schon alle Sorten aus der Top 5 probiert?",
        "Welche Brauerei beeindruckt dich am meisten?",
        "Nimm dir ein Bier und lehn dich zurück – die App übernimmt."
    )
    // Auswahl eines zufälligen Eintrags
    val rndnmb = Random.nextInt(infos.size)
    return infos[rndnmb]
}