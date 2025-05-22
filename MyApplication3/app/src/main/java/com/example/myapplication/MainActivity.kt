package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                MainMenuScreen(
                    schnitzelJagd = {
                        // Intent für Schnitzeljagd starten
                        // val intent = Intent(this@MainActivity, SchnitzeljagdActivity::class.java)
                        // startActivity(intent)
                    },
                    onSettingsClick = {
                        // Intent für Einstellungen starten
                        // val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                        // startActivity(intent)
                    },
                    onCameraClick = {
                        val intent = Intent(this@MainActivity, CameraX::class.java)
                        startActivity(intent)
                    }
                )
            }
        }
    }
}

@Composable
fun MainMenuScreen(
    schnitzelJagd: () -> Unit,
    onSettingsClick: () -> Unit,
    onCameraClick: () -> Unit
) {
    val modus = remember { mutableStateOf(false) }
    //Die Box, die alle anderen Dinge fässt
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background((Color(0xFFE8F5E9))) //Hellgrüner Hintergrund
    ) {

        // Button-Zeile oben
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = 100.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            //Schnitzeljagd Icon Button
            IconButton(
                onClick = {
                    modus.value = !modus.value
                    schnitzelJagd()
                },
                modifier = Modifier.padding(5.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.schnitzeljagdbild),
                    contentDescription = "Schnitzeljagdbutton"
                )
            }

            //Einstellungen Button
            IconButton(
                onClick = onSettingsClick,
                modifier = Modifier
                    .padding(bottom = 50.dp)
                    .padding(start = 195.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.settingsicon),
                    contentDescription = "Einstellungsbutton",
                )
            }
        }

        //Anzeige in der Mitte
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
                //Schnitzeljagd Liste
                val eintraege = remember {
                    mutableStateListOf(
                        SchnitzeljagdEintrag("Wolters", false),
                        SchnitzeljagdEintrag("Astra", false),
                        SchnitzeljagdEintrag("Krombacher", false),
                        SchnitzeljagdEintrag("Veltins", false),
                        SchnitzeljagdEintrag("Kölsch", false),
                    )
                }
                //Wenn Modus nicht aktiviert, zeige Infoanzeige
                if (!modus.value) {
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
                    //Wenn Modus aktiviert, zeige Liste
                } else {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        eintraege.forEachIndexed { index, eintrag ->
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
                                IconButton(
                                    onClick = {
                                        eintrag.erledigt = !eintrag.erledigt
                                    }
                                ) {
                                    Icon(
                                        painter = painterResource(
                                            id = if (eintrag.erledigt) R.drawable.hakenicon else R.drawable.nichterledigticon
                                        ),
                                        contentDescription = if (eintrag.erledigt) "Erledigt" else "Offen",
                                        tint = if (eintrag.erledigt) Color(0xFF388E3C) else Color(
                                            0xFFD32F2F
                                        )
                                    )
                                }
                            }
                        }
                    }
                }

            }
        } // Ende Box Mitte für Info oder Schnitzeljagd

            //Kamera Icon-Button
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
                        contentDescription = "Fotobutton",
                        modifier = Modifier.size(180.dp)
                    )
                    Spacer(modifier = Modifier.width(7.dp))
                }
            }

    }
}

//Funktion für die Infoanzeige, die jedes Mal neu sein soll
fun infoTextRandom(): String {
    val infos = arrayOf(
        "Was ist dein Lieblingsbier?",
        "Du trinkst bestimmt nur Radler, hihi",
        "Einfach Foto aufnehmen und deine Empfehlung generieren"
    )
    val rndnmb = Random.nextInt(4)
    return infos[rndnmb]
}
