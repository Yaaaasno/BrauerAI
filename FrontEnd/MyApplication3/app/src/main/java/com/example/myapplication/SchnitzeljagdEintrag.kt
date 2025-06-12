package com.example.myapplication

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember

/**
 * Datenklasse für einen Schnitzeljagd‑Eintrag.
 *
 * @property titel    Der angezeigte Name des Eintrags.
 * @property erledigt Flag, ob der Eintrag bereits abgeschlossen ist.
 */
data class SchnitzeljagdEintrag(
    val titel: String,
    var erledigt: Boolean
)