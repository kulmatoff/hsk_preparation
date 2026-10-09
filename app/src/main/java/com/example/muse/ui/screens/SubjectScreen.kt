package com.example.muse.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.muse.data.content.ContentProvider
import com.example.muse.ui.components.BackRow
import com.example.muse.ui.components.CircleIcon
import com.example.muse.ui.components.ProgressCard
import com.example.muse.ui.components.SectionTitle
import com.example.muse.ui.components.TopicRow
import com.example.muse.ui.components.styleFor
import com.example.muse.ui.progress.ProgressViewModel
import com.example.muse.ui.theme.MuseTextPrimary
import com.example.muse.ui.theme.MuseTextSecondary
import com.example.muse.ui.theme.MuseTheme

/**
 * Экран предмета: шапка с иконкой, карточка прогресса и список тем.
 * Универсален — один и тот же экран для всех предметов (математика,
 * физика, химия, китайский), данные приходят из [ContentProvider].
 */
@Composable
fun SubjectScreen(
    subjectId: String,
    onBack: () -> Unit = {},
    onTopicClick: (Int) -> Unit = {}
) {
    val subjects by ContentProvider.subjects.collectAsStateWithLifecycle()
    val subject = subjects.firstOrNull { it.id == subjectId }
    val progressViewModel: ProgressViewModel = viewModel(
        factory = ProgressViewModel.factory(LocalContext.current.applicationContext)
    )
    val completedTopics by progressViewModel.completedTopics.collectAsStateWithLifecycle()
    val doneCount = completedTopics[subjectId]?.size ?: 0

    Scaffold { padding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            color = MaterialTheme.colorScheme.background
        ) {
            if (subject == null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Предмет не найден",
                        color = MuseTextSecondary,
                        fontSize = 17.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    BackRow(label = "Назад", onClick = onBack)
                }
                return@Surface
            }

            val style = styleFor(subject)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                BackRow(label = "Назад", onClick = onBack)

                Spacer(modifier = Modifier.height(12.dp))

                // Шапка предмета: иконка + название
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CircleIcon(
                        icon = style.icon,
                        tint = style.tint,
                        background = style.tintBackground,
                        size = 56.dp,
                        iconSize = 28.dp
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = subject.name,
                            color = MuseTextPrimary,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = subject.pageSubtitle,
                            color = MuseTextSecondary,
                            fontSize = 15.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                ProgressCard(
                    done = doneCount,
                    total = subject.progressTotal,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                SectionTitle("Темы")
                Spacer(modifier = Modifier.height(12.dp))

                subject.topics.forEachIndexed { index, topic ->
                    TopicRow(
                        title = topic.title,
                        completed = completedTopics[subjectId]?.contains(index) == true,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onTopicClick(index) }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SubjectScreenPreview() {
    MuseTheme {
        SubjectScreen(subjectId = "math")
    }
}
