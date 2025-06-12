package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import org.json.JSONObject

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.BeerInfo
import com.example.myapplication.ui.theme.beerInfoMap


/**
 * Zeigt die Bier-Ergebnisse in einer Top-Liste mit einem speziellen Abschnitt für das Flopbier.
 *
 * @param bierListe Die Liste der Top-Biere in Reihenfolge
 * @param flopbier  Das Bier, das als schlechtestes bewertet wurde
 */

class ErgebnisScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Daten aus dem Intent lesen
        val bierListe = listOf(
            intent.getStringExtra("bier1") ?: "Krombacher",
            intent.getStringExtra("bier2") ?: "Wolters",
            intent.getStringExtra("bier3") ?: "Bitburger",
            intent.getStringExtra("bier4") ?: "Veltins",
            intent.getStringExtra("bier5") ?: "Kölsch"
        )
        val flopbier = intent.getStringExtra("flopbier") ?: "Astra"

        setContent{
            MyApplicationTheme {
                ResultScreen(
                    bierListe = bierListe,
                    flopbier = flopbier,
                    onBackToMenuClicked = {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)

                        // Ergebnis mit erstem Bier zurückgeben
                        val resultIntent = Intent().apply {
                            putExtra("erledigtesBier", bierListe[0])
                        }
                        setResult(RESULT_OK, resultIntent)
                        print(resultIntent)
                        finish()
                    }
                )
            }
        }
    }
}
@Composable
fun ResultScreen(
    bierListe: List<String>,
    flopbier: String,
    onBackToMenuClicked: () -> Unit
) {
    var selectedBier by remember { mutableStateOf<String?>(null) }

    val selectedInfo = selectedBier?.let { beerInfoMap[it] }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //------------------ Home-Icon ------------------------
        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(
                onClick = onBackToMenuClicked,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.homeicon),
                    contentDescription = "Zurück zum Menü",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
        //--------------- Ergebnis Anzeige ----------------------
        Text("Top 5 Biere", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            itemsIndexed(bierListe) { index, bier ->
                Text(
                    text = "Platz ${index + 1}: $bier",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = (30 - index * 2).dp.value.sp  // Größere Schrift für die ersten Plätze
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            selectedBier = bier
                        }
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
        Divider()
        Spacer(modifier = Modifier.height(16.dp))

        Text("Flopbier: ", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = flopbier,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.clickable {
                selectedBier = flopbier
            }
        )

    }

    // Dialog anzeigen
    if (selectedInfo != null) {
        AlertDialog(
            onDismissRequest = { selectedBier = null },
            title = { Text(text = selectedInfo.brand) },
            text = {
                Column {
                    Text(selectedInfo.detail1)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(selectedInfo.detail2)
                }
            },
            confirmButton = {
                TextButton(onClick = { selectedBier = null }) {
                    Text("OK")
                }
            }
        )
    }
}

