package com.example.muse.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material.icons.outlined.EmojiObjects
import androidx.compose.material.icons.outlined.Science
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.muse.data.SubjectContent
import com.example.muse.ui.theme.MuseBlue
import com.example.muse.ui.theme.MuseBlueLight
import com.example.muse.ui.theme.MuseGreen
import com.example.muse.ui.theme.MuseGreenLight
import com.example.muse.ui.theme.MusePurple
import com.example.muse.ui.theme.MusePurpleLight
import com.example.muse.ui.theme.MuseRed
import com.example.muse.ui.theme.MuseRedLight

/** Иконка и цвета предмета — единый стиль для главного экрана и экрана предмета. */
data class SubjectStyle(
    val icon: ImageVector,
    val tint: Color,
    val tintBackground: Color
)

fun styleFor(subject: SubjectContent): SubjectStyle = when (subject.id) {
    "math" -> SubjectStyle(Icons.Outlined.Calculate, MuseBlue, MuseBlueLight)
    "physics" -> SubjectStyle(Icons.Outlined.EmojiObjects, MusePurple, MusePurpleLight)
    "chemistry" -> SubjectStyle(Icons.Outlined.Science, MuseGreen, MuseGreenLight)
    else -> SubjectStyle(Icons.Outlined.Translate, MuseRed, MuseRedLight)
}
