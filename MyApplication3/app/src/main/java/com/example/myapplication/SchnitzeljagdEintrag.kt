package com.example.myapplication

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember

data class SchnitzeljagdEintrag(
    val titel: String,
    var erledigt: Boolean
)
